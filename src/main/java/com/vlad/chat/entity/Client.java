package com.vlad.chat.entity;

import java.io.BufferedWriter;

public class Client {
    private String userName;
    private BufferedWriter connection;

    public Client(String userName, BufferedWriter connection) {
        this.userName = userName;
        this.connection = connection;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BufferedWriter getConnection() {
        return connection;
    }

    public void setConnection(BufferedWriter connection) {
        this.connection = connection;
    }
}
