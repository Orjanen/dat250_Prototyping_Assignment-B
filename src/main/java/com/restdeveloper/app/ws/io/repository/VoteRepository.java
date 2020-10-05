package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Long> {
    VoteEntity findByVoteId(String id);
    public List<VoteEntity> findAllVotesByUser(UserEntity user);
    VoteEntity findByUserAndPollEntity(UserEntity user, PollEntity pollEntity);
}
