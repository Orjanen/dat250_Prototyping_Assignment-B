package no.hvl.dat250.iotdevice;

import no.hvl.dat250.iotdevice.device.IoTDevice;
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

/*
NO LONGER IN USE
kept in case a need arises to test a command line IoT Device
 */

public class IotWebSocketClient {



/*

    public static void main(String[] args) {

        final String webSocketServer = "ws://localhost:8080/ws";

        IoTDevice device = new IoTDevice("2");

        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        //stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setMessageConverter(new StringMessageConverter());


        StompSessionHandler sessionHandler = new IoTDeviceSessionHandler(device);

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0VXR2aWtsZXIxMjNAdGVzdGxhbmQuY29tIiwiZXhwIjoxNjA0NDAxMDAwfQ.Lx3wR6HsXsQoFVpvH6nm34gSV_7yDrfHcRh-3EE1U5wsBy0dckmKdXajlqkJEchVmvKbc5GvbBtsaEp0tcYSMA");
        //headers.add("IoTDeviceId", "1");

        stompClient.connect(webSocketServer, headers, sessionHandler);



        //Don't exit program
        //TODO: Change to GUI?
        new Scanner(System.in).nextLine();

    }



 */






}
