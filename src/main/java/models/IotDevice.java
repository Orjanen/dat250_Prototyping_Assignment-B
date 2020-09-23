package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class IotDevice {

    @Id
    @GeneratedValue
    private Long Id;
    private String IPAddress;
    private int numbersOfRedVote;
    private int numbersOfGreenVote;

    @OneToMany
    private List<Vote> votes;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getNumbersOfRedVote() {
        return numbersOfRedVote;
    }

    public void setNumbersOfRedVote(int numbersOfRedVote) {
        this.numbersOfRedVote = numbersOfRedVote;
    }

    public int getNumbersOfGreenVote() {
        return numbersOfGreenVote;
    }

    public void setNumbersOfGreenVote(int numbersOfGreenVote) {
        this.numbersOfGreenVote = numbersOfGreenVote;
    }
}
