package test.com;

import java.util.*;
import java.io.*;

public class ChatClient  implements ConnectionListener {

    private final String IP;
    private final int PORT = 8000;
    private String name;

    public ChatClient(String IP, String name) {
        this.IP = IP;
        this.name=name;
        try {
            Connection connection = new Connection(this, IP, PORT);
            while (true) {
                connection.sendString(writeNameMsg(writeMsg()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            printMsg("Connection exception: " + e);
        }
    }

    @Override
    public synchronized void onConnectionReady(Connection connection) {
        printMsg("Connection with server exist...");
    }

    @Override
    public synchronized void onReceiveString(Connection connection, String string) {
        printMsg(string);
    }

    @Override
    public synchronized void onDisconneckt(Connection connection) {
        printMsg("User disconnected: " + connection);
    }

    @Override
    public synchronized void onException(Connection connection, Exception e) {
        printMsg("Connection exception: " + e);
    }
    private synchronized void printMsg(String msg) {
        System.out.println(msg);
    }

    public static synchronized String writeMsg() {
        Scanner scanner = new Scanner(System.in);
        String write = scanner.nextLine();
        return write;
    }
    public String writeNameMsg(String msg) {
        String writenameplusmsg = name + ": " + msg;
        return writenameplusmsg;
    }
}