package com.vlad.chat;

import com.vlad.chat.dao.ClientsList;
import com.vlad.chat.entity.Client;
import com.vlad.chat.entity.Message;
import com.vlad.chat.service.MessageService;
import org.junit.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Test
    public void testReceiveMessage() throws IOException {
        String userMessage = "Hello everyone!";
        BufferedReader reader = new BufferedReader(new CharArrayReader(userMessage.toCharArray()));
        BufferedWriter writerMock = mock(BufferedWriter.class);

        MessageService messageService = new MessageService();
        Client client = new Client("User", writerMock);
        Message message = messageService.receiveMessage(client, reader);

        assertEquals("User", message.getFromClient().getUserName());
        assertEquals("Hello everyone!", message.getMessageText());

        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String expectedTime = formatter.format(now);

        assertEquals(expectedTime, message.getMessageTime());
    }

    @Test
    public void testSend() throws IOException {
        BufferedWriter writerIraMock = mock(BufferedWriter.class);
        BufferedWriter writerVladMock = mock(BufferedWriter.class);
        BufferedWriter writerRomaMock = mock(BufferedWriter.class);
        Message message = new Message();
        Client ira = new Client("Ira", writerIraMock);
        ClientsList clientsList = ClientsList.getInstance();
        Client vlad = new Client("Vlad", writerVladMock);
        Client roma = new Client("Roma", writerRomaMock);
        clientsList.add(ira);
        clientsList.add(vlad);
        clientsList.add(roma);

        message.setMessageTime("14:29:33");
        message.setFromClient(ira);
        message.setMessageText("Send to all");

        MessageService messageService = new MessageService();
        messageService.send(message, ira);


        verify(writerVladMock).write("[" + message.getMessageTime() + ", " + ira.getUserName() + "]: " + message.getMessageText() + "\n");
        verify(writerVladMock).flush();
        verifyNoMoreInteractions(writerVladMock);

        verify(writerRomaMock).write("[" + message.getMessageTime() + ", " + ira.getUserName() + "]: " + message.getMessageText() + "\n");
        verify(writerRomaMock).flush();
        verifyNoMoreInteractions(writerRomaMock);

        verifyNoMoreInteractions(writerIraMock);
    }
}