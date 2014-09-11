/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Utils;

/**
 * @author Lars Mortensen
 */
public class TestClient {

    private EchoClient testClient;
    private Socket socket;
    private ServerSocket serverSocket;
    private final Properties properties = Utils.initProperties("server.properties");

    public TestClient() {
    }

    @BeforeClass
    public static void setUpClass() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EchoServer.main(null);
            }
        }).start();
    }

    @AfterClass
    public static void tearDownClass() {
        EchoServer.stopServer();
    }

    @Before
    public void setUp() throws IOException {
        int port = 0;
        String ip = "localhost";
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(ip, port));
        socket = new Socket(ip, port);
        testClient = new EchoClient();

    }

    @Test
    public void connect() throws IOException {
        testClient.connect("localhost", 0, "Hans");
        assertEquals(socket, serverSocket.accept());

//    assertEquals("HELLO", client.receive());
    }

//    @Test
//    public void send() throws IOException {
//        EchoClient client = new EchoClient();
//        client.connect("localhost", 9090);
//        client.send("Hello");
//        assertEquals("HELLO", client.getMessage());
//
////    assertEquals("HELLO", client.receive());
//    }
}
