package com.vlad.chat;

import org.junit.Test;

import java.io.IOException;

public class ChatServerTest {

    @Test
    public void testStart() throws IOException {
        ChatServer chat = new ChatServer(3000);
        chat.start();
    }
}