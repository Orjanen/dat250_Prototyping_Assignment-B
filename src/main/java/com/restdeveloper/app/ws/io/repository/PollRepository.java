package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends CrudRepository<PollEntity, Long> {
    PollEntity findByPollId(String id);
}
