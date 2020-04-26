package test.com;

public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter yor name or create new server with command (server) ... ");
        String name = ChatClient.writeMsg();
        if(name.equals("server")) {
            ChatServer chatServer = new ChatServer();
        } else {
            System.out.println("Dear " + name + ", please enter ip ****.****.****.**** ...");
        }
        String ip = ChatClient.writeMsg();
        ChatClient client = new ChatClient(ip, name);
    }

}