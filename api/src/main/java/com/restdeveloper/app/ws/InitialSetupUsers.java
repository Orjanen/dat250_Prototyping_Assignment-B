package com.restdeveloper.app.ws;

import com.restdeveloper.app.ws.io.entity.AuthorityEntity;
import com.restdeveloper.app.ws.io.entity.RoleEntity;
import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.io.repository.AuthorityRepository;
import com.restdeveloper.app.ws.io.repository.RoleRepository;
import com.restdeveloper.app.ws.io.repository.UserRepository;
import com.restdeveloper.app.ws.shared.Roles;
import com.restdeveloper.app.ws.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event){

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) return;
        if (userRepository.findByEmail("admin@admin.com") != null) return;
        UserEntity adminUser = new UserEntity();
        adminUser.setFirstName("admin");
        adminUser.setLastName("admin");
        adminUser.setEmail("admin@admin.com");
        adminUser.setUserId(utils.generateUserId(30));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("admin"));
        adminUser.setRoles(Arrays.asList(roleAdmin));

        userRepository.save(adminUser);
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
