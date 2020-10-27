package com.restdeveloper.app.ws.shared.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 3136891483866285943L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private List<PollDto> myPolls;
    private List<VoteDto> myVotes;
    private Collection<String> roles;

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<PollDto> getMyPolls() {
        return myPolls;
    }

    public void setMyPolls(List<PollDto> myPolls) {
        this.myPolls = myPolls;
    }

    public List<VoteDto> getMyVotes() {
        return myVotes;
    }

    public void setMyVotes(List<VoteDto> myVotes) {
        this.myVotes = myVotes;
    }
}
