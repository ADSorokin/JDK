package client.clientgui;

import client.controller.ClientController;
import client.controller.IClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;


public class ChatView extends JFrame implements IChatView {


    private IClientController clientController;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private JTextField loginField;
    private JPanel header;
    private JTextArea messageArea = new JTextArea();
    private JTextField inputField;

    public JPanel getHeader() {
        return header;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public ChatView() {

        enableEvents(java.awt.AWTEvent.WINDOW_EVENT_MASK);
        setResizable(false);
        setTitle("Chat Client");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLocation(200, 200);
        messageArea.setEditable(false);
        createPanel();
        setVisible(true);
    }

    private Component headerComponent() {
        header = new JPanel(new GridLayout(2, 3));
        header.setBorder(BorderFactory.createLineBorder(Color.black));
        JTextField ipField = new JTextField("127.0.0.1");
        JTextField portField = new JTextField("8180");
        loginField = new JTextField("Ivan Ivanovich");
        JPasswordField passwordField = new JPasswordField("123456");
        JButton btnLogin = new JButton("login");
        btnLogin.addActionListener(e -> clientController.connectToServer(loginField.getText()));
        header.add(ipField);
        header.add(portField);
        header.add(new JLabel("введите имя пароль"));
        header.add(loginField);
        header.add(passwordField);
        header.add(btnLogin);

        return header;
    }

    private void createPanel() {
        add(headerComponent(), BorderLayout.NORTH);
        add(createLog(), BorderLayout.CENTER);
        add(footerComponent(), BorderLayout.SOUTH);

    }

    private Component footerComponent() {
        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    clientController.message();
                }
            }
        });
        JButton btnSend = new JButton("send");
        btnSend.addActionListener(e -> clientController.message());
        panel.add(inputField);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    private Component createLog() {
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        return new JScrollPane(messageArea);
    }

    @Override
    public void appendLog(String text) {
        messageArea.append(text + "\n");
    }


    @Override
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }


    public void disconnectServer() {
        clientController.disconnectServer();
    }


    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.out.println(WindowEvent.WINDOW_CLOSING);
            disconnectServer();
            dispose();

        }

    }
}