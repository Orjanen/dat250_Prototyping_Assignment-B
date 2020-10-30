package no.hvl.dat250.iotdevice.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import no.hvl.dat250.iotdevice.device.DeviceListener;
import no.hvl.dat250.iotdevice.device.IoTDevice;
import no.hvl.dat250.iotdevice.model.Poll;
import no.hvl.dat250.iotdevice.model.Vote;

public class IoTDeviceGUIController implements DeviceListener {
    @FXML public Label PollQuestionLabel;
    @FXML public Label InfoLabel;
    @FXML public Label OptionOneLabel;
    @FXML public Label OptionTwoLabel;
    @FXML public Label OptionOneVotesLabel;
    @FXML public Label OptionTwoVotesLabel;




    public IoTDevice device;
    public void setIoTDevice(IoTDevice device){
        this.device = device;
        device.registerDeviceListener(this);
    }

    private void updateDisplayedVotes(Poll poll){

        //JavaFX only allows GUI updates from its own thread - delay update until the thread is ready to update
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                OptionOneVotesLabel.setText(Integer.toString(poll.getOptionOneVotes()));
                OptionTwoVotesLabel.setText(Integer.toString(poll.getOptionTwoVotes()));
            }
        });


    }

    public void updateCurrentUnsentDisplayedVote(Vote vote){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                InfoLabel.setText("Current device votes (press send to send votes to server):");
                OptionOneVotesLabel.setText(Integer.toString(vote.getOptionOneVotes()));
                OptionTwoVotesLabel.setText(Integer.toString(vote.getOptionTwoVotes()));
            }
        });
    }

    public void voteForOptionOneButtonPressed(ActionEvent actionEvent) {
        if(device.voteForOptionOne()){
            updateCurrentUnsentDisplayedVote(device.getCurrentVote());

        } else{
            voteWasRejected();
        }

    }

    public void voteForOptionTwoButtonPressed(ActionEvent actionEvent) {
        if(device.voteForOptionTwo()){
            updateCurrentUnsentDisplayedVote(device.getCurrentVote());
        } else{
            voteWasRejected();
        }

    }

    public void voteWasRejected(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                InfoLabel.setText("Vote not accepted - Poll has finished or device is not paired with poll");
            }
        });
    }

    public void resetVotesButtonPressed(ActionEvent actionEvent) {
        device.resetCurrentVote();
        resetDisplayedVotesToCurrentPollVotes(device.getCurrentPoll());
    }

    private void resetDisplayedVotesToCurrentPollVotes(Poll poll) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                OptionOneVotesLabel.setText(Integer.toString(poll.getOptionOneVotes()));
                OptionTwoVotesLabel.setText(Integer.toString(poll.getOptionTwoVotes()));
            }
        });

    }


    public void sendVotesButtonPressed(ActionEvent actionEvent) {

        device.handleSendButtonPressed();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               //TODO: Implement confirmation of sent votes


                InfoLabel.setText("Total votes in cloud: ");
            }
        });

    }

    @Override
    public void onPollEnd(Poll poll) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                InfoLabel.setText("POLL IS ENDING - NO MORE VOTES WILL BE ACCEPTED");
            }
        });

        resetDisplayedVotesToCurrentPollVotes(poll);
    }

    @Override
    public void onReceivedPollUpdate(Poll poll) {
        //Delay displaying the updated total if the device has unsent votes
        if(device.noVotesHaveBeenCastLocally()){
            resetDisplayedVotesToCurrentPollVotes(poll);
        }

    }

    @Override
    public void onNewPollReceived(Poll poll) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                PollQuestionLabel.setText(poll.getPollName());
            }
        });

        resetDisplayedVotesToCurrentPollVotes(poll);

    }
}
