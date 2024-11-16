package client.clientgui;

import client.controller.ClientController;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public interface IChatView {

    void appendLog(String text);

    void setClientController(ClientController clientController);

    Component getHeader();

    JTextComponent getInputField();

    JTextArea getMessageArea();
}
