package com.restdeveloper.app.ws.service.implementation;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.io.entity.Voter;
import com.restdeveloper.app.ws.io.repository.PollRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.io.repository.VoteRepository;
import com.restdeveloper.app.ws.io.repository.VoterRepository;
import com.restdeveloper.app.ws.service.VoteService;
import com.restdeveloper.app.ws.shared.UnregisteredVoteForPrivatePollException;
import com.restdeveloper.app.ws.shared.WebSocketMessageConstants;
import com.restdeveloper.app.ws.shared.dto.VoteDto;
import com.restdeveloper.app.ws.ui.model.request.VotingDetailsModel;
import com.restdeveloper.app.ws.websocket.WebSocketMessageSender;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    VoterRepository voterRepository;



    @Autowired
    WebSocketMessageSender webSocketMessageSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteServiceImpl.class);

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void updateVotes(String voteId, VotingDetailsModel votingDetailsModel) {
        LOGGER.info("Updating votes on vote with vote-ID: {}", voteId);

        VoteEntity voteEntity = voteRepository.findByVoteId(voteId);
        if (voteEntity == null) {
            LOGGER.error("Could not find vote with vote-ID: {}", voteId);
            throw new ResourceNotFoundException("Could not find vote with vote-ID: " + voteId);
        }

        int vote1 = voteEntity.getOption1Count();
        int vote2 = voteEntity.getOption2Count();
        voteEntity.setOption1Count(vote1 + votingDetailsModel.getOption1Count());
        voteEntity.setOption2Count(vote2 + votingDetailsModel.getOption2Count());

        voteRepository.save(voteEntity);
        LOGGER.debug("Done updating vote");
    }

    @Override
    public VoteDto updateVote(VoteDto voteDto, String pollId, String userId) {
        LOGGER.info("Update vote by user and poll");

        //VoteEntity voteEntity = voteRepository.findByVoteId(voteDto.getVoteId())
        PollEntity poll = pollRepository.findByPollId(pollId);


        //UserEntity user = userRepository.findByUserId(userId);
        Voter voter = voterRepository.findByPublicId(userId);



        VoteEntity voteEntity = voteRepository.findByVoterAndPollEntity(voter, poll);
        if (voteEntity == null) {
            LOGGER.error("Could not find vote");
            throw new ResourceNotFoundException("Could not find vote");
        }

        voteEntity.setOption1Count(voteDto.getOption1Count());
        voteEntity.setOption2Count(voteDto.getOption2Count());

        VoteEntity updatedVote = voteRepository.save(voteEntity);
        VoteDto returnVote = modelMapper.map(updatedVote, VoteDto.class);

        //TODO: WEBSOCKET: Må kanskje oppdatere poll før den sendes til websocket?

        webSocketMessageSender.sendVoteMessageAfterVoteReceived(poll.getPollId(), returnVote);


        LOGGER.debug("Done");
        return returnVote;
    }

    @Override
    public VoteDto addVoteByRegisteredUserToPoll(VoteDto voteDto, String pollId, String userId) {
        LOGGER.debug("Initializing addVote with registered user...");
        return addVote(voteDto, pollId, userId);
    }

    @Override
    public VoteDto addVoteByUnregisteredUserToPoll(VoteDto voteDto, String pollId) {
        LOGGER.debug("Initializing addVote with unregistered user...");
        return addVote(voteDto, pollId, null);
    }

    private VoteDto addVote(VoteDto voteDto, String pollId, String userId) {
        LOGGER.info("Adding vote on poll with poll-ID: {}", pollId);

        VoteEntity newVote = modelMapper.map(voteDto, VoteEntity.class);
        newVote.setVoteId(UUID.randomUUID().toString());

        PollEntity poll = pollRepository.findByPollId(pollId);
        if (poll == null) {
            LOGGER.error("Could not find a poll with ID: {}", pollId);
            throw new ResourceNotFoundException("Could not find poll with ID: " + pollId);
        }

        UserEntity user;
        if (userId == null && poll.isPrivate()) {
            //Private poll can't accept votes not linked to users
            LOGGER.error("Unregistered users can't vote on a private poll");
            throw new UnregisteredVoteForPrivatePollException("Unregistered users can't vote in private poll");

        } else {
            user = userRepository.findByUserId(userId);
            if (user == null) {
                LOGGER.error("Could not find user with user-ID: {}", userId);
                throw new ResourceNotFoundException("Could not find user with user-ID: " + userId);
            }
            newVote.setVoter(user);
        }

        newVote.setPollEntity(poll);
        VoteEntity savedVote = voteRepository.save(newVote);
        VoteDto returnVote = modelMapper.map(savedVote, VoteDto.class);


        LOGGER.debug("Done adding vote");

        webSocketMessageSender.sendVoteMessageAfterVoteReceived(poll.getPollId(), returnVote);

        return returnVote;
    }

    @Override
    public List<VoteDto> getAllVotesByUser(String userId) {
        LOGGER.info("Getting all votes by user with user-ID: {}", userId);

        UserEntity user = userRepository.findByUserId(userId);
        List<VoteEntity> votes = voteRepository.findAllVotesByVoter(user);

        LOGGER.debug("Done getting all votes by user");
        return votes.stream().map(voteEntity -> modelMapper.map(voteEntity, VoteDto.class)).collect(Collectors.toList());
    }

}
