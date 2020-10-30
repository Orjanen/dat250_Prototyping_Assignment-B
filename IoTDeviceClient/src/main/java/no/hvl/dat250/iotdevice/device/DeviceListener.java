package no.hvl.dat250.iotdevice.device;

import no.hvl.dat250.iotdevice.model.Poll;

import java.util.EventListener;

public interface DeviceListener extends EventListener {
    void onPollEnd(Poll poll);
    void onReceivedPollUpdate(Poll poll);
    void onNewPollReceived(Poll poll);
}
