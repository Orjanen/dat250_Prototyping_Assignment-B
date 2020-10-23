package no.hvl.dat250.iotdevice;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IotWebSocketClient {


    final static String webSocketServer = "ws://localhost:8080/ws";


    public static void main(String[] args) {

        IoTVotingDevice device = new IoTVotingDevice("IOT-1");

        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new StringMessageConverter());

        StompSessionHandler sessionHandler = new IoTDeviceSessionHandler(device);

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0VXR2aWtsZXIyM0B0ZXN0bGFuZC5jb20iLCJleHAiOjE2MDM3MjEzODV9.tLHdunSiPVPdbKEljUuFOYH54zpXKDIImzwF8O_NLf4gAm9Y9h2ZcHq38P32cHHCQYXWhyOW9czIk1JIWABbAQ");
        //headers.add("IoTDeviceId", "1");

        stompClient.connect(webSocketServer, headers, sessionHandler);



        //Don't exit program
        //TODO: Change to GUI?
        new Scanner(System.in).nextLine();

    }






}
