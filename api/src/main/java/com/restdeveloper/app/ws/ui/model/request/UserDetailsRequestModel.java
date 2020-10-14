package com.restdeveloper.app.ws.ui.model.request;

import java.util.List;

//class that must match the json payload
public class UserDetailsRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<PollsRequestModel> myPolls;

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

    public List<PollsRequestModel> getMyPolls() {
        return myPolls;
    }

    public void setMyPolls(List<PollsRequestModel> myPolls) {
        this.myPolls = myPolls;
    }
}
