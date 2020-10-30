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

    private Collection<DeviceListener> listeners;

    public IoTDevice(String publicId) {
        this.publicId = publicId;
        listeners = new HashSet<>();
    }

    public void voteForOptionOne() {
        if(currentPoll != null){
            if(currentVote == null){
                currentVote = new Vote(currentPoll.getPollId());
            }
            currentVote.voteForOptionOne();
        }

    }

    public void registerListener(DeviceListener listener){
        listeners.add(listener);
    }

    public void handlePollEnding() {
        for(DeviceListener listener : listeners){
            listener.onPollEnd(currentPoll);
        }
    }

    public void handlePollUpdate() {
        for(DeviceListener listener : listeners){
            listener.onReceivedPollUpdate(currentPoll);
        }

    }

    public void handleNewPollReceived(){
        //TODO: Setup new poll
        for(DeviceListener listener : listeners){
            listener.onNewPollReceived(currentPoll);
        }
    }

    public void voteForOptionTwo() {
        if(currentPoll != null){
            if(currentVote == null){
                currentVote = new Vote(currentPoll.getPollId());
            }
            currentVote.voteForOptionTwo();
        }
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
        handleNewPollReceived();
    }

    public boolean isCurrentlyVoting() {
        return currentlyVoting;
    }

    public void setCurrentlyVoting(boolean currentlyVoting) {
        this.currentlyVoting = currentlyVoting;
    }

    public void resetCurrentVote() {
        currentVote = new Vote(currentPoll.getPollId());
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public void handleNewVoteReceived(Vote vote) {
        currentPoll.addVote(vote);

        handlePollUpdate();
    }


    public String getPairedPollId() {
        return pairedPollId;
    }

    public void setPairedPollId(String pairedPollId) {
        this.pairedPollId = pairedPollId;
    }
}
