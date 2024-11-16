package server.controller;

import client.controller.ClientController;


public interface IServerController {

    boolean connectUser(ClientController clientController);

    void setLog(String s, boolean b);

    void disconnectUser(ClientController clientController);


    void startServer();

    void stopServer();
}
