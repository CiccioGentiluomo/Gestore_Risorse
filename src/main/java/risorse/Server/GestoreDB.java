package risorse.Server;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import risorse.Data.Amministratore;
import risorse.Data.MacchinaVirtuale;
import risorse.Data.Utente;

import static com.mongodb.client.model.Filters.*;
import org.bson.types.ObjectId;

public class GestoreDB {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;
    MongoCollection<Document> macchineVirtuali;
    Bson filter;
    Document doc;

    public GestoreDB(String uri, String dbname) {
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(dbname);

        /*
        collection = database.getCollection("Utenti");
        
        collection.find().forEach(doc -> System.out.println(doc.toJson()));
        System.out.println();

        collection.insertOne(new Document("nome", "Pippo").append("cognome", "Rossi"));
        collection.insertOne(new Document("nome", "Pluto").append("cognome", "Bianchi").append("username", "a").append("password", "a"));
        collection.find().forEach(doc -> System.out.println(doc.toJson()));
        System.out.println();
        // Definisci il filtro per la query
        filter = Filters.eq("nome", "Pippo");

        // Esegui la query e proietta solo i campi desiderati (_id escluso)
        Bson projection = exclude("nome", "_id");

        // Esegui la query
        //Document doc = collection.find(filter).projection(projection).first();

        // Visualizza i risultati
        //System.out.println(doc.toJson());

        //collection.deleteMany(or(eq("nome", "Pippo"), eq("cognome", "Rossi"), eq("cognome", "Bianchi")));
        collection.find().forEach(doc -> System.out.println(doc.toJson()));
        
        //collection.deleteMany(new Document());
        collection.find().forEach(doc -> System.out.println(doc.toJson()));

        macchineVirtuali = database.getCollection("MacchineVirtuali");
        macchineVirtuali.insertOne(new Document("nomeRisorsa", "prima").append("GPU", "nvidia").append("RAM", 32).append("CPU", "Intel")
            .append("memoriadisco", 256).append("memoriasecondaria", false).append("periodouso", 3000).append("inUso", false));
        macchineVirtuali.find().forEach(doc -> System.out.println(doc.toJson()));

        macchineVirtuali.insertOne(new Document("nomeRisorsa", "prima").append("GPU", "nvidia").append("RAM", 32).append("CPU", "Intel")
            .append("memoriadisco", 256).append("memoriasecondaria", false).append("periodouso", 3000).append("inUso", false));

        //macchineVirtuali.insertOne(new Document("nomeRisorsa", "prima").append("GPU", "nvidia").append("RAM", 32).append("CPU", "Intel")
            //.append("memoriadisco", 256).append("memoriasecondaria", false).append("periodouso", 3000).append("inUso", true));

        ArrayList<ObjectId> array2=new ArrayList<ObjectId>();
        array2.add(new ObjectId("5f9618416489200001000000"));
        array2.add(new ObjectId("5f9618416489200001000001"));
        
        collection.insertOne(new Document("nome", "Pippo").append("cognome", "Rossi").append("listaid_MacchineVirtuali", array2));
        collection.find().forEach(doc -> System.out.println(doc.toJson()));

        /*filter = and(eq("username", "ab"), eq("password", "ab"));
        doc = collection.find(filter).first();
        Date data1=doc.getDate("dataDiNascita");
        System.out.println(doc.getDate("dataDiNascita"));
        
        Instant instant = data1.toInstant();

        // Estrai la data in formato LocalDate
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate);
        LocalDate dataDiNascita=LocalDate.of(1, 1, 1);
        collection.deleteMany(new Document());
        ArrayList<ObjectId> array=new ArrayList<ObjectId>();
        array.add(new ObjectId("5f9618416489200001000000"));
        array.add(new ObjectId("5f9618416489200001000001"));
        
        collection.insertOne(new Document("nome", "Pippo").append("cognome", "Rossi").append("listaId_MacchineVirtuali", array).append("username", "ab").append("password", "ab").append("dataDiNascita", 0001-01-01));



        macchineVirtuali = database.getCollection("MacchineVirtuali");

        //controllo se esiste una macchina nella posizione scelta
        // Creo un cursore per iterare attraverso la collezione
        MongoCursor<Document> cursor = macchineVirtuali.find(eq("inUso", false)).iterator();

        int indiceCorrente = 1;
        boolean elementoTrovato = false;

        // Itero finché non raggiungi l'elemento desiderato o raggiungi la fine della collezione        
        while (cursor.hasNext() && indiceCorrente<1) {
            
            doc=cursor.next();
            System.out.println("\n"+doc);
            System.out.println(cursor.hasNext());
            indiceCorrente++;
        }
        if (indiceCorrente == 1) {
            elementoTrovato = true;
        }
        if(cursor.hasNext())
            System.out.println(cursor.next());
        // Verifico se l'elemento è stato trovato o se non esiste
        if (!elementoTrovato){
            System.out.println("\nNon esiste una macchina virtuale con questo numero");
        }

        if (macchineVirtuali.find(eq("inUso",false)).first()==null)
            System.out.println("Non esiste una macchina virtuale disponibile");
        System.out.println(isMacchineLibere());

        */


        /*
        macchineVirtuali = database.getCollection("MacchineVirtuali");
        macchineVirtuali.insertOne(new Document("nomeRisorsa", "terza").append("GPU", "nvidia").append("RAM", 32).append("CPU", "Intel")
            .append("memoriadisco", 256).append("memoriasecondaria", false).append("periodouso", 1).append("inUso", false));
        macchineVirtuali.insertOne(new Document("nomeRisorsa", "seconda").append("GPU", "nvidia").append("RAM", 32).append("CPU", "Intel")
            .append("memoriadisco", 256).append("memoriasecondaria", false).append("periodouso", 1).append("inUso", false));
        */
    }


    //creo un metodo che registra un utente controllando se esiste già
    public boolean registraUtente(Utente utente) {
        collection = database.getCollection("Utenti");

        if(collection.find(eq("username", utente.getUsername())).first()!= null){
            return false;
        }
        doc = new Document("nome", utente.getNome()).append("cognome", utente.getCognome()).append("dataDiNascita", utente.getDataDiNascita())
            .append("username", utente.getUsername()).append("password", utente.getPassword());
        collection.insertOne(doc);
        return true;
    }

    public boolean registraAmministratore(Amministratore amministratore) {
        collection = database.getCollection("Utenti");

        if(collection.find(eq("username", amministratore.getUsername())).first()!= null){
            return false;
        }
        doc = new Document("nome", amministratore.getNome()).append("cognome", amministratore.getCognome()).append("dataDiNascita", amministratore.getDataDiNascita())
            .append("username", amministratore.getUsername()).append("password", amministratore.getPassword()).append("amministratore", true);
        collection.insertOne(doc);
        return true;
    }


    //metodo che verifica il login
    public Utente login(String username, String password) {
        collection = database.getCollection("Utenti");
        filter = and(eq("username", username), eq("password", password));
        doc = collection.find(filter).first();
        // Se il documento è null, l'utente non esiste, quindi return null
        if(doc == null) {
            return null;
        }
        // Altrimenti, creo un nuovo utente e lo ritorno
        String nome=doc.getString("nome");
        String cognome= doc.getString("cognome");
        Date dataDiNascita1=doc.getDate("dataDiNascita");
        Instant instant = dataDiNascita1.toInstant();
        // Estrai la data in formato LocalDate
        LocalDate dataDiNascita = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        // Estraggo l'ArrayList di ObjectId dal database
        ArrayList<ObjectId> listaId_MacchineVirtuali = doc.get("listaId_MacchineVirtuali", ArrayList.class);
        //creo il nuovo utente
        return new Utente(nome, cognome, dataDiNascita, username, password, listaId_MacchineVirtuali);
    }
    

        //metodo che verifica il login
    public Amministratore login_admin(String username, String password) {
        collection = database.getCollection("Utenti");
        filter = and(eq("username", username), eq("password", password));
        doc = collection.find(filter).first();
        // Se il documento è null, l'utente non esiste, quindi return null
        if(doc == null) {
            return null;
        }
        // Altrimenti, creo un nuovo utente e lo ritorno
        String nome=doc.getString("nome");
        String cognome= doc.getString("cognome");
        Date dataDiNascita1=doc.getDate("dataDiNascita");
        Instant instant = dataDiNascita1.toInstant();
        // Estrai la data in formato LocalDate
        LocalDate dataDiNascita = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        //creo il nuovo amministratore
        return new Amministratore(nome, cognome, dataDiNascita, username, password);
    }


    
    public int aggiungiMacchinaUtente(Utente utente, int numeroMacchina){
        //estraggo la lista di id di macchine virtuali dell'utente
        Document doc2=collection.find(eq("username", utente.getUsername())).first();
        ArrayList<ObjectId> listaId_MacchineVirtuali = doc2.get("listaId_MacchineVirtuali", ArrayList.class);
        if (listaId_MacchineVirtuali==null)
            listaId_MacchineVirtuali=new ArrayList<ObjectId>();
        macchineVirtuali = database.getCollection("MacchineVirtuali");

        //controllo se esiste una macchina nella posizione scelta
        int numeroMacchine=(int)macchineVirtuali.countDocuments(eq("inUso", false));
        if (numeroMacchine<numeroMacchina || numeroMacchina<1)
            return -1;

        doc = macchineVirtuali.find(eq("inUso", false)).skip(numeroMacchina - 1) // Sottraggo 1 perché l'indice parte da 0
            .limit(1).first();
        //aggiungo l'id alla lista di id di macchine dell'utente
        listaId_MacchineVirtuali.add(doc.getObjectId("_id"));
        collection = database.getCollection("Utenti");
        //aggiorno lo stato della macchina
        macchineVirtuali.updateOne(eq("_id", doc.getObjectId("_id")), new Document("$set", new Document("inUso", true)));
        macchineVirtuali.updateOne(eq("_id", doc.getObjectId("_id")), new Document("$set", new Document("id_proprietario", doc2.getObjectId("_id"))));
        //aggiorno l'utente con la lista aggiornata
        collection.updateOne(eq("username", utente.getUsername()), new Document("$set", new Document("listaId_MacchineVirtuali", listaId_MacchineVirtuali)));
        return 1;
    }
    
    

    public int cancellaMacchinaUtente(Utente utente, int numeroMacchina){
        //estraggo la lista di id di macchine virtuali dell'utente
        macchineVirtuali = database.getCollection("MacchineVirtuali");
        Document doc=collection.find(eq("username", utente.getUsername())).first();
        ArrayList<ObjectId> listaId_MacchineVirtuali = doc.get("listaId_MacchineVirtuali", ArrayList.class);
        //se il numero selezionato non rientra nelle dimensioni della lista delle macchine ritorno -1
        if (listaId_MacchineVirtuali.size()<numeroMacchina || numeroMacchina<0)
            return -1;
        ObjectId idMacchinaCancellata=listaId_MacchineVirtuali.get(numeroMacchina-1);
        listaId_MacchineVirtuali.remove(numeroMacchina-1);
        //aggiorno lo stato della macchina e la lista delle macchine dell'utente
        macchineVirtuali.updateOne(eq("_id", idMacchinaCancellata), new Document("$set", new Document("inUso", false)).append("$unset", new Document("id_proprietario", "")));
        //aggiorno l'utente con la lista aggiornata
        collection.updateOne(eq("username", utente.getUsername()), new Document("$set", new Document("listaId_MacchineVirtuali", listaId_MacchineVirtuali)));
        return 1;
    }



    //restituisce tutte le macchine non in uso
    public void getListaMacchine(ArrayList<MacchinaVirtuale> listaMacchine){
        macchineVirtuali = database.getCollection("MacchineVirtuali");
        for(Document doc : macchineVirtuali.find(eq("inUso", false))) {
            listaMacchine.add(ottieniMacchina(doc));
        }
    }

    //restituisce la lista delle macchine dell'utente
    public void getListaMacchine(ArrayList<MacchinaVirtuale> listaMacchine, Utente utente){
        collection = database.getCollection("Utenti");
        macchineVirtuali=database.getCollection("MacchineVirtuali");
        Document doc_utente=collection.find(eq("username", utente.getUsername())).first();
        //estraggo la lista di macchine virtuali
        ArrayList<ObjectId> listaId_MacchineVirtuali=doc_utente.get("listaId_MacchineVirtuali", ArrayList.class);
        if (listaId_MacchineVirtuali==null)
            return;
        //per ogni ogetto nella lista, aggiungo una macchina virtuale
        for(ObjectId id : listaId_MacchineVirtuali) {
            listaMacchine.add(ottieniMacchina(macchineVirtuali.find(eq("_id", id)).first()));
        }
    }

    public MacchinaVirtuale getMacchinaVirtuale(int numeroMacchina, Utente utente){
        if(utente.getListaId_MacchineVirtuali().size()==0)
            return null;
        macchineVirtuali = database.getCollection("MacchineVirtuali");
        ArrayList<ObjectId> listaId_MacchineVirtuali = utente.getListaId_MacchineVirtuali();
        return ottieniMacchina(macchineVirtuali.find(eq("_id", listaId_MacchineVirtuali.get(numeroMacchina-1))).first());
    }

    public boolean isMacchineLibere(){
        macchineVirtuali = database.getCollection("MacchineVirtuali");
        if (macchineVirtuali.find(eq("inUso", false)).first()==null)
            return false;
        else
            return true;
    }

    public MacchinaVirtuale ottieniMacchina(Document doc){
        String nomeRisorsa, GPU, CPU;
        int RAM, memoriadisco, periodouso;
        boolean memoriasecondaria;
        nomeRisorsa = doc.getString("nomeRisorsa");
        GPU = doc.getString("GPU");
        RAM = doc.getInteger("RAM");
        CPU = doc.getString("CPU");
        memoriadisco = doc.getInteger("memoriadisco");
        memoriasecondaria = doc.getBoolean("memoriasecondaria");
        periodouso = doc.getInteger("periodouso");
        return new MacchinaVirtuale(nomeRisorsa, GPU, RAM, CPU, memoriadisco, memoriasecondaria, periodouso);
    }

        public Utente ottieniUtente(Document doc){
        ArrayList<ObjectId> listaid_MacchineVirtuali;
        String nome, cognome, username, password;
        nome = doc.getString("nome");
        cognome = doc.getString("cognome");
        username = doc.getString("username");
        password = doc.getString("password");
        Date dataDiNascita1=doc.getDate("dataDiNascita");
        Instant instant = dataDiNascita1.toInstant();
        // Estrai la data in formato LocalDate
        LocalDate dataDiNascita = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        listaid_MacchineVirtuali = doc.get("listaId_MacchineVirtuali", ArrayList.class);
        return new Utente(nome, cognome, dataDiNascita, username, password, listaid_MacchineVirtuali);
    }



    public boolean terminaMacchina(MacchinaVirtuale macchina){
        collection=database.getCollection("Utenti");
        macchineVirtuali=database.getCollection("MacchineVirtuali");
        doc=macchineVirtuali.find(eq("nomeRisorsa", macchina.getNomeRisorsa())).first();
        //se la macchina è già stata terminata dall'utente
        if (doc.getObjectId("id_proprietario")==null)
            return false;
        Document doc2=collection.find(eq("_id", doc.getObjectId("id_proprietario"))).first();
        Utente utente=ottieniUtente(doc2);

        macchineVirtuali.updateOne(eq("nomeRisorsa", macchina.getNomeRisorsa()), new Document("$set", new Document("inUso", false)).append("$unset", new Document("id_proprietario", "")));
        ArrayList<ObjectId> listaId_MacchineVirtuali=utente.getListaId_MacchineVirtuali();
        listaId_MacchineVirtuali.remove(doc.getObjectId("_id"));
        collection.updateOne(eq("username", utente.getUsername()), new Document("$set", new Document("listaId_MacchineVirtuali", listaId_MacchineVirtuali)));
        return true;
    }


    public boolean creaMacchina(MacchinaVirtuale macchinaVirtuale){
        macchineVirtuali=database.getCollection("MacchineVirtuali");
        if(macchineVirtuali.find(eq("nomeRisorsa", macchinaVirtuale.getNomeRisorsa())).first()!= null){
            return false;
        }

        macchineVirtuali.insertOne(new Document("nomeRisorsa", macchinaVirtuale.getNomeRisorsa()).append("GPU", macchinaVirtuale.getGPU())
        .append("RAM", macchinaVirtuale.getRAM()).append("CPU", macchinaVirtuale.getCPU()).append("memoriadisco", (int)macchinaVirtuale.getMemoriaDisco())
        .append("memoriasecondaria", macchinaVirtuale.getMemoriaSecondaria()).append("periodouso", macchinaVirtuale.getPeriodoUso()).append("inUso", false));
        return true;
    }


public void getTutteMacchine(ArrayList<MacchinaVirtuale> listaMacchine){
    macchineVirtuali=database.getCollection("MacchineVirtuali");
    for (Document doc : macchineVirtuali.find()) {
        listaMacchine.add(ottieniMacchina(doc));
    }
}


    public int eliminaMacchina(int numeroMacchina){
        macchineVirtuali=database.getCollection("MacchineVirtuali");
        collection=database.getCollection("Utenti");


         MongoCursor<Document> cursor = macchineVirtuali.find().iterator();

        int indiceCorrente = 1;
        boolean elementoTrovato = false;

        // Itero finché non raggiungi l'elemento desiderato o raggiungi la fine della collezione        
        while (cursor.hasNext() && indiceCorrente<numeroMacchina) {
            
            doc=cursor.next();
            indiceCorrente++;
        }
        if (indiceCorrente == numeroMacchina) {
            elementoTrovato = true;
        }

        if (!elementoTrovato){
            return -1;
        }

        if (cursor.hasNext())
            doc=cursor.next();

        if(doc.getObjectId("id_proprietario")!=null){
            Document doc2=collection.find(eq("_id", doc.getObjectId("id_proprietario"))).first();
            Utente utente=ottieniUtente(doc2);
            ArrayList<ObjectId> listaId_MacchineVirtuali=utente.getListaId_MacchineVirtuali();
            listaId_MacchineVirtuali.remove(doc.getObjectId("_id"));
            collection.updateOne(eq("username", utente.getUsername()), new Document("$set", new Document("listaId_MacchineVirtuali", listaId_MacchineVirtuali)));
        }

        macchineVirtuali.deleteOne(doc);
        return 1;
    }


}