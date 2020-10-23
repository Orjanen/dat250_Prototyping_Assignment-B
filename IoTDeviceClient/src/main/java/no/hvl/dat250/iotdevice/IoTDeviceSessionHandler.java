package no.hvl.dat250.iotdevice;

import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

public class IoTDeviceSessionHandler extends StompSessionHandlerAdapter {


    private IoTVotingDevice device;
    private StompSession session;
    private StompSession.Subscription pollSub;

    public IoTDeviceSessionHandler(IoTVotingDevice device){
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


        if(message.startsWith(MessageConstants.PAIRED_WITH_NEW_CHANNEL)){
            String channelId = message.substring(MessageConstants.PAIRED_WITH_NEW_CHANNEL.length());
            pollSub = session.subscribe("/app/poll/" + channelId + "/sub", this);
            device.setCurrentPollId(channelId);


            System.out.println("Subscribed to: " + pollSub.getSubscriptionHeaders());

        } else if(message.startsWith(MessageConstants.POLL_ENDED)){
            device.handlePollEnding();
            pollSub.unsubscribe();
            pollSub = null;
        } else if(message.startsWith(MessageConstants.POLL_UPDATE)){
            device.handlePollUpdate(message);
        } else {
            System.out.println("Received: " + message);
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
