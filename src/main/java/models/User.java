package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "appUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    
    @OneToMany(mappedBy="creator")
    private List<Poll> mypolls;

    @OneToMany(mappedBy="voteCastByUser")
    private List<Vote> votes = new ArrayList<>();

    public void addVote(Vote vote){
        vote.setVoteCastByUser(this);
        votes.add(vote);
    }


    public List<Poll> getMypolls() {
        return mypolls;
    }

    public void setMypolls(List<Poll> mypolls) {
        this.mypolls = mypolls;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
