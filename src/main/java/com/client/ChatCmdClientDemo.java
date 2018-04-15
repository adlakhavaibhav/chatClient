package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * created by vaibhava on 15/04/18
 **/
public class ChatCmdClientDemo {

    public static void main(String[] args) throws IOException {

        ChatCmdClient client = new ChatCmdClient("127.0.0.1", 9095);
        client.connect();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            String[] tokenArr = parseInput(userInput);
            //String[] tokenArr = userInput.split("\\s");
            String token = tokenArr[0];
            if ("JOIN".equalsIgnoreCase(token)) {
                client.joinRoom(tokenArr[1]);
            } else if ("SEND".equalsIgnoreCase(token)) {
                client.sendMessage(tokenArr[1], tokenArr[2]);
            }

        }

        client.shutDown();
        stdIn.close();
    }


    private static String[] parseInput(String input) {
        try {
            String[] result = new String[3];

            int idx = input.indexOf(" ");
            String firstToken = input.substring(0, idx);
            firstToken = firstToken.trim();
            result[0] = firstToken;
            if ("JOIN".equalsIgnoreCase(firstToken)) {
                String secondToken = input.substring(input.indexOf("<") +1, input.indexOf(">"));
                result[1] = secondToken;
                return result;
            } else if ("SEND".equalsIgnoreCase(firstToken)) {
                String secondToken = input.substring(input.indexOf("<")+ 1 , input.indexOf(">"));
                String thirdToken = input.substring(input.lastIndexOf("<")+1, input.lastIndexOf(">"));
                result[1] = secondToken;
                result[2] = thirdToken;
                return result;
            } else {
                System.err.println("Invalid input");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
