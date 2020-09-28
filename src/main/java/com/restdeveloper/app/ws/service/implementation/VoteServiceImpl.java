package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.io.repository.VoteRepository;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.UnregisteredVoteForPrivatePollException;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.ui.model.response.PollRest;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PollRepository pollRepository;

    ModelMapper modelMapper = new ModelMapper();



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

    @Override
    public VoteDto addVoteByRegisteredUserToPoll(VoteDto voteDto, String pollId, String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if(user == null) throw new ResourceNotFoundException("User not found");
        PollEntity poll = pollRepository.findByPollId(pollId);
        if(poll == null) throw new ResourceNotFoundException("Poll not found");

        VoteEntity newVote = modelMapper.map(voteDto, VoteEntity.class);
        newVote.setUser(user);
        newVote.setPollEntity(poll);

        VoteEntity savedVote = voteRepository.save(newVote);
        VoteDto returnVote = modelMapper.map(savedVote, VoteDto.class);
        return returnVote;

    }

    @Override
    public VoteDto addVoteByUnregisteredUserToPoll(VoteDto voteDto, String pollId) {

        PollEntity poll = pollRepository.findByPollId(pollId);
        if(poll == null) throw new ResourceNotFoundException("Poll not found");

        //Private poll can't accept votes not linked to users
        if(poll.isPrivate()){
            throw new UnregisteredVoteForPrivatePollException("Unregistered users can't vote in private poll");
        }

        VoteEntity newVote = modelMapper.map(voteDto, VoteEntity.class);
        newVote.setVoteId(UUID.randomUUID().toString());
        newVote.setPollEntity(poll);

        VoteEntity savedVote = voteRepository.save(newVote);
        VoteDto returnVote = modelMapper.map(savedVote, VoteDto.class);
        return returnVote;


    }

    @Override
    public List<VoteDto> getAllVotesByUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        List<VoteEntity> votes = voteRepository.findAllVotesByUser(user);
        List<VoteDto> voteDtos = votes.stream().map(voteEntity -> modelMapper.map(voteEntity, VoteDto.class)).collect(Collectors.toList());
        return voteDtos;
    }


}
