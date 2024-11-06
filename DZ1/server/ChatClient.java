package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient extends JFrame {


    private ServerControlWindow server;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPanel header;
    private JTextField ipField;
    private JTextField portField;
    private JTextArea  messageArea = new JTextArea();
    private JTextField inputField;
    private JButton  btnSend;

    private JTextArea log;

    private boolean isConnected;
    private String name;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    public ChatClient(ServerControlWindow window) {

        this.server = window;
        setResizable(false);
        setTitle("Chat Client");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLocation(server.getX() - 200, server.getY()- 200);

        createPanel();
        setVisible(true);
        messageArea.setEditable(false);
    }



    private Component headerComponent() {
        header = new JPanel(new GridLayout(2, 3));
        header.setBorder(BorderFactory.createLineBorder(Color.black));
        ipField = new JTextField("127.0.0.1");
        portField = new JTextField("8180");
        loginField = new JTextField("Ivan Ivanovich");
        passwordField = new JPasswordField("123456");
        JButton btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                             connectToServer();
            }
        });
        header.add(ipField);
        header.add(portField);
        header.add(new JLabel("введите имя пароль"));
        header.add(loginField);
        header.add(passwordField);
        header.add(btnLogin);

        return header;
    }

    private void connectToServer() {
        if (server.connectUser(this)) {
            appendLog("Вы успешно подключились!\n");
             header.setVisible(false);
             isConnected = true;
            name = loginField.getText();
            String log = server.getLog();
            if (log != null) {
                appendLog(log);
            }
        } else {
            appendLog("Подключение не удалось");
        }
    }



    private void createPanel() {
        add(headerComponent(),BorderLayout.NORTH);
        add(createLog(),BorderLayout.CENTER);
        add(footerComponent(), BorderLayout.SOUTH);
    }

    private Component footerComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    message();
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        panel.add(inputField);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }
    public void message(){
        if (isConnected){
            String text = inputField.getText();
            if (!text.equals("")){
                server.setLog(name + ": " + text);
                inputField.setText("");
                messageArea.append(name + ": " + text+"\n");

            }
        } else {
            appendLog("Нет подключения к серверу");
        }

    }
    private Component createLog(){
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        return new JScrollPane(messageArea);
    }

    private void appendLog(String text){
        messageArea.append(text);
    }
    public void disconnectFromServer() {
        if (isConnected) {
            header.setVisible(true);
            isConnected = false;
            server.disconnectUser(this);
            appendLog("Вы были отключены от сервера!");
        }
    }


    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }

}
