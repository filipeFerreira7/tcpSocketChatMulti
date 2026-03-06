package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private String key;
    private String clientId;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try{
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(
                    socket.getOutputStream(),true);
            clientId = UUID.randomUUID().toString();
            key = UUID.randomUUID().toString();

            System.out.println("Cliente conectado: " + clientId);

            out.println(key);

            while(true){
                String encrypted = in.readLine();

                String decrypted =
                        CryptoUtils.decrypt(encrypted,key);

                System.out.println("Mensagem recebida: "+ decrypted);

                String processed =
                        "Cliente " + clientId +": " + decrypted;

                String encryptedBroadcast =
                CryptoUtils.encrypt(processed,key);

                Server.broadcast(encryptedBroadcast,this);
            }

        }catch (Exception e){
            System.out.println("Cliente desconectado");
        }
    }
    public void sendMessage(String message){
        out.println(message);
    }
}
