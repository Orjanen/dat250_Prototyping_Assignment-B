package com.restdeveloper.app.ws.publisher;

import com.restdeveloper.app.ws.io.entity.IoTDevice;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.repository.IoTDeviceRepository;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.publisher.dweetIO.DweetIOAlerter;
import com.restdeveloper.app.ws.publisher.dweetIO.DweetStatusConstants;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.websocket.WebSocketMessageSender;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
@Transactional
public class PollAlertScheduleHandler {

    @Autowired
    PollRepository pollRepository;

    @Autowired
    IoTDeviceRepository deviceRepository;

    @Autowired
    WebSocketMessageSender webSocketMessageSender;

    @Autowired
    DweetIOAlerter dweetIOAlerter;

    ModelMapper modelMapper = new ModelMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(PollAlertScheduleHandler.class);

    //300 000 milliseconds = every 5 minutes
    @Scheduled(fixedRate = 2000)
    //@Scheduled(fixedRate = 30000) //30 000 = 30 seconds
    public void sendAlertsForFinishedPolls(){
        //Get all polls from database where end time is before now, and its alerts have not yet been sent
        List<PollEntity> finishedPolls = pollRepository.findAllByEndTimeBeforeAndAlertsHaveBeenSentFalse(LocalDateTime.now());

        for(PollEntity poll : finishedPolls){
            LOGGER.info("Poll {} is finished | Sending alerts and unpairing IoTDevices", poll.getPollId());



            //Alert IoTDevices about their poll having ended
            PollDto pollDto = modelMapper.map(poll, PollDto.class);
            webSocketMessageSender.sendFinishedPollMessage(pollDto);

            performPollEndedActions(poll);


            dweetIOAlerter.notifyDweetAboutPoll(poll, DweetStatusConstants.POLL_ENDED);

            poll.setAlertsHaveBeenSent(true);
            pollRepository.save(poll);

        }

    }

    private void performPollEndedActions(PollEntity poll){
        List<IoTDevice> pairedDevices = deviceRepository.findAllByCurrentPoll(poll);
        for(IoTDevice device : pairedDevices){
            device.resetDevice();
            deviceRepository.save(device);
        }
    }

}
