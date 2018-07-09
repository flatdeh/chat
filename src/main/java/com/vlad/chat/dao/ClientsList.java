package com.vlad.chat.dao;

import com.vlad.chat.entity.Client;

import java.io.BufferedWriter;
import java.util.HashMap;

public final class ClientsList {
    private static volatile ClientsList instance;
    private HashMap<String, BufferedWriter> clientsList = new HashMap<>();

    public static ClientsList getInstance() {
        if (instance == null) {
            synchronized (ClientsList.class) {
                if (instance == null) {
                    instance = new ClientsList();
                }
            }
        }
        return instance;
    }

    public synchronized Boolean add(Client client) {
        return clientsList.putIfAbsent(client.getUserName(), client.getConnection()) == null;
    }

    public synchronized void remove(Client client) {
        clientsList.remove(client.getUserName());
    }

    public synchronized HashMap<String, BufferedWriter> getClientsList() {
        return clientsList;
    }
}
