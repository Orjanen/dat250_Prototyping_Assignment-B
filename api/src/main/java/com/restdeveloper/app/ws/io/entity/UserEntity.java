package com.restdeveloper.app.ws.io.entity;

import com.google.gson.annotations.Expose;
import com.restdeveloper.app.ws.publisher.listener.EntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
public class UserEntity extends Voter implements Serializable {
    private static final long serialVersionUID= -3609876517654923756L;


    @Column(nullable = false)
    @Expose
    private String userId;

    @Column(nullable = false, length = 50)
    @Expose
    private String firstName;

    @Column(nullable = false, length = 50)
    @Expose
    private String lastName;

    @Column(nullable = false, length = 120)
    @Expose
    private String email;

    @Column(nullable = false)
    private boolean banStatus = false;

    @Column(nullable = false)
    private String encryptedPassword;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @Expose(serialize = false, deserialize = false)
    private List<PollEntity> myPolls;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName ="id" ),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }

    public boolean isBanStatus() {
        return banStatus;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }

    public List<PollEntity> getMyPolls() {
        return myPolls;
    }

    public void setMyPolls(List<PollEntity> myPolls) {
        this.myPolls = myPolls;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

}
