package no.hvl.dat250.iotdevice.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.hvl.dat250.iotdevice.IoTDeviceSessionHandler;
import no.hvl.dat250.iotdevice.device.IoTDevice;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;


public class IoTDeviceGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        final String webSocketServer = "ws://localhost:8080/ws/websocket";
        final String IOT_DEVICE_ID = "IOT-3";


        IoTDevice device = new IoTDevice(IOT_DEVICE_ID);

        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        //stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setMessageConverter(new StringMessageConverter());


        StompSessionHandler sessionHandler = new IoTDeviceSessionHandler(device);

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        //headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0VXR2aWtsZXIxMjNAdGVzdGxhbmQuY29tIiwiZXhwIjoxNjA0NDAxMDAwfQ.Lx3wR6HsXsQoFVpvH6nm34gSV_7yDrfHcRh-3EE1U5wsBy0dckmKdXajlqkJEchVmvKbc5GvbBtsaEp0tcYSMA");
        //headers.add("IoTDeviceId", "1");

        //stompClient.connect(webSocketServer, headers, sessionHandler);
        stompClient.connect(webSocketServer, sessionHandler);







        FXMLLoader loader = new FXMLLoader(getClass().getResource("/IoTDisplay.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);


        stage.setTitle("IoT Device - Poll");
        stage.setScene(scene);
        IoTDeviceGUIController controller = loader.getController();
        controller.setIoTDevice(device);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }






    /*
    private final JTextField uriField;

    private final JButton voteOne;
    private final JButton voteTwo;
    private final JButton resetVotes;
    private final JButton sendVotes;

    private final JTextField currentVotes;
    private final JTextArea textArea;




    public IoTDeviceGUI(String defaultLocation){
        super("IoT Voting Device");
        Container container = getContentPane();
        GridLayout layout = new GridLayout();
        layout.setColumns(4);
        layout.setRows(3);
        container.setLayout(layout);


    }

     */
}




