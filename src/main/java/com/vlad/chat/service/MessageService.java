package com.vlad.chat.service;

import com.vlad.chat.dao.ClientsList;
import com.vlad.chat.entity.Client;
import com.vlad.chat.entity.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class MessageService {
    private ClientsList clientsList = ClientsList.getInstance();

    public Message receiveMessage(Client currentClient, BufferedReader reader) throws IOException {
        String messageText = reader.readLine();
        Message message = new Message();
        message.setMessageText(messageText);

        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String time = formatter.format(now);
        message.setMessageTime(time);

        message.setFromClient(currentClient);
        return message;
    }

    public void send(Message message, Client fromClient) throws IOException {
        for (Map.Entry<String, BufferedWriter> entry : clientsList.getClientsList().entrySet()) {
            String userName = entry.getKey();
            BufferedWriter bufferedWriter = entry.getValue();
            if (!fromClient.getUserName().equals(userName)) {
                bufferedWriter.write("[" + message.getMessageTime() + ", " + fromClient.getUserName() + "]: " + message.getMessageText() + "\n");
                bufferedWriter.flush();
            }

        }
    }

    public void sendJoined(Client fromClient) throws IOException {
        for (Map.Entry<String, BufferedWriter> entry : clientsList.getClientsList().entrySet()) {
            String userName = entry.getKey();
            BufferedWriter bufferedWriter = entry.getValue();
            if (!fromClient.getUserName().equals(userName)) {
                bufferedWriter.write(fromClient.getUserName() + " joined to chat!\n");
                bufferedWriter.flush();
            }

        }
    }


    public void sendLeave(Client fromClient) throws IOException {
        for (Map.Entry<String, BufferedWriter> entry : clientsList.getClientsList().entrySet()) {
            String userName = entry.getKey();
            BufferedWriter bufferedWriter = entry.getValue();
            if (!fromClient.getUserName().equals(userName)) {
                bufferedWriter.write(fromClient.getUserName() + " leave chat!\n");
                bufferedWriter.flush();
            }

        }
    }
}


