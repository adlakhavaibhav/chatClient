package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * created by vaibhava on 15/04/18
 **/
public class ChatCmdClient implements ChatClient {

    private String serverIp;
    private int serverPort;
    private boolean pollMessages;
    private Socket clientSocket;

    private PollServerMessage pollRunnable;
    private Thread pollThread;

    private PrintWriter out = null;
    private BufferedReader in = null;

    public ChatCmdClient(String serverIp, int serverPort) {
        this(serverIp, serverPort, true);
    }

    public ChatCmdClient(String serverIp, int serverPort, boolean pollMessages) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.pollMessages = pollMessages;
    }

    public void connect() {
        try {
            clientSocket = new Socket(serverIp, serverPort);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            if (pollMessages) {
                pollRunnable = new PollServerMessage(in);
                pollThread = new Thread(pollRunnable);
                pollThread.start();
            }
            System.out.println("Client connected to key value server at : " + serverIp + " port: " + serverPort);

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverIp);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't connect to: " + serverIp);
            System.exit(1);
        }
    }


    public String joinRoom(String roomName) throws IOException {
        out.println("JOIN <" + roomName + ">\n");
        return serverResponse();
    }

    public String sendMessage(String roomName, String messge) throws IOException {
        out.println("SEND <" + roomName + "> <" + messge + ">\n");
        return serverResponse();
    }


    public void shutDown() throws IOException {

        if (null != pollThread && null != pollRunnable) {
            pollRunnable.stopPolling();
            try {
                pollThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        out.close();
        in.close();

        clientSocket.close();
    }

    private String serverResponse() throws IOException {
        if (!pollMessages) {
            return getNextMessageFromServer();
        }
        return "";

    }


    public String getNextMessageFromServer() throws IOException {
        String serverMsg = null;
        while ((serverMsg = in.readLine()) != null) {
            return serverMsg;
        }
        return null;
    }
}
