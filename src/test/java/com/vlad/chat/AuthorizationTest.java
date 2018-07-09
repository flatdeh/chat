package com.vlad.chat;

import com.vlad.chat.dao.ClientsList;
import com.vlad.chat.entity.Client;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayReader;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthorizationTest {

    @Test
    public void testAuth() throws IOException {
        String authUserName = "Vlad";
        BufferedReader readerMock = new BufferedReader(new CharArrayReader(authUserName.toCharArray()));
        BufferedWriter writerMock = mock(BufferedWriter.class);
        Authorization authorization = new Authorization(readerMock, writerMock);

        Client client = authorization.auth();

        assertNotNull(client);
        assertEquals(authUserName, client.getUserName());
        assertEquals(writerMock, client.getConnection());

        verify(writerMock).write("Hello, enter your username: \n");
        verify(writerMock).write("Hello " + client.getUserName() + "! Welcome to chat!\n");
    }

    @Test
    public void testAuthUserNameExists() throws IOException {
        String authUserName = "Vlad";
        BufferedReader readerMock = new BufferedReader(new CharArrayReader(authUserName.toCharArray()));
        BufferedWriter writerMock = mock(BufferedWriter.class);
        ClientsList clientsList = ClientsList.getInstance();
        clientsList.add(new Client(authUserName,mock(BufferedWriter.class)));
        Authorization authorization = new Authorization(readerMock, writerMock);

        Client client = authorization.auth();

        assertNull(client);

        verify(writerMock).write("Hello, enter your username: \n");
        verify(writerMock).write("Username \"" + authUserName + "\" already exist, enter another: \n");
    }

    @Test
    public void testAuthUserNameNotValidEmpty() throws IOException {
        String authUserName = "";
        BufferedReader readerMock = new BufferedReader(new CharArrayReader(authUserName.toCharArray()));
        BufferedWriter writerMock = mock(BufferedWriter.class);
        Authorization authorization = new Authorization(readerMock, writerMock);

        Client client = authorization.auth();

        assertNull(client);
        verify(writerMock).write("Hello, enter your username: \n");
        verify(writerMock).write("Username not valid\n");
    }

    @Test
    public void testAuthUserNameNotValidLengthLessThanThree() throws IOException {
        String authUserName = "Vl";
        BufferedReader readerMock = new BufferedReader(new CharArrayReader(authUserName.toCharArray()));
        BufferedWriter writerMock = mock(BufferedWriter.class);
        Authorization authorization = new Authorization(readerMock, writerMock);

        Client client = authorization.auth();

        assertNull(client);
        verify(writerMock).write("Hello, enter your username: \n");
        verify(writerMock).write("Username not valid\n");
    }

    @Test
    public void testAuthUserNameNotValidLengthLargerThanTwenty() throws IOException {
        String authUserName = "dfdfdfdfdfdfdfdfdfdfdf";
        BufferedReader readerMock = new BufferedReader(new CharArrayReader(authUserName.toCharArray()));
        BufferedWriter writerMock = mock(BufferedWriter.class);
        Authorization authorization = new Authorization(readerMock, writerMock);

        Client client = authorization.auth();

        assertNull(client);
        verify(writerMock).write("Hello, enter your username: \n");
        verify(writerMock).write("Username not valid\n");
    }
}