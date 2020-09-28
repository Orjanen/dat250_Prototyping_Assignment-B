package com.restdeveloper.app.ws.service;

import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VoteService {
    void updateVotes(String voteId, VotingDetailsModel votingDetailsModel);

    VoteDto addVoteByRegisteredUserToPoll(VoteDto voteDto, String pollId, String userId);

    VoteDto addVoteByUnregisteredUserToPoll(VoteDto voteDto, String pollId);

    List<VoteDto> getAllVotesByUser(String userId);
}
