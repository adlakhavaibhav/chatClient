package com.client;

import java.io.BufferedReader;
import java.net.SocketException;

/**
 * created by vaibhava on 15/04/18
 **/
public class PollServerMessage implements Runnable {

    private BufferedReader bufferedReader;
    private volatile boolean poll = true;

    public PollServerMessage(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void stopPolling(){
        poll = false;
    }

    public void run() {
        String serverMsg = null;

        try {
            while (poll && (serverMsg = bufferedReader.readLine()) != null) {
                System.out.println("Server says: " + serverMsg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
