package com.client;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

/**
 * created by vaibhava on 15/04/18
 **/
public class ChatCmdClientTest {


    private static ChatClient chatClient;
    private static ChatClient chatClientTwo;

    public ChatCmdClientTest() {
        chatClient = new ChatCmdClient("127.0.0.1", 9095, false);
        chatClient.connect();
        chatClientTwo = new ChatCmdClient("127.0.0.1", 9095, false);
        chatClientTwo.connect();
    }

    @Test
    public void testJoinNewRoom() {
        try {
            String message = chatClient.joinRoom("room 1");
            assertEquals("OK", message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testJoinExistingRoom() {
        try {
            String message = chatClient.joinRoom("room 1");
            assertEquals("OK", message);
            String message2 = chatClientTwo.joinRoom("room 1");
            assertEquals("OK", message2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void joinClientsAndTestExchange() {
        try {
            String message = chatClient.joinRoom("room 1");
            assertEquals("OK", message);
            String message2 = chatClientTwo.joinRoom("room 1");
            assertEquals("OK", message2);
            String sendMessageResp = chatClient.sendMessage("room 1", "Hello room one");
            assertEquals("Hello room one", sendMessageResp);
            String messageForTwo = chatClientTwo.getNextMessageFromServer();
            assertEquals("Hello room one", sendMessageResp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void sendMessageToNonJoinedRoom() {
        try {
            String message = chatClient.joinRoom("room 1");
            assertEquals("OK", message);
            String message2 = chatClientTwo.joinRoom("room 2");
            assertEquals("OK", message2);
            String sendMessageResp = chatClient.sendMessage("room 2", "Hello room two");
            assertEquals("Please join room 2 before trying to send messages", sendMessageResp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void sendMessageToNonExistentRoom() {
        try {
            String sendMessageResp = chatClient.sendMessage("room 3", "Hello room one");
            assertEquals("Room with specified name does not exist", sendMessageResp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @AfterClass
    public static void tear() {
        if (null != chatClient) {
            try {
                chatClient.shutDown();
                chatClientTwo.shutDown();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
