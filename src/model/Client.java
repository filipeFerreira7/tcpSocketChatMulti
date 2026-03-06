package model;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost",5000);

        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

        PrintWriter out =
                new PrintWriter(socket.getOutputStream(),true);

        BufferedReader keyboard =
                new BufferedReader(
                        new InputStreamReader(System.in));

        String key = in.readLine();

        System.out.println("Conectado ao servidor");

        new Thread(() -> {

            try{

                while(true){

                    String encrypted = in.readLine();

                    String message =
                            CryptoUtils.decrypt(encrypted,key);

                    System.out.println(message);
                }

            }catch(Exception e){}

        }).start();

        while(true){

            String msg = keyboard.readLine();

            Map<String,Object> data = new HashMap<>();

            data.put("id",key);
            data.put("mensagem",msg);
            data.put("timestamp",System.currentTimeMillis());

            String message = data.toString();

            String encrypted =
                    CryptoUtils.encrypt(message,key);

            out.println(encrypted);
        }
    }
}