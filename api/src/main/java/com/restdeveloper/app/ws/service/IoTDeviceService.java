package com.restdeveloper.app.ws.service;

import com.restdeveloper.app.ws.shared.dto.IoTDeviceDto;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IoTDeviceService {
    IoTDeviceDto getIoTDeviceByPublicDeviceId(String publicDeviceId);

    PollDto getPairedPoll(String publicDeviceId);

    IoTDeviceDto addNewDevice(IoTDeviceDto ioTDeviceDto);

    VoteDto updateVoteForCurrentPoll(String deviceId, VoteDto voteDto);

    IoTDeviceDto setPairedPoll(String deviceId, String pollId);

    List<IoTDeviceDto> getAllUnpairedDevices();
}
