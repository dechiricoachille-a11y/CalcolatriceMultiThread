package it.dechirico;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerEcho {
    public static void main(String[] args) {
        int porta = 5000;

        int maxClientSimultanei = 5;
        Semaphore semaforo = new Semaphore(maxClientSimultanei);

        try (ServerSocket s1 = new ServerSocket(porta)) {
            System.out.println("Il serer è in ascolto sulla porta " + porta);
            while (true) {
                while (true) {
                    Socket clientSocket = s1.accept();
                    System.out.println("Nuovo client connesso alla socket");
                    ClientHandler CH = new ClientHandler(clientSocket, semaforo);
                    Thread thread1 = new Thread(CH);
                    thread1.start();
                }

            }

        } catch (Exception e) {
            System.err.println("Errore del server: " + e.getMessage());
        }
    }
}
