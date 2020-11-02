package com.restdeveloper.app.ws.shared;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.service.implementation.PollServiceImpl;
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
    WebSocketMessageSender webSocketMessageSender;

    ModelMapper modelMapper = new ModelMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(PollServiceImpl.class);

    //300 000 milliseconds = every 5 minutes
    @Scheduled(fixedRate = 300000)
    //@Scheduled(fixedRate = 30000) //30 000 = 30 seconds
    public void sendAlertsForFinishedPolls(){
        //Get all polls from database where end time is before now, and its alerts have not yet been sent
        List<PollEntity> finishedPolls = pollRepository.findAllByEndTimeBeforeAndAlertsHaveBeenSentFalse(LocalDateTime.now());

        for(PollEntity poll : finishedPolls){
            LOGGER.info("Sending alerts: Poll " + poll.getPollId() + " is finished");

            PollDto pollDto = modelMapper.map(poll, PollDto.class);
            webSocketMessageSender.sendFinishedPollMessage(pollDto);

            //TODO: Send RabbitMQ message
            //TODO: Send dweet.io message

            poll.setAlertsHaveBeenSent(true);
            pollRepository.save(poll);

        }

    }

}
