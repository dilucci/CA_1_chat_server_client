package Server;

import ChatClient.ClientListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import shared.ProtocolStrings;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gruppe 4, Andreas, Michael og Sebastian
 */
public class MockClient extends Thread {
    
    private Socket socket;
    
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private String userName;
    
    public void connect(String address, int port, String name) throws UnknownHostException, IOException {
        if (socket == null) {
            this.userName = name;
            serverAddress = InetAddress.getByName("localhost");
            socket = new Socket(serverAddress, port);
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
            start();
            command(ProtocolStrings.CONNECT + userName);
        }
    }
    
     public void command(String commandString) {
        output.println(commandString);
    }
    
    public String inputClientString(){
        return input.nextLine();
    }
}
