package no.hvl.dat250.iotdevice;


import no.hvl.dat250.iotdevice.device.IoTDevice;
import no.hvl.dat250.iotdevice.model.Poll;
import no.hvl.dat250.iotdevice.model.Vote;
import no.hvl.dat250.iotdevice.util.Converter;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class IoTDeviceSessionHandler extends StompSessionHandlerAdapter {


    private IoTDevice device;
    private StompSession session;
    private StompSession.Subscription pollSub;

    public IoTDeviceSessionHandler(IoTDevice device){
        super();

        this.device = device;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders){

        this.session = session;
        System.out.println("New session established: " + session.getSessionId());


        //Subscribe to own queue
        session.subscribe("/app/device/" + device.getPublicId(), this);


    }

    @Override
    public Type getPayloadType(StompHeaders headers){
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload){
        String message = (String)payload;

        System.out.println("Received: " + message);

        if(message.startsWith(MessageConstants.PAIRED_WITH_NEW_CHANNEL)){
            String splitMessage[] = message.split(String.valueOf(MessageConstants.SEPARATOR));
            String channelId = splitMessage[1];
            pollSub = session.subscribe("/topic/poll/" + channelId + "/vote", this);

            Poll poll = Converter.convertPollMessageToPoll(message);
            device.setCurrentPoll(poll);

            System.out.println("Subscribed to: " + pollSub.getSubscriptionHeaders());




        } else if(message.startsWith(MessageConstants.POLL_ENDED)){
            device.handlePollEnding();
            pollSub.unsubscribe();
            pollSub = null;
        } else if(message.startsWith(MessageConstants.POLL_UPDATE)){
            Vote vote = Converter.convertVoteMessageToVote(message);


            device.handleNewVoteReceived(vote);

        } else {
        }


    }

    @Override
    public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception){
        System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println(exception);
        exception.printStackTrace();
    }



}
