package risorse.Client;

import org.w3c.dom.*;

import risorse.Server.ParserXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class Client{

    private static final String CLOSE = "#"; 
    private static final String ASK = "/";
    public static void main(String[] args) throws IOException{
        Scanner keyboard = new Scanner(System.in);

        Document document = ParserXML.loadDocument("config_client.xml");
        Element client = document.getDocumentElement();

        String ip = client.getElementsByTagName("ip").item(0).getTextContent();
        int port = Integer.parseInt(client.getElementsByTagName("port").item(0).getTextContent());

        Socket s = new Socket(ip, port);
        //crea un flusso di output verso il server
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        //crea un flusso di input dal server
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String messaggioServer="";
        //il client comunica con il server fin quando non riceve la stringa "CLOSE"
        while(true){
            messaggioServer = input.readLine();
            //se il server invia la stringa "CLOSE" allora esce dal ciclo while
            if(messaggioServer.equals(CLOSE)) 
                break;

            //se il server invia la stringa "ASK" allora il cliente deve scrivere una risposta
            if(messaggioServer.equals(ASK)){
                System.out.print("Risposta: ");
                String rispostaClient = keyboard.nextLine();
                out.println(rispostaClient);
            }
            //altrimenti il client visualizza il messaggio ricevuto dal server
            else
                System.out.println(messaggioServer);
        }
        out.close();
        input.close();
        s.close();
        System.exit(0);
        keyboard.close();
    }
}