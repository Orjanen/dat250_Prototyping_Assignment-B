package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.io.repository.VoteRepository;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Override
    public void updateVotes(String voteId, VotingDetailsModel votingDetailsModel) {
        VoteEntity voteEntity = voteRepository.findByVoteId(voteId);
        if (voteEntity == null) throw new ResourceNotFoundException("Vote entity not found");
        int vote1 = voteEntity.getOption1Count();
        int vote2 = voteEntity.getOption2Count();

        voteEntity.setOption1Count(vote1 + votingDetailsModel.getOption1Count());
        voteEntity.setOption2Count(vote2 + votingDetailsModel.getOption2Count());

        voteRepository.save(voteEntity);
    }
}
