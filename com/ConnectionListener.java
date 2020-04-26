package test.com;

public interface ConnectionListener {
    public void onConnectionReady (Connection connection);
    public void onReceiveString (Connection connection, String string);
    public void onDisconneckt (Connection connection);
    public void onException (Connection connection, Exception e);
}
