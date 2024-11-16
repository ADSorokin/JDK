package client.controller;

import client.clientgui.IChatView;
import server.controller.IServerController;
import server.repo.IStorage;


public class ClientController implements IClientController {
    private boolean isConnected;

    @Override
    public String getName() {
        return name;
    }

    private String name;
    private final IStorage storage;

    private IServerController server;
    private IChatView chatView;

    public ClientController(IChatView chatView, IServerController server, IStorage storage) {
        this.chatView = chatView;
        this.server = server;
        this.storage = storage;
        chatView.setClientController(this);
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)) {
            chatView.appendLog("Вы успешно подключились!\n");
            chatView.getHeader().setVisible(false);
            isConnected = true;
            String log = storage.getData();
            if (log != null) {
                chatView.appendLog(log);
            }
        } else {
            chatView.appendLog("Подключение не удалось");
            isConnected = false;
        }
        return isConnected;
    }

    @Override
    public void disconnectFromServer() {
        if (isConnected) {
            chatView.getHeader().setVisible(true);
            isConnected = false;
            chatView.appendLog("Вы были отключены от сервера!");
        }
    }

    public void message() {
        if (isConnected) {
            String text = chatView.getInputField().getText();
            if (!text.equals("")) {
                server.setLog(name + ": " + text, false);
                chatView.getInputField().setText("");


            }
        } else {
            chatView.appendLog("Нет подключения к серверу");
        }
    }

    public void push(String text) {
        chatView.appendLog(text);
    }

    public void disconnectServer() {
        server.disconnectUser(this);
    }
}
