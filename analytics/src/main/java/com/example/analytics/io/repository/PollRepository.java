package com.example.analytics.io.repository;

import com.example.analytics.io.entity.PollEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends MongoRepository<PollEntity, String> {

    public PollEntity findByJpaId(String jpaId);
}
