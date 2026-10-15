package it.dechirico;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ClientHandler implements Runnable {
    //Riferimento privato alla socket del client connesso
    private final Socket clientSocket;
    private final Semaphore semaforo; 
    public ClientHandler(Socket clientSocket, Semaphore semaforo) {
        this.clientSocket = clientSocket;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        try (InputStream message = clientSocket.getInputStream();
             OutputStream newMessage = clientSocket.getOutputStream()) {
            
            byte[] buffer = new byte[1024];
            int byteLetti = message.read(buffer);

            if (byteLetti != -1) {
                String stringaRicevuta = new String(buffer, 0, byteLetti).trim();
                String[] parti = stringaRicevuta.split(" ");
                
                // Gestione minima di sicurezza se l'input non contiene 3 parti
                if (parti.length < 3) {
                    newMessage.write("Formato non valido. Usa: numero operatore numero".getBytes());
                    newMessage.flush();
                    return;
                }

                double num1 = Double.parseDouble(parti[0]);
                String operatore = parti[1];
                double num2 = Double.parseDouble(parti[2]);
                
                boolean attempt = false;
                double risultato = 0;
                String errore = "operazione non valida";

                switch (operatore) {
                    case "+": risultato = num1 + num2; break;
                    case "-": risultato = num1 - num2; break;
                    case "*": risultato = num1 * num2; break;
                    case "/": 
                        if (num2 != 0) {
                            risultato = num1 / num2;
                        } else {
                            attempt = true;
                            errore = "Errore: Divisione per zero";
                        }
                        break;
                    default:
                        attempt = true;
                        break;
                }

                if (attempt) {
                    newMessage.write(errore.getBytes());
                } else {
                    String rispostaTestuale = String.valueOf(risultato);
                    newMessage.write(rispostaTestuale.getBytes());
                }
                newMessage.flush();
            }
        } catch (Exception e) {
            System.err.println("Errore nella gestione del client: " + e.getMessage());
        } finally { 
            semaforo.release();
            try {
                clientSocket.close();
            } catch (Exception e) {
                System.err.println("Errore nella chiusura della socket: " + e.getMessage());
            }
        }
    }
}
