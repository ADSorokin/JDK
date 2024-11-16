package server.servergui;

import server.controller.IServerController;
import server.controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class ServerView extends JFrame implements IServerView {

    final JButton startButton;
    final JButton stopButton;
    private final JTextArea logArea;
    private IServerController serverController;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    @Override
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    public ServerView() {
        setTitle("Communication server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        startButton = new JButton("Запустить сервер");
        stopButton = new JButton("Остановить сервер");
        startButton.addActionListener(e -> serverController.startServer());
        stopButton.addActionListener(e -> serverController.stopServer());
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);

    }

    @Override
    public void show(String text) {
        logArea.append(text + "\n");
    }
}
