/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Client.Client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Gruppe 4, Andreas, Michael og Sebastian
 */
public class ClientTest {

    private Client testClient1;
    private Client testClient2;
    private String ip;
    private int port;
    private static MockServer mockServer;

    public ClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MockServer.main(null);
            }
        }).start();
    }
    @AfterClass
    public static void tearDownClass() {
        MockServer.stopServer();
    }

    @Before
    public void setUp() throws IOException {
        testClient1 = new Client();
        testClient2 = new Client();
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
    
    @Test
    public void closeClient() throws IOException{
        testClient1.connect("localhost", 9090, "Hans");
        assertEquals("CONNECT#Hans", MockServer.inputServerString());
        
        testClient1.stopClient();
        assertEquals("CLOSE#", MockServer.inputServerString());
    }
    
    @Test
    public void sendOne() throws IOException {
        testClient1.connect("localhost", 9090, "Hans");
        assertEquals("CONNECT#Hans", MockServer.inputServerString());
        
        List<String> receivers = new ArrayList<>();
        receivers.add("Ib");
        testClient1.send("Hej Ib", receivers);
        assertEquals("SEND#Ib#Hej Ib", MockServer.inputServerString());
    }
    
    @Test
    public void sendMultiple() throws IOException {
        testClient1.connect("localhost", 9090, "Hans");
        assertEquals("CONNECT#Hans", MockServer.inputServerString());
        
        List<String> receivers = new ArrayList<>();
        receivers.add("Ib");
        receivers.add("Hans");
        testClient1.send("Hej Ib og Hans", receivers);
        assertEquals("SEND#Ib,Hans#Hej Ib og Hans", MockServer.inputServerString());
    }
    
    @Test
    public void sendAll() throws IOException {
        testClient1.connect("localhost", 9090, "Hans");
        assertEquals("CONNECT#Hans", MockServer.inputServerString());
        
        List<String> noReceivers = new ArrayList<>();
        testClient1.send("Hej alle", noReceivers);
        assertEquals("SEND#*#Hej alle", MockServer.inputServerString());
    }
    
    
    
    
    
    
    
    

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
