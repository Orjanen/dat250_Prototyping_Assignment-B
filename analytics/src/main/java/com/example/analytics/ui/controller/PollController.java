package com.example.analytics.ui.controller;

import com.example.analytics.io.repository.PollRepository;
import com.example.analytics.service.PollService;
import com.example.analytics.shared.dto.PollDto;
import com.example.analytics.ui.model.PollModel;
import com.example.analytics.ui.model.RabbitPollModel;
import com.example.analytics.ui.model.RabbitVoteModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("poll")
public class PollController {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PollService pollService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PollController.class);


    /* TODO: The following code-block is only meant to help testing during code-production.
     * Erase this code-block, when project is finished. (Or implement it with PollService, to keep it.)
     */
    @Autowired
    PollRepository pollRepository;

    @DeleteMapping
    public String deleteAllPolls() {
        LOGGER.info("Deleting all polls");
        pollRepository.deleteAll();
        return "Successfully deleted all polls";
    }
    // End of temporary code-block


    @GetMapping
    public List<PollModel> getAllPolls() {
        LOGGER.debug("Controller initiated to get all polls");

        List<PollDto> pollDtos = pollService.getAllPolls();
        return pollDtos.stream().map(pollDto -> modelMapper.map(pollDto, PollModel.class)).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public PollModel getPollById(@PathVariable String id) {
        LOGGER.debug("Controller initiated to get poll by ID: {}", id);

        PollDto pollDto = pollService.getPollById(id);
        return modelMapper.map(pollDto, PollModel.class);
    }

    @PostMapping
    public PollModel createPoll(@RequestBody RabbitPollModel pollModel) {
        LOGGER.debug("Controller initiated to create poll with pollModel: {}", pollModel);

        PollDto receivedPoll = modelMapper.map(pollModel, PollDto.class);
        PollDto pollDto = pollService.createPoll(receivedPoll);
        return modelMapper.map(pollDto, PollModel.class);
    }

    @GetMapping(path = "/jpa-id/{jpaId}")
    public PollModel getPollByJpaId(@PathVariable String jpaId) {
        LOGGER.debug("Controller initiated to get poll with jpaID: {}", jpaId);

        PollDto pollDto = pollService.getPollByJpaId(jpaId);
        return modelMapper.map(pollDto, PollModel.class);
    }

    @PutMapping(path = "/jpa-id/{jpaId}")
    public PollModel updatePollVotes(@RequestBody RabbitVoteModel voteModel, @PathVariable("jpaId") String jpaId) {
        LOGGER.debug("Controller initiated to update poll with jpaID {}, using voteModel: {}", jpaId, voteModel);

        PollDto receivedVotes = modelMapper.map(voteModel, PollDto.class);
        PollDto pollDto = pollService.updatePollVotes(receivedVotes, jpaId);
        return modelMapper.map(pollDto, PollModel.class);
    }
}
