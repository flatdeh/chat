package com.vlad.chat;

import com.vlad.chat.dao.ClientsList;
import com.vlad.chat.entity.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Authorization {
    private BufferedReader reader;
    private BufferedWriter writer;
    private ClientsList clientsList = ClientsList.getInstance();

    public Authorization(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public Client auth() throws IOException {
        writer.write("Hello, enter your username: \n");
        writer.flush();

        String userName = reader.readLine();
        if (!validate(userName)) {
            writer.write("Username not valid\n");
            writer.flush();
            return null;
        } else {
            Client client = new Client(userName, writer);
            if (clientsList.add(client)) {
                writer.write("Hello " + userName + "! Welcome to chat!\n");
                writer.flush();
                return client;
            } else {
                writer.write("Username \"" + userName + "\" already exist, enter another: \n");
                writer.flush();
                return null;
            }
        }
    }

    private boolean validate(String userName) {
        if (userName == null) {
            return false;
        }

        int userNameLength = userName.length();

        if (userNameLength <= 2 || userNameLength > 20) {
            return false;
        }

        int count = 0;
        for (int i = 0; i < userNameLength; i++) {
            if (userName.charAt(i) == ' ') {
                count++;
            }
        }

        return count != userNameLength;
    }
}
