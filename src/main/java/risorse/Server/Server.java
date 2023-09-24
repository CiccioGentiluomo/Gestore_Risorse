package risorse.Server;

import org.w3c.dom.*;
import java.io.*;
import java.net.*;
public class Server{
    public static void main(String[] args) throws IOException{
        Thread thread;
        boolean esci=false;
        Document document = ParserXML.loadDocument("config_server.xml");
        Element root = document.getDocumentElement();

        Element db=(Element)root.getElementsByTagName("db").item(0);
        String dbName = db.getElementsByTagName("dbname").item(0).getTextContent();
        String uri = db.getElementsByTagName("uri").item(0).getTextContent();

        Element server=(Element)root.getElementsByTagName("server").item(0);
        int port = Integer.parseInt(server.getElementsByTagName("port").item(0).getTextContent());

        ServerSocket listener = new ServerSocket(port);
        GestoreDB gestoreDB = new GestoreDB(uri,dbName);

        System.out.println("Server is running");
        while (!esci){//il server rimane sempre in ascolto per qualsiasi connessione
            Socket socket = listener.accept();
            System.out.println("Connection established");
            GestoreClient gestoreClient= new GestoreClient(socket, gestoreDB);
            thread = new Thread(gestoreClient);
            thread.start();
        }
        listener.close();
    }
}