package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Long> {
}
