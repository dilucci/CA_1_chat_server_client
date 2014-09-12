package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;

/**
 *
 * @author Gruppe 4, Andreas, Michael og Sebastian
 */
public class Server {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private static final Properties properties = Utils.initProperties("server.properties");
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void stopServer() {
        keepRunning = false;
    }

    protected static synchronized void removeHandler(ClientHandler ch) {
        clients.remove(ch);
    }

    protected static synchronized void addHandler(ClientHandler ch) {
        clients.add(ch);
    }

    public static int getNumberOfClients() throws InterruptedException {
        return clients.size();
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public static void message(String command, String msg, String... receivers) {
        if (receivers.length > 0) {  // sends to specific users if array.length > 0
            for (String receiver : receivers) {
                for (ClientHandler ch : clients) {
                    if (ch.getUserName().equals(receiver)) {
                        ch.send(command + msg);
                    }
                }
            }
        } else {
            for (ClientHandler ch : clients) {  // sends to all users, since none were specified
                ch.send(command + msg);
            }
        }
    }

    protected static synchronized void broadcastUserList() {
        ArrayList<String> userList = new ArrayList<>();
        for (ClientHandler ch : clients) {
            userList.add(ch.getUserName());
        }
        for (ClientHandler ch : clients) {
            ch.sendUserList(userList);
        }
    }

    /**
     Reads and sends chatlog to HttpServer, for displaying on web-server.
    @return String chatLog
    @throws IOException 
    */
    public static String readChatLog() {
        File file = new File("chatLog.txt");
        String chatLog = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            while (line != null) {
                sb.append(line);
                sb.append("%#¤");
                line = br.readLine();
            }
            chatLog = sb.toString();
        }
        catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chatLog;
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(properties.getProperty("port"));
        String ip = properties.getProperty("serverIp");
        String logFile = properties.getProperty("logFile");
        Utils.setLogFile(logFile, Server.class.getName());
        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Server started");
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept(); //Important Blocking call
                ClientHandler clientHandler = new ClientHandler(socket);
                addHandler(clientHandler);
                clientHandler.start();
            } while (keepRunning);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Utils.closeLogger(Server.class.getName());
        }
    }
}
