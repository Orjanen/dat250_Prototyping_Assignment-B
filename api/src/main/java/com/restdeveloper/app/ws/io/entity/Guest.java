package com.restdeveloper.app.ws.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Guest extends Voter {

    UUID uuid;

    public String getPublicId() {
        return uuid.toString();
    }

    @Override
    void setPublicId(String publicId) {
        uuid = UUID.fromString(publicId);
    }

    public UUID getUUID(){
        return uuid;
    }

    public void setUuid(UUID uuid){
        this.uuid = uuid;
    }

}
