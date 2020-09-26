package com.restdeveloper.app.ws.service;

import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import org.springframework.stereotype.Service;

@Service
public interface VoteService {
    void updateVotes(String voteId, VotingDetailsModel votingDetailsModel);
}
