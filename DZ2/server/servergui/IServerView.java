package server.servergui;

import server.controller.ServerController;

public interface IServerView {
    void setServerController(ServerController serverController);

    void show(String text);

}
