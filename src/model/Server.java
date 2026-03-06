package model;

import java.net.*;
import java.util.*;

public class Server {

    public static List<ClientHandler> clients =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Servidor iniciado...");

        while(true){

            Socket socket = serverSocket.accept();

            ClientHandler handler = new ClientHandler(socket);

            clients.add(handler);

            handler.start();
        }
    }

    public static void broadcast(String message, ClientHandler sender){

        for(ClientHandler client : clients){

            if(client != sender){

                client.sendMessage(message);
            }
        }
    }
}