package com.restdeveloper.app.ws.service;

import com.restdeveloper.app.ws.shared.dto.PollDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PollService {
    PollDto createPoll(PollDto poll, String id);
    PollDto getPollByPollId(String id);
    List<PollDto> getPolls(String userId);
}
