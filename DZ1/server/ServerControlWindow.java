package server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ServerControlWindow extends JFrame {

    final private JTextArea logArea;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final String LOG_PATH = "src/server/logfile.txt";
    private boolean isServerWorking = false;
    ArrayList<ChatClient> clientList = new ArrayList<>();

    public ServerControlWindow() {

        setTitle("Communication server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton startButton = new JButton("Запустить сервер");
        JButton stopButton = new JButton("Остановить сервер");
        startButton.addActionListener(e -> {
            if (isServerWorking) {
                setLog("Сервер уже работает.",true);
            } else {
                isServerWorking = true;
                setLog("Сервер запущен.",true);
            }
        });

        stopButton.addActionListener(e -> {
            if (!isServerWorking) {
                setLog("Сервер не запущен",true);

            } else {
                isServerWorking = false;
                while (!clientList.isEmpty()) {
                    disconnectUser(clientList.get(clientList.size() - 1));
                }
                setLog("Сервер остановлен.",true);
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

    }


    private void saveLogInFile(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getLog()  {
        StringBuilder sb;
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_PATH))) {
            sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
        }

    public void setLog(String text) {
        saveLogInFile(text);
        logArea.append(text+"\n");
    }
    public void setLog(String text,boolean noRec) {
        if (noRec){
            logArea.append(text+"\n");
        }
        else
            saveLogInFile(text);

    }

    public boolean connectUser(ChatClient chatClient) {
        if (!isServerWorking){
            return false;
        }
        clientList.add(chatClient);
        return true;
    }

    public void disconnectUser(ChatClient chatClient){
        clientList.remove(chatClient);
        if (chatClient != null){
            chatClient.disconnectFromServer();
        }
    }

}


