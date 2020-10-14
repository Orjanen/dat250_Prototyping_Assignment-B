package com.restdeveloper.app.ws.ui.model.response;

import java.util.List;

//communicate info back to the client
public class UserRest {
    //public userId
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<PollRest> myPolls;

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

    public List<PollRest> getMyPolls() {
        return myPolls;
    }

    public void setMyPolls(List<PollRest> myPolls) {
        this.myPolls = myPolls;
    }
}
