package com.restdeveloper.app.ws.io.repository;

import com.restdeveloper.app.ws.io.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {
    AuthorityEntity findByName(String name);
}
