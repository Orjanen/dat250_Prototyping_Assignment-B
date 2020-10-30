package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.io.repository.VoteRepository;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.UnregisteredVoteForPrivatePollException;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    SimpMessagingTemplate template;


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
    public VoteDto updateVote(VoteDto voteDto, String pollId, String userId) {
        //VoteEntity voteEntity = voteRepository.findByVoteId(voteDto.getVoteId());
        PollEntity poll = pollRepository.findByPollId(pollId);
        UserEntity user = userRepository.findByUserId(userId);
        VoteEntity voteEntity = voteRepository.findByVoterAndPollEntity(user, poll);
        if(voteEntity == null) throw new ResourceNotFoundException("Vote not found");

        voteEntity.setOption1Count(voteDto.getOption1Count());
        voteEntity.setOption2Count(voteDto.getOption2Count());

        VoteEntity updatedVote = voteRepository.save(voteEntity);
        VoteDto returnVote = modelMapper.map(updatedVote, VoteDto.class);

        //TODO: WEBSOCKET: Må kanskje oppdatere poll før den sendes til websocket?


        template.convertAndSend("/topic/poll/"
                + poll.getPollId()
                + "/vote",
                WebSocketMessageConstants.POLL_UPDATE
                        + WebSocketMessageConstants.SEPARATOR
                        + poll.getPollId()
                        + WebSocketMessageConstants.SEPARATOR
                        + poll.getOptionOneVotes()
                        + WebSocketMessageConstants.SEPARATOR
                        + poll.getOptionTwoVotes()
        );

        return returnVote;
    }

    @Override
    public VoteDto addVoteByRegisteredUserToPoll(VoteDto voteDto, String pollId, String userId) {
        return addVote(voteDto, pollId, userId);
    }

    @Override
    public VoteDto addVoteByUnregisteredUserToPoll(VoteDto voteDto, String pollId) {
        return addVote(voteDto, pollId, null);
    }

    private VoteDto addVote(VoteDto voteDto, String pollId, String userId) {
        VoteEntity newVote = modelMapper.map(voteDto, VoteEntity.class);
        newVote.setVoteId(UUID.randomUUID().toString());

        PollEntity poll = pollRepository.findByPollId(pollId);
        if(poll == null) throw new ResourceNotFoundException("Poll not found");

        UserEntity user = null;
        if (userId == null) {
            //Private poll can't accept votes not linked to users
            if(poll.isPrivate()){
                throw new UnregisteredVoteForPrivatePollException("Unregistered users can't vote in private poll");
            }
        } else {
            user = userRepository.findByUserId(userId);
            if(user == null) throw new ResourceNotFoundException("User not found");
            newVote.setVoter(user);
        }

        newVote.setPollEntity(poll);
        VoteEntity savedVote = voteRepository.save(newVote);
        VoteDto returnVote = modelMapper.map(savedVote, VoteDto.class);

        //PollDto pollDto = modelMapper.map(poll, PollDto.class);

        /*
        template.convertAndSend("/app/poll/" + poll.getPollId() + "/sub",
                WebSocketMessageConstants.POLL_UPDATE + "Poll: " + poll.getPollId() + " - 1: " + poll.getOptionOneVotes() + " - 2: " + poll.getOptionTwoVotes()
        );

         */
        template.convertAndSend("/topic/poll/"
                        + poll.getPollId()
                        + "/vote",
                WebSocketMessageConstants.POLL_UPDATE
                        + WebSocketMessageConstants.SEPARATOR
                        + poll.getPollId()
                        + WebSocketMessageConstants.SEPARATOR
                        + returnVote.getOption1Count()
                        + WebSocketMessageConstants.SEPARATOR
                        + returnVote.getOption2Count()
        );
        return returnVote;
    }

    @Override
    public List<VoteDto> getAllVotesByUser(String userId) {

        UserEntity user = userRepository.findByUserId(userId);
        List<VoteEntity> votes = voteRepository.findAllVotesByVoter(user);
        List<VoteDto> voteDtos = votes.stream().map(voteEntity -> modelMapper.map(voteEntity, VoteDto.class)).collect(Collectors.toList());

        return voteDtos;
    }

}
