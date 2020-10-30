package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.Voter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends CrudRepository<Voter, Long> {
    Voter findByPublicId(String publicId);

}
