package no.hvl.dat250.iotdevice.device;

import no.hvl.dat250.iotdevice.model.Poll;
import no.hvl.dat250.iotdevice.model.Vote;

import java.util.Collection;
import java.util.HashSet;

public class IoTDevice {

    private String publicId;

    private Vote currentVote;
    private Poll currentPoll;

    private String pairedPollId;

    private boolean currentlyVoting;

    private Collection<DeviceListener> deviceListeners;
    private Collection<DeviceSendButtonListener> deviceSendButtonListeners;

    public IoTDevice(String publicId) {
        this.publicId = publicId;
        deviceListeners = new HashSet<>();
        deviceSendButtonListeners = new HashSet<>();
    }


    /*
    voteForOptionX:
    don't accept vote if the device is not paired with a poll
    if this is the first vote cast for a new vote, create a new Vote
     */
    public boolean voteForOptionOne() {
        if(currentPoll != null){
            if(currentVote == null){
                currentVote = new Vote(currentPoll.getPollId());
            }
            if(!currentPoll.isFinished()){
                currentVote.voteForOptionOne();
                return true;
            }

        }
        return false;

    }

    public boolean voteForOptionTwo() {
        if(currentPoll != null){
            if(currentVote == null){
                currentVote = new Vote(currentPoll.getPollId());
            }
            if(!currentPoll.isFinished()){
                currentVote.voteForOptionTwo();
                return true;
            }
        }
        return false;
    }

    public void registerDeviceListener(DeviceListener listener){
        deviceListeners.add(listener);
    }

    public void registerDeviceSendButtonListener(DeviceSendButtonListener listener){
        deviceSendButtonListeners.add(listener);
    }

    public void handlePollEnding() {
        for(DeviceListener listener : deviceListeners){
            listener.onPollEnd(currentPoll);
        }
    }

    public void handlePollUpdate() {
        for(DeviceListener listener : deviceListeners){
            listener.onReceivedPollUpdate(currentPoll);
        }

    }

    public void handleNewPollReceived(){
        for(DeviceListener listener : deviceListeners){
            listener.onNewPollReceived(currentPoll);
        }
    }

    public void handleSendButtonPressed(){
        for(DeviceSendButtonListener listener : deviceSendButtonListeners){
            listener.onSendButtonPressed(currentVote);
        }

        resetCurrentVote();
    }

    public void resetCurrentVote() {
        currentVote = new Vote(currentPoll.getPollId());
    }


    public boolean noVotesHaveBeenCastLocally(){
        return currentVote == null || currentVote.getOptionOneVotes() == 0 && currentVote.getOptionTwoVotes() == 0;
    }

    public void handleNewVoteReceived(Vote vote) {
        currentPoll.addVote(vote);

        handlePollUpdate();
    }



    public Vote getCurrentVote() {
        return currentVote;
    }

    public void setCurrentVote(Vote currentVote) {
        this.currentVote = currentVote;
    }

    public Poll getCurrentPoll() {
        return currentPoll;
    }

    public void setCurrentPoll(Poll currentPoll) {
        this.currentPoll = currentPoll;
        this.currentVote = new Vote(currentPoll.getPollId());
        handleNewPollReceived();
    }

    public boolean isCurrentlyVoting() {
        return currentlyVoting;
    }

    public void setCurrentlyVoting(boolean currentlyVoting) {
        this.currentlyVoting = currentlyVoting;
    }



    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }




    public String getPairedPollId() {
        return pairedPollId;
    }

    public void setPairedPollId(String pairedPollId) {
        this.pairedPollId = pairedPollId;
    }

    public void handleNotPaired() {
    }
}
