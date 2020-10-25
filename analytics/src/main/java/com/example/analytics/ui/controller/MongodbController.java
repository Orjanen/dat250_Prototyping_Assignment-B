package com.example.analytics.ui.controller;

import com.example.analytics.io.repository.PollRepository;
import com.example.analytics.service.PollService;
import com.example.analytics.shared.dto.PollDto;
import com.example.analytics.ui.model.PollModel;
import com.example.analytics.ui.model.RabbitPollModel;
import com.example.analytics.ui.model.RabbitVoteModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("poll")
public class MongodbController {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    PollService pollService;


    /* TODO: The following code-block is only meant to help testing during code-production.
     * Erase this code-block, when project is finished. (Or implement it with PollService, to keep it.)
     */
    @Autowired
    PollRepository pollRepository;
    @DeleteMapping
    public String deleteAllPolls() {
        System.out.println("Deleting all polls...");
        pollRepository.deleteAll();
        return "Polls are deleted successfully";
    }
    // End of temporary code-block


    @GetMapping
    public List<PollModel> getAllPolls() {
        List<PollDto> pollDtos = pollService.getAllPolls();
        return pollDtos.stream().map(pollDto -> modelMapper.map(pollDto, PollModel.class)).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public PollModel getPollById(@PathVariable String id) {
        PollDto pollDto = pollService.getPollById(id);
        return modelMapper.map(pollDto, PollModel.class);
    }

    @PostMapping
    public PollModel createPoll(@RequestBody RabbitPollModel pollModel) {
        PollDto receivedPoll = modelMapper.map(pollModel, PollDto.class);
        PollDto pollDto = pollService.createPoll(receivedPoll);
        return modelMapper.map(pollDto, PollModel.class);
    }



    @GetMapping(path = "/jpa-id/{jpaId}")
    public PollModel getPollByJpaId(@PathVariable String jpaId) {
        PollDto pollDto = pollService.getPollByJpaId(jpaId);
        return modelMapper.map(pollDto, PollModel.class);
    }

    @PutMapping(path = "/jpa-id/{jpaId}")
    public PollModel updatePollVotes(@RequestBody RabbitVoteModel voteModel, @PathVariable("jpaId") String jpaId){
        PollDto receivedVotes = modelMapper.map(voteModel,PollDto.class);
        PollDto pollDto = pollService.updatePollVotes(receivedVotes, jpaId);
        return modelMapper.map(pollDto,PollModel.class);
    }
}
