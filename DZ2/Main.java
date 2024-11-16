
import client.clientgui.ChatView;
import client.controller.ClientController;
import server.controller.ServerController;

import server.repo.IStorage;

import server.repo.Storage;
import server.servergui.ServerView;

public class Main {
    public static void main(String[] args) {
        IStorage storage= new Storage();
        ServerController controller = new ServerController(new ServerView(),storage);
    new ClientController(new ChatView(),controller,storage);
    new ClientController(new ChatView(),controller,storage);

    }
}