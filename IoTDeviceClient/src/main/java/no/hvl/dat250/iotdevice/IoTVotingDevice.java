package no.hvl.dat250.iotdevice;

public class IoTVotingDevice {

    private String publicId;
    private String currentPollId;

    public IoTVotingDevice(String publicId) {
        this.publicId = publicId;
    }



    public void handlePollEnding() {
        System.out.println("Poll " + currentPollId + " ending - no more votes will be accepted");
    }

    public void handlePollUpdate(String message) {
        System.out.println(message);

    }

    public String getPublicId(){
        return this.publicId;
    }

    public String getCurrentPollId() {
        return currentPollId;
    }

    public void setCurrentPollId(String currentPollId) {
        this.currentPollId = currentPollId;
    }
}
