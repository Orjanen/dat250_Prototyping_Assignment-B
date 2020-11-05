package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PollRepository extends CrudRepository<PollEntity, Long> {
    PollEntity findByPollId(String id);

    public List<PollEntity> findAllPollsByCreator(UserEntity user);

    public List<PollEntity> findAllByEndTimeBeforeAndAlertsHaveBeenSentFalse(LocalDateTime endTime);

}
