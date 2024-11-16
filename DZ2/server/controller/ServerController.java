package server.controller;

import client.controller.ClientController;
import client.controller.IClientController;
import server.repo.IStorage;
import server.servergui.IServerView;

import java.util.ArrayList;

public class ServerController implements IServerController {


    private final IStorage storage;
    private final IServerView serverView;
    ArrayList<IClientController> clientList;
    private boolean isServerWorking = false;


    public ServerController(IServerView serverView, IStorage storage) {
        this.storage = storage;
        this.serverView = serverView;
        clientList = new ArrayList<>();
        serverView.setServerController(this);

    }

    public void startServer() {
        if (isServerWorking) {
            setLog("Сервер уже работает.", true);
        } else {
            isServerWorking = true;
            setLog("Сервер запущен.", true);
        }
    }

    public void stopServer() {
        if (!isServerWorking) {
            setLog("Сервер не запущен", true);

        } else {
            isServerWorking = false;
            while (!clientList.isEmpty()) {
                disconnectUser((ClientController) clientList.get(clientList.size() - 1));
            }
            setLog("Сервер остановлен.", true);
        }
    }

    public void setLog(String text, boolean noRec) {
        if (!noRec) {
            serverView.show(text);
            pushAll(text);
            storage.saveData(text);
        } else serverView.show(text);
  //
    }
    private void pushAll(String text){
        for (IClientController clientController : clientList){
            clientController.push(text);
        }
    }

    public boolean connectUser(ClientController chatClient) {
        if (!isServerWorking) {
            return false;
        }
        clientList.add(chatClient);
        return true;
    }

    @Override
    public void disconnectUser(ClientController chatClient) {
        clientList.remove(chatClient);
        if (chatClient != null) {
            setLog(chatClient.getName() + ":" + "Отключился от беседы.", true);
            chatClient.disconnectFromServer();

        }
    }


}


