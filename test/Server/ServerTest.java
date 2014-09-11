/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Seb
 */
public class ServerTest {
    
    public ServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of stopServer method, of class Server.
     */
    @Test
    public void testStopServer() {
        System.out.println("stopServer");
        Server.stopServer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeHandler method, of class Server.
     */
    @Test
    public void testRemoveHandler() {
        System.out.println("removeHandler");
        ClientHandler ch = null;
        Server.removeHandler(ch);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberOfClients method, of class Server.
     */
    @Test
    public void testGetNumberOfClients() throws Exception {
        System.out.println("getNumberOfClients");
        int expResult = 0;
        int result = Server.getNumberOfClients();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClients method, of class Server.
     */
    @Test
    public void testGetClients() {
        System.out.println("getClients");
        Server instance = new Server();
        List<ClientHandler> expResult = null;
        List<ClientHandler> result = instance.getClients();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of send method, of class Server.
     */
    @Test
    public void testSend() {
        System.out.println("send");
        String messageString = "";
        String msg = "";
        String[] receivers = null;
        Server.send(messageString, msg, receivers);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of broadcastUserList method, of class Server.
     */
    @Test
    public void testBroadcastUserList() {
        System.out.println("broadcastUserList");
        Server.broadcastUserList();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Server.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Server.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
