package no.hvl.dat250.iotdevice.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import no.hvl.dat250.iotdevice.device.DeviceListener;
import no.hvl.dat250.iotdevice.device.IoTDevice;
import no.hvl.dat250.iotdevice.model.Poll;

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
        device.registerListener(this);
    }

    private void updateDisplayedVotes(Poll poll){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                OptionOneVotesLabel.setText(Integer.toString(poll.getOptionOneVotes()));
                OptionTwoVotesLabel.setText(Integer.toString(poll.getOptionTwoVotes()));
            }
        });


    }

    public void voteForOptionOneButtonPressed(ActionEvent actionEvent) {
        device.voteForOptionOne();
        updateDisplayedVotes(device.getCurrentPoll());

    }

    public void voteForOptionTwoButtonPressed(ActionEvent actionEvent) {
        device.voteForOptionTwo();
        updateDisplayedVotes(device.getCurrentPoll());
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
        resetDisplayedVotesToCurrentPollVotes(poll);

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
