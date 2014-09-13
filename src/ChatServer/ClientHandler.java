/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author Gruppe 4, Andreas, Michael og Sebastian
 */
public class ClientHandler extends Thread {

    private Scanner input;
    private PrintWriter writer;
    private Socket socket;
    private String userName;

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String message) {
        writer.println(message);
    }

    public void sendUserList(ArrayList<String> userList) {
        String userListString = "";
        for (String user : userList) {
            userListString = userListString + user + ",";
        }
        if (userListString.endsWith(",")) {
            userListString = userListString.substring(0, userListString.length() - 1);
        }
        send(ProtocolStrings.ONLINE + userListString);
    }

    @Override
    public void run() {
        String message = input.nextLine(); //IMPORTANT blocking call
        
        while (!message.equals(ProtocolStrings.CLOSE)) {

            String[] partsArray = message.split("#", 3);  // entire command including COMMAND, NAMES and MESSAGE. Should this be refactor into seperate method?
            String command = partsArray[0] + "#";
            if (command.equals("CHATLOG#")) {
                    String chatLogString = Server.readChatLog();
                    send(chatLogString);
            }
            if (command.equals("ONLINEUSERS#")) {
                try {
                    Server.removeHandler(this);
                    send("" + Server.getNumberOfClients());
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (command.equals(ProtocolStrings.CONNECT)) { //add new users and broadcast new users connected.
                String name = partsArray[1];
                setUserName(name);
                Server.broadcastUserList();
                Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("[" + userName + "] connected: %1$S ", message));
            }
            if (command.equals(ProtocolStrings.SEND)) {
                Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("[" + userName + "] sent: %1$S ", message));
                message = partsArray[2];
                if (partsArray[1].equals("*")) {                    // sends to all users
                    Server.message(ProtocolStrings.MESSAGE + getUserName() + "#", message);
                }
                if (partsArray[1].contains(",")) {                  // sends to specified users
                    String[] receivers = partsArray[1].split(",");
                    Server.message(ProtocolStrings.MESSAGE + getUserName() + "#", message, receivers);
                } else {                                              // sends to 1 user
                    Server.message(ProtocolStrings.MESSAGE + getUserName() + "#", message, partsArray[1]);
                }
            }
            
            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println(ProtocolStrings.CLOSE); //Echo the stop message back to the client for a nice closedown
        Server.removeHandler(this);
        Server.broadcastUserList();
        try {
            socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(Server.class.getName()).log(Level.INFO, String.format("[" + userName + "] disconnected: %1$S ", message));
    }
}
