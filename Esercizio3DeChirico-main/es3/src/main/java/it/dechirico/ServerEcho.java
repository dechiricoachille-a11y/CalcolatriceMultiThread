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
            while(true){
                Socket clienSocket = s1.accept();

                ClientHandler CH = new ClientHandler(clienSocket);
            }



        
    }
}
}
    



