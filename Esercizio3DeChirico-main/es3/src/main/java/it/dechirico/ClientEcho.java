package it.dechirico;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientEcho {

    public static void main(String[] args) {
        int porta = 5000;
        String host = "localhost";

        System.out.println(
                "Inserisci il messaggio da inviare al server: una operazione matematica con cifre e operandi separati da spazi");
        Scanner scan = new Scanner(System.in);
        String messaggioInviato = scan.nextLine();

        try (Socket sock = new Socket(host, porta);
                OutputStream newMessage = sock.getOutputStream();
                InputStream responseMessage = sock.getInputStream()) {

            // Invio del messaggio
            newMessage.write(messaggioInviato.getBytes());
            newMessage.flush();
            System.out.println("Messaggio inviato. In attesa di risposta...");

            // Ascolto risposta
            byte[] buffer = new byte[1024];
            int byteLetti = responseMessage.read(buffer);

            if (byteLetti != -1) {
                // Convertiamo i bytes
                String messaggioRicevuto = new String(buffer, 0, byteLetti);
                System.out.println("Risposta dal server: " + messaggioRicevuto);
            } else {
                System.out.println("errore server");
            }

        } catch (IOException e) {
            System.err.println("Errore di connessione");
        }
    }
}
