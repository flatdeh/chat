package com.vlad.chat;

import com.vlad.chat.dao.ClientsList;
import com.vlad.chat.handler.ServiceHandler;
import com.vlad.chat.service.MessageService;

import java.io.*;
import java.net.Socket;

public class Connection implements Runnable {
    private final Socket socket;
    private ServiceHandler serviceHandler;
    private final ClientsList clientsList = ClientsList.getInstance();

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            serviceHandler = new ServiceHandler(reader, writer);
            while (true) {
                serviceHandler.handle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException rte) {
            try {
                socket.close();
                new MessageService().sendLeave(serviceHandler.getCurrentClient());
                clientsList.remove(serviceHandler.getCurrentClient());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
