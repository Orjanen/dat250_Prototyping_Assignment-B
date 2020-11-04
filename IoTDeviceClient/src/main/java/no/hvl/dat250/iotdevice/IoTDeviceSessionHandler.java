package no.hvl.dat250.iotdevice;


import com.google.gson.*;
import no.hvl.dat250.iotdevice.device.DeviceSendButtonListener;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IoTDeviceSessionHandler extends StompSessionHandlerAdapter implements DeviceSendButtonListener {


    private IoTDevice device;
    private StompSession session;
    private StompSession.Subscription pollSub;

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();

    public IoTDeviceSessionHandler(IoTDevice device){
        super();

        this.device = device;
        this.device.registerDeviceSendButtonListener(this);
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


        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String context = jsonObject.get("context").getAsString();

        System.out.println("Received: " + message);


        if(context.equals(MessageConstants.PAIRED_WITH_NEW_CHANNEL)){

            //TODO: Confirm pairing on device?
            Poll poll = gson.fromJson(message, Poll.class);

            pollSub = session.subscribe("/topic/poll/" + poll.getPollId(), this);

            device.setCurrentPoll(poll);

            System.out.println("Subscribed to: " + pollSub.getSubscriptionHeaders());


        } else if(context.equals(MessageConstants.POLL_ENDED)){
            device.handlePollEnding();
            pollSub.unsubscribe();
            pollSub = null;
        } else if(context.equals(MessageConstants.POLL_UPDATE)){
            Vote vote = gson.fromJson(message, Vote.class);

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


    @Override
    public void onSendButtonPressed(Vote vote) {
        if(pollSub != null){



            String payload = Integer.toString(vote.getOptionOneVotes()) + MessageConstants.SEPARATOR + Integer.toString(vote.getOptionTwoVotes());

            String voteEndpoint = "/app/device/" + device.getPublicId() + "/vote/ws";


            session.send(voteEndpoint, payload);
        }
    }
}
