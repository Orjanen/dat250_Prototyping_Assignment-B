package com.restdeveloper.app.ws;

import com.restdeveloper.app.ws.io.entity.AuthorityEntity;
import com.restdeveloper.app.ws.io.entity.RoleEntity;
import com.restdeveloper.app.ws.io.repository.AuthorityRepository;
import com.restdeveloper.app.ws.io.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialSetupUsers {

    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event){

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

    }

    @Transactional
    AuthorityEntity createAuthority(String name){

        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null){
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }

        return authority;
    }

    @Transactional
    RoleEntity createRole(String name, Collection<AuthorityEntity> authorities){

        RoleEntity role = roleRepository.findByName(name);
        if (role == null){
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }

        return role;
    }

}
