/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import echoclient.EchoClient;
import java.io.IOException;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Gruppe4
 */
public class TestClient {

    private EchoClient testClient1;
    private EchoClient testClient2;
    private String ip;
    private int port;
    private static MockServer mockServer;

    public TestClient() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MockServer.main(null);
//                mockServer = new MockServer();
//                mockServer.
            }
        }).start();
    }
    @AfterClass
    public static void tearDownClass() {
//        MockServer.stopServer();
    }

    @Before
    public void setUp() throws IOException {
        testClient1 = new EchoClient();
        testClient2 = new EchoClient();
        ip = "localhost";
        port = 9090;
    }

    @Test
    public void connect() throws IOException{
        testClient1.connect("localhost", 9090, "Hans");
        assertEquals("CONNECT#Hans", MockServer.inputServerString());

        testClient2.connect("localhost", 9090, "Ib");
        assertEquals("CONNECT#Ib", MockServer.inputServerString());
    }
    
//    @Test
//    public void send() throws IOException {
//        ArrayList<String> receivers = new ArrayList<>();
//        receivers.add("Ib");
//        testClient1.send("Hej Ib", receivers);
//        assertEquals("SEND#Ib#Hej Ib", mockServer.inputServerString());
//    }

//    @Test
//    public void send() throws IOException {
//        socket = new Socket("localhost", 9090);
//        input = new Scanner(socket.getInputStream());
//        testClient1.connect("localhost", 9090, "Hans");
//        testClient2.connect("localhost", 9090, "Ib");
//        System.out.println("første: " + input.nextLine());
//        System.out.println("første: " + input.nextLine());
//        ArrayList<String> receivers = new ArrayList<>();
//        ArrayList<ClientHandler> users = new ArrayList<>();
//        receivers.add("Ib");
//        testClient1.send("Hej Ib din gamle svinger!", receivers);
//        assertEquals("SEND#Ib#Hej Ib din gamle svinger!", input.nextLine());
//        for (ClientHandler user : users) {
//            if (user.getUserName().equals("Ib")) {
//                String msg = user.input.nextLine();
//                assertEquals("SEND#Ib#Hej Ib din gamle svinger!", msg);
//            }
//        }
//
//        assertEquals("Hej Ib din gamle svinger!", testClient2.getMessage());
//    }
}
