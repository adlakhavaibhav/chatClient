package com.client;

import java.io.IOException;

/**
 * created by vaibhava on 15/04/18
 **/
public interface ChatClient {


    void connect();

    String joinRoom(String roomName) throws IOException;

    String sendMessage(String roomName, String messge) throws IOException;

    String getNextMessageFromServer() throws IOException;

    void shutDown() throws IOException;


}
