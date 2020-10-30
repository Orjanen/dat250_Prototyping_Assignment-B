package no.hvl.dat250.iotdevice.device;

import no.hvl.dat250.iotdevice.model.Vote;

public interface DeviceSendButtonListener {
    void onSendButtonPressed(Vote vote);
}
