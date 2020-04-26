package test.com;

import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class Connection {

    private final Socket socket;
    private final Thread myThread;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final ConnectionListener eventListener;

    public Connection(ConnectionListener eventListener, String ip, int port) throws IOException {
        this(eventListener, new Socket(ip,port));
    }

    public Connection (ConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener=eventListener;
        this.socket=socket;
        in=new BufferedReader(new InputStreamReader(socket.getInputStream(),Charset.forName("UTF-8")));
        out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),Charset.forName("UTF-8")));
        myThread=new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(Connection.this);
                    while (!myThread.isInterrupted()) {
                        eventListener.onReceiveString(Connection.this, in.readLine());
                    }
                } catch (IOException e) {
                } finally {
    eventListener.onDisconneckt(Connection.this);
                }
            }
        });
        myThread.start();
    }
    public synchronized void sendString(String string){
        try {
            out.write(string + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(Connection.this, e);
            disconnect();
        }
    }
    public synchronized void disconnect (){
        myThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(Connection.this, e);
        }
    }
    @Override
    public String toString() {
        return "Connection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
