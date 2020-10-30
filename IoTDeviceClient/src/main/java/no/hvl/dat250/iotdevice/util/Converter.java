package no.hvl.dat250.iotdevice.util;


import com.google.gson.*;
import no.hvl.dat250.iotdevice.MessageConstants;
import no.hvl.dat250.iotdevice.model.Poll;
import no.hvl.dat250.iotdevice.model.Vote;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

public class Converter {

    public static Poll convertPollMessageToPoll(String message){
        String[] pollMessage = message.split(String.valueOf(MessageConstants.SEPARATOR));
        if(pollMessage.length != 8){
            return null;
        }

        //first array entry is PAIRED_WITH_NEW_CHANNEL
        Poll newPoll = new Poll(
                pollMessage[1], //pollId
                pollMessage[2], //pollName
                pollMessage[3], //OptionOne
                pollMessage[4], //OptionTwo
                Integer.parseInt(pollMessage[5]), // optionOneVotes
                Integer.parseInt(pollMessage[6]), // optionTwoVotes
                Duration.parse(pollMessage[7])); // timeRemaining
        return newPoll;
    }

    public static Vote convertVoteMessageToVote(String message) {
        String[] voteMessage = message.split(String.valueOf(MessageConstants.SEPARATOR));


        Vote newVote = new Vote(voteMessage[1], Integer.parseInt(voteMessage[2]), Integer.parseInt(voteMessage[3]));
        return newVote;
    }
}
