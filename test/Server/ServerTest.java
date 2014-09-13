/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import ChatServer.Server;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gruppe 4, Andreas, Michael og Sebastian
 */
public class ServerTest {
    
    MockClient mockClientIb;
    MockClient mockClientHans;
    
    public ServerTest() {
    }
    
//    @BeforeClass
//    public static void setUpClass() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Server.main(null);
//            }
//        }).start();
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//        Server.stopServer();
//       
//    }
    
    @Before
    public void setUp() {
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(null);
            }
        }).start();
        
        mockClientIb = new MockClient();
        mockClientHans = new MockClient();
    }
    
    @After
    public void tearDown() {
        Server.stopServer();
    }

        /**
     * Test of message method, of class Server.
     */
    @Test
    public void testOnlineSendClose() throws IOException {
        mockClientIb.connect("localhost", 9090, "Ib");
        assertEquals("ONLINE#Ib", mockClientIb.inputClientString());
        
        mockClientHans.connect("localhost", 9090, "Hans");
        assertEquals("ONLINE#Ib,Hans", mockClientIb.inputClientString());
        assertEquals("ONLINE#Ib,Hans", mockClientHans.inputClientString());
        
        mockClientIb.command("SEND#Hans#Hej Hans");
        assertEquals("MESSAGE#Ib#Hej Hans", mockClientHans.inputClientString());
        
       
        mockClientIb.command("SEND#Hans,Ib#Hej Hans og mig selv");
        assertEquals("MESSAGE#Ib#Hej Hans og mig selv", mockClientHans.inputClientString());
        assertEquals("MESSAGE#Ib#Hej Hans og mig selv", mockClientIb.inputClientString());
        
        mockClientIb.command("SEND#*#Hej alle");
        assertEquals("MESSAGE#Ib#Hej alle", mockClientHans.inputClientString());
        assertEquals("MESSAGE#Ib#Hej alle", mockClientIb.inputClientString());
        
        mockClientIb.command("CLOSE#");
        assertEquals("CLOSE#", mockClientIb.inputClientString());
        assertEquals("ONLINE#Hans", mockClientHans.inputClientString());
    }    
    
    
//    /**
//     * Test of message method, of class Server.
//     */
//    @Test
//    public void testOnline() throws IOException {
//        mockClientIb.connect("localhost", 9090, "Ib");
//        assertEquals("ONLINE#Ib", mockClientIb.inputClientString());
//        
//        mockClientHans.connect("localhost", 9090, "Hans");
//        assertEquals("ONLINE#Ib,Hans", mockClientIb.inputClientString());
//        assertEquals("ONLINE#Ib,Hans", mockClientHans.inputClientString());
//    }    
//    
//    /**
//     * Test of message method, of class Server.
//     */
//    @Test
//    public void testMessage() throws IOException {
//        mockClientIb.connect("localhost", 9090, "Ib");
//        assertEquals("ONLINE#Ib", mockClientIb.inputClientString());
//        
//        mockClientHans.connect("localhost", 9090, "Hans");
//        assertEquals("ONLINE#Ib,Hans", mockClientHans.inputClientString());
//        
//        mockClientIb.command("SEND#Hans#Hej Hans");
//        assertEquals("MESSAGE#Ib#Hej Hans", mockClientHans.inputClientString());
//    }
    
    
}
