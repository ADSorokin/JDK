package server;

public class Main {
    public static void main(String[] args) {

        ServerControlWindow window = new ServerControlWindow();
        new ChatClient(window);
        new ChatClient(window);
        window.setVisible(true);
    }
}