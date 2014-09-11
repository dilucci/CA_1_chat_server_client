
import echoserver.ClientHandler;
import echoserver.EchoServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michael
 */
public class MockServer {

    private static ServerSocket serverSocket;
    private static Scanner inputServer;
    private static boolean keepRunning = true;


    public static void main(String[] args) {
        int port = 9090;
        String ip = "localhost";
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            System.out.println("MOCK SERVER STARTED!");
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call
                inputServer = new Scanner(socket.getInputStream());
            }
            while (keepRunning);
        }
        catch (IOException ex) {
            Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            Utils.closeLogger(EchoServer.class.getName());
        }
    }
    
    public static String inputServerString(){
        return inputServer.nextLine();
    }
    
    public static void stopServer() {
        keepRunning = false;
    }
}
