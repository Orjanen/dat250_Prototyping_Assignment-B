package com.restdeveloper.app.ws.io.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class IoTDevice extends Voter {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private PollEntity currentPoll;


    private String publicDeviceId;

    public IoTDevice(String publicDeviceId) {
        this.publicDeviceId = publicDeviceId;
    }

    public IoTDevice() {

    }

    public boolean pairDeviceWithPoll(PollEntity pollEntity) {
        //TODO: false if already paired
        currentPoll = pollEntity;
        return true;
    }


    public void resetDevice() {
        currentPoll = null;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public PollEntity getCurrentPoll() {
        return currentPoll;
    }

    public void setCurrentPoll(PollEntity currentPoll) {
        this.currentPoll = currentPoll;
    }

    public String getPublicDeviceId() {
        return publicDeviceId;
    }

    public void setPublicDeviceId(String publicDeviceId) {
        this.publicDeviceId = publicDeviceId;
    }


    @Override
    public String getPublicId() {
        return getPublicDeviceId();
    }

    @Override
    public void setPublicId(String publicId) {
        this.publicDeviceId = publicId;
    }

}
