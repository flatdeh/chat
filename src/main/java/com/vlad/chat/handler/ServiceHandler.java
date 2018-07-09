package com.vlad.chat.handler;

import com.vlad.chat.Authorization;
import com.vlad.chat.service.MessageService;
import com.vlad.chat.entity.Client;
import com.vlad.chat.entity.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ServiceHandler {
    private Authorization authorization;
    private MessageService messageService;
    private Client currentClient = null;
    private BufferedReader reader;

    public ServiceHandler(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        authorization = new Authorization(reader, writer);
        messageService = new MessageService();
    }

    public void handle() throws IOException {
        if (currentClient != null) {
            Message message = messageService.receiveMessage(currentClient, reader);
            if (message.getMessageText() == null) {
                throw new RuntimeException("Client disconnected!");
            }
            messageService.send(message, currentClient);
        } else {
            currentClient = authorization.auth();
            if (currentClient != null) {
                messageService.sendJoined(currentClient);
            }
        }
    }

    public Client getCurrentClient() {
        return currentClient;
    }
}
