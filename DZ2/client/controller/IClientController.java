package client.controller;

public interface IClientController {
    void disconnectFromServer();

    String getName();

    boolean connectToServer(String text);

    void message();

    void disconnectServer();

    void push(String text);
}
