package com.restdeveloper.app.ws.ui.model.request;

public class VotingDetailsModel {
    private int option1Count;
    private int option2Count;

    public int getOption1Count() {
        return option1Count;
    }

    public void setOption1Count(int option1Count) {
        this.option1Count = option1Count;
    }

    public int getOption2Count() {
        return option2Count;
    }

    public void setOption2Count(int option2Count) {
        this.option2Count = option2Count;
    }
}
