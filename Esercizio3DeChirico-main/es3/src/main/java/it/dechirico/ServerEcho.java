package it.dechirico;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerEcho{
    public static void main (String[] args){
    int porta = 5000;
    try(ServerSocket s1 = new ServerSocket(porta)){
        System.out.println("Il serer è in ascolto sulla porta " + porta);

        try(Socket c1 = s1.accept();
        InputStream message = c1.getInputStream();
        OutputStream newMessage = c1.getOutputStream();){
        byte[] buffer = new byte[1024];

                // byteLetti conterrà il numero di byte ricevuti 
                int byteLetti = message.read(buffer);

                if (byteLetti != -1) {
                     String stringaRicevuta = new String(buffer, 0, byteLetti).trim();
    
                     // Dividiamo la stringa usando lo spazio come separatore, quindi un operazione separata da spazi diventa un array
                    String[] parti = stringaRicevuta.split(" ");
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
                         risultato= 0;
                        }

                    }
                  String rispostaTestuale = String.valueOf(risultato);
                    if (attempt) {
                newMessage.write(errore.getBytes());
                    } else {
                    newMessage.write(rispostaTestuale.getBytes());
                    }
                    newMessage.flush();
                }
                }
        


        } catch (Exception e) {

        
        
        
    
    }
    }
}

    



