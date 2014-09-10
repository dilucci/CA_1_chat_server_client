package echoclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

public class EchoClient extends Thread {

    Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private static List<EchoListener> listeners = new ArrayList();
    private String message;
    private String userName;
    private ArrayList<String> onlineUserList = new ArrayList<>();

    public void connect(String address, int port, String name) throws UnknownHostException, IOException {
        if (socket == null) {
            this.userName = name;
            this.port = port;
            serverAddress = InetAddress.getByName(address);
            socket = new Socket(serverAddress, port);
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
            start();
            command(ProtocolStrings.CONNECT + userName);
        }
    }

    public void send(String msg, List<String> receivers) {
        if (receivers.size() > 0) {
            String receiverString = "";
            for (String user : receivers) {
                receiverString = receiverString + user + ",";
            }
//            System.out.println("ReceiverString: " + receiverString);
            if (receiverString.endsWith(",")) {
                receiverString = receiverString.substring(0, receiverString.length() - 1); // remove last character from the string, which will always be a surplus ","
            }
            msg = receiverString + "#" + msg;
//            System.out.println("Message: " + msg);
        }
        else {
            msg = "*#" + msg;
        }
        command(ProtocolStrings.SEND + msg);
    }

    public void stopClient() throws IOException {
        output.println(ProtocolStrings.CLOSE);
    }

    public void command(String commandString) {
        output.println(commandString);
    }

    public void registerEchoListener(EchoListener l) {
        listeners.add(l);
    }

    public void unregisterEchoListener(EchoListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(String msg) {
        for (EchoListener echoListener : listeners) {
            echoListener.messageArrived(msg);
        }
    }

    private void notifyListeners(ArrayList<String> onlineUserList) {
        for (EchoListener echoListener : listeners) {
            echoListener.messageArrived(onlineUserList);
        }
    }

    public String getMessage() {
        return message;
    }

//    public String receive() {
//        String msg = input.nextLine();
//        if (msg.equals(ProtocolStrings.CLOSE)) {
//            try {
//                socket.close();
//            }
//            catch (IOException ex) {
//                Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return msg;
//    }
    public static void main(String[] args) {
        int port = 9090;
        String ip = "localhost";
        if (args.length == 2) {
            port = Integer.parseInt(args[0]);
            ip = args[1];
        }
//        try {
//            EchoClient tester = new EchoClient();
//            tester.registerEchoListener(new EchoListener() {
//
//                @Override
//                public void messageArrived(String data) {
//                    System.out.println("test " + data);
//                }
//            });
//
//            tester.connect(ip, port, "test");
//            System.out.println("Sending 'Hello world'");
//            tester.send("Hello World");
//            System.out.println("Waiting for a reply");
//            tester.stopClient();
//        }
//        catch (UnknownHostException ex) {
//            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        catch (IOException ex) {
//            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void run() {
        String msg = input.nextLine();

        while (!msg.equals(ProtocolStrings.CLOSE)) {
            String[] partsArray = msg.split("#");
            String command = partsArray[0] + "#";
            if (command.equals(ProtocolStrings.ONLINE)) {
                ArrayList<String> newUserList;
                String userListString = partsArray[1];
                if (userListString.contains(",")) {
                    String[] userList = userListString.split(",");
                    newUserList = new ArrayList<>(Arrays.asList(userList));
                }
                else {
                    newUserList = new ArrayList<>();
                    newUserList.add(userListString);
                }
                if (onlineUserList.isEmpty()) {
                    notifyListeners("Welcome to the chat-server "+"["+userName+"].");
                }
                if (newUserList.size() > onlineUserList.size() && onlineUserList.size() > 0) {
                    notifyListeners("[" + newUserList.get(newUserList.size() - 1) + "] " + "connected."); //finds the last connected user
                }
                if (newUserList.size() < onlineUserList.size()) {
                    for (String user : onlineUserList) {
                        if (!newUserList.contains(user)) {
                            notifyListeners("[" + user + "] " + "disconnected.");
                        }
                    }
                }
                onlineUserList =  newUserList;
                Collections.sort(onlineUserList);
                notifyListeners(onlineUserList);
            }
            if (command.equals(ProtocolStrings.MESSAGE)) {
                String[] messageParts = msg.split("#",3);
                notifyListeners("From: " + "[" + messageParts[1] + "]" + ": " + messageParts[2]);
            }
            msg = input.nextLine();
        }
        notifyListeners(msg);
        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void stringReceiver(String message) {
//        String[] partsArray = message.split("#");  // entire command including COMMAND, NAMES and MESSAGE. Should this be refactor into seperate method?
//        String command = partsArray[0] + "#";
//
//        if (command.equals(ProtocolStrings.CONNECT)) {
//            String name = partsArray[1];
//            setUserName(name);
//        }
//    }
}
