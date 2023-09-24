package risorse.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import risorse.Data.*;

public class GestoreClient implements Runnable{
    Socket s;
    PrintWriter out;
    BufferedReader input;
    GestoreDB gestoreDB;
    Utente utente;
    Amministratore amministratore;
    private static final String CLOSE = "#"; 
    private static final String ASK = "/";


    public GestoreClient(Socket s, GestoreDB gestoreDB){
        this.utente=null;
        this.s = s;
        this.gestoreDB = gestoreDB;
        try {
            this.out = new PrintWriter(s.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            System.err.println("Errore di I/O con il client");
            System.exit(-1);
        }
    }

    public void Menu() throws Exception {
        String risposta;
        int result=0;
        while(result<1 || result>3){
            out.println("Benvenuto, inserisci un numero corrispondente all'azione che vuoi svolgere");
            out.println("\t1) Registrazione\n\t2) Login\n\t3) Esci\n");
            out.println(ASK);
            result = Integer.parseInt(input.readLine()); //converte la stringa in un numero intero

            switch (result){
                case 1:
                    out.println("\nSei un Amministratore? Rispondi true o false\n");
                    out.println(ASK);
                    risposta = input.readLine();
                    if (risposta.equals("true")==false){
                        if (risposta.equals("false")==false){
                            out.println("\n risposta non valida\n");
                            result=0;
                            break;
                        }
                    }

                    if(risposta.equals("false"))
                        result=Registrazione();

                    else{
                        result=Registrazione_admin();
                        if (result!=0)
                        result=MainMenu_amministratore();
                        if (result==1)
                            result=0;
                        break;
                    }
                        
                    if (result!=0)
                        result=MainMenu();
                        if (result==1)
                            result=0;
                    break;

                case 2:
                    out.println("\nSei un Amministratore? Rispondi true o false\n");
                    out.println(ASK);
                    risposta = input.readLine();
                    if (risposta.equals("true")==false){
                        if (risposta.equals("false")==false){
                            out.println("\n risposta non valida\n");
                            result=0;
                            break;
                        }
                    }

                    if(risposta.equals("false")){
                        result=Login();
                    }
                    else{
                        result=Login_admin();
                        if (result!=0)
                            result=MainMenu_amministratore();
                        if (result==1)
                            result=0;
                        break;
                    }

                    if (result!=0)
                        result=MainMenu();
                        if (result==1)
                            result=0;
                    break;

                case 3:
                    out.println("Arrivederci!");
                    out.println(CLOSE);
                    input.close();
                    out.close();
                    s.close();
                    break;
                default:
                    out.println("Risposta non valida, riprovare");
            }
        }
    }

    public int Registrazione() throws Exception {
        out.println("\nInserisci il tuo nome");
        out.println(ASK);
        String nome = input.readLine();
        out.println("\nInserisci il tuo cognome");
        out.println(ASK);
        String cognome = input.readLine();
        out.println("\nInserisci giorno di nascita");
        out.println(ASK);
        String giorno1 = input.readLine();
        int giorno= Integer.parseInt(giorno1);
        out.println("\nInserisci mese di nascita");
        out.println(ASK);
        String mese1 = input.readLine();
        int mese= Integer.parseInt(mese1);
        out.println("\nInserisci anno di nascita");
        out.println(ASK);
        String anno1 = input.readLine();
        int anno= Integer.parseInt(anno1);
        LocalDate dataDiNascita=LocalDate.of(anno, mese, giorno);
        out.println("\nInserisci il tuo username");
        out.println(ASK);
        String username = input.readLine();
        out.println("\nInserisci la tua password");
        out.println(ASK);
        String password = input.readLine();
        Utente utente =new Utente(nome,cognome,dataDiNascita,username,password);
        boolean risultato=gestoreDB.registraUtente(utente);
        if (!risultato){
            out.println("Registrazione fallita, utente gia' esistente\n");
            return 0;
        }
        this.utente=utente;
        out.println("\nBenvenuto, " +utente.toString()+"!\n");
        return 1;
    }


    public int Registrazione_admin() throws Exception{
        out.println("\nInserisci il tuo nome");
        out.println(ASK);
        String nome = input.readLine();
        out.println("\nInserisci il tuo cognome");
        out.println(ASK);
        String cognome = input.readLine();
        out.println("\nInserisci giorno di nascita");
        out.println(ASK);
        String giorno1 = input.readLine();
        int giorno= Integer.parseInt(giorno1);
        out.println("\nInserisci mese di nascita");
        out.println(ASK);
        String mese1 = input.readLine();
        int mese= Integer.parseInt(mese1);
        out.println("\nInserisci anno di nascita");
        out.println(ASK);
        String anno1 = input.readLine();
        int anno= Integer.parseInt(anno1);
        LocalDate dataDiNascita=LocalDate.of(anno, mese, giorno);
        out.println("\nInserisci il tuo username");
        out.println(ASK);
        String username = input.readLine();
        out.println("\nInserisci la tua password");
        out.println(ASK);
        String password = input.readLine();
        Amministratore amministratore =new Amministratore(nome,cognome,dataDiNascita,username,password);
        boolean risultato=gestoreDB.registraAmministratore(amministratore);
        if (!risultato){
            out.println("Registrazione fallita, amministratore gia' esistente\n");
            return 0;
        }
        this.amministratore=amministratore;
        out.println("\nBenvenuto, " +amministratore.toString()+"!\n");
        return 1;
    }

    public int Login() throws Exception {
        out.println("\nInserisci il tuo username");
        out.println(ASK);
        String username = input.readLine();
        out.println("\nInserisci la tua password");
        out.println(ASK);
        String password = input.readLine();
        Utente utente =gestoreDB.login(username,password);
        //se l'username o la password sono errati
        if (utente==null){
            out.println("\nUsername o password errati\n");
            return 0;
        }
        //se il login e' andato a buon fine
        this.utente=utente;
        out.println("\nBentornato, " + utente.toString()+"!\n");
        return 1;
    }


    public int Login_admin() throws Exception{
        out.println("\nInserisci il tuo username");
        out.println(ASK);
        String username = input.readLine();
        out.println("\nInserisci la tua password");
        out.println(ASK);
        String password = input.readLine();
        Amministratore amministratore =gestoreDB.login_admin(username,password);
        //se l'username o la password sono errati
        if (amministratore==null){
            out.println("\nUsername o password errati\n");
            return 0;
        }
        //se il login e' andato a buon fine
        this.amministratore=amministratore;
        out.println("\nBentornato, " + amministratore.toString()+"!\n");
        return 1;
    }


    public int MainMenu() throws Exception {
        int result=0;
        while(result<1 || result>4){
            out.println("Inserisci un numero corrispondente all'azione che vuoi svolgere");
            out.println("\t1) Aggiungi una macchina virtuale\n\t2) Cancella una macchina virtuale\n\t3) Visiona lista macchine virtuali possedute\n\t4) Torna al menu principale\n");
            out.println(ASK);
            result = Integer.parseInt(input.readLine());

            switch (result){
                case 1:
                    aggiungiMacchina();
                    result=0;
                    break;
                case 2:
                    cancellaMacchina();
                    result=0;
                    break;
                case 3:
                    stampaListaMacchine(utente);
                    result=0;
                    break;
                case 4:
                    result=1;
                    break;
                default:
                    out.println("Risposta non valida, riprovare");
            }
        }
        return result;
    }


    public int MainMenu_amministratore() throws Exception{
        int result=0;
        while(result<1 || result>4){
            out.println("Inserisci un numero corrispondente all'azione che vuoi svolgere");
            out.println("\t1) Crea una nuova macchina virtuale\n\t2) Elimina una macchina virtuale\n\t3) Visiona lista macchine virtuali\n\t4) Torna al menu principale\n");
            out.println(ASK);
            result = Integer.parseInt(input.readLine());

            switch (result){
                case 1:
                    creaMacchina();
                    result=0;
                    break;
                case 2:
                    eliminaMacchina();
                    result=0;
                    break;
                case 3:
                    stampaListaMacchine();
                    result=0;
                    break;
                case 4:
                    result=1;
                    break;
                default:
                    out.println("Risposta non valida, riprovare");
            }
        }
        return result;

    }



    public void aggiungiMacchina() throws Exception{
        int numero;
        if (!gestoreDB.isMacchineLibere()){
            out.println("\nMi dispiace, non sono presenti macchine virtuali per il momento\n");
            return;
        }
        //stampo la lista delle macchine libere
        stampaListaMacchine();
        out.println("\nInserisci il numero della macchina che vuoi aggiungere");
        out.println(ASK);
        numero = Integer.parseInt(input.readLine());
        int result=gestoreDB.aggiungiMacchinaUtente(utente, numero);
        if(result==-1){
            out.println("\nInserisci un numero valido");
            out.println("riprova\n");
        }
        else{
            //quando aggiungo una macchina, la avvio anche con un thread
            ArrayList<MacchinaVirtuale> macchineVirtuali = new ArrayList<MacchinaVirtuale>();
            gestoreDB.getListaMacchine(macchineVirtuali, utente);
            MacchinaVirtuale macchinaVirtuale=macchineVirtuali.get(macchineVirtuali.size()-1);
            Thread thread = new Thread(() -> {avvia(macchinaVirtuale, utente);});
            thread.start();
            out.println("\nMacchina aggiunta con successo\n");
        }
    }


    //stampa tutte le macchine disponibili
    public void stampaListaMacchine(){
        ArrayList<MacchinaVirtuale> macchineVirtuali = new ArrayList<MacchinaVirtuale>();
        //otengo la lista delle macchine virtuali libere
        gestoreDB.getListaMacchine(macchineVirtuali);
        if(macchineVirtuali.size()==0){
            out.println("\nNon esistono macchine virtuali\n");
            return;
        }
        int i=1;
        Iterator<MacchinaVirtuale> macchinaVirtuale = macchineVirtuali.iterator();
        out.println("\nLista macchine virtuali disponibili:\n");
        // Scorrere gli elementi con l'iteratore
        while (macchinaVirtuale.hasNext()) {
            MacchinaVirtuale macchina = macchinaVirtuale.next();
            out.println("\t"+i+") "+macchina.toString());
            i++;
        }
    }

    //stampa la lista di tutte le macchine di un utente
    public void stampaListaMacchine(Utente utente){
        ArrayList<MacchinaVirtuale> listaMacchine = new ArrayList<MacchinaVirtuale>();
        gestoreDB.getListaMacchine(listaMacchine, utente);
        //se listamacchine è vuota
        if (listaMacchine.size()==0){
            out.println("\nNon hai macchine virtuali\n");
            return;
        }
        int i=1;
        Iterator<MacchinaVirtuale> macchinaVirtuale = listaMacchine.iterator();
        while (macchinaVirtuale.hasNext()) {
            MacchinaVirtuale macchina = macchinaVirtuale.next();
            out.println("\t"+i+") "+macchina.toString());
            i++;
        }
    }


    public void cancellaMacchina() throws NumberFormatException, IOException{
        //se non ho macchine virtuali
        ArrayList<MacchinaVirtuale> macchine=new ArrayList<MacchinaVirtuale>();
        gestoreDB.getListaMacchine(macchine, utente);
        if(macchine.size()==0){
            out.println("\nNon hai macchine virtuali\n");
            return;
        }
        //altrimenti stampo la lista delle macchine virtuali
        stampaListaMacchine(utente);
        int numero;
        out.println("\nInserisci il numero della macchina che vuoi cancellare");
        out.println(ASK);
        numero = Integer.parseInt(input.readLine());
        int result=gestoreDB.cancellaMacchinaUtente(utente, numero);
        if(result==-1){
            out.println("\nInserisci un numero valido");
            out.println("riprova\n");
            return;
        }
        out.println("\nMacchina eliminata con successo\n");
    }



    public void avvia(MacchinaVirtuale macchina, Utente utente){
        Timer periodo=macchina.getPeriodo();
        periodo.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean result=gestoreDB.terminaMacchina(macchina);
                if (result)
                    System.out.println("La macchina virtuale " + macchina.getNomeRisorsa() + " è terminata");
            }
        }, macchina.getPeriodoUso()*1000*60);
    }


    public void creaMacchina() throws Exception{
        String nomeRisorsa, CPU, GPU;
        int RAM, memoriadisco, periodoUso;
        boolean memoriasecondaria;
        out.println("\nInserisci il nome della risorsa");
        out.println(ASK);
        nomeRisorsa = input.readLine();
        out.println("\nInserisci il nome della CPU");
        out.println(ASK);
        CPU = input.readLine();
        out.println("\nInserisci il nome della GPU");
        out.println(ASK);
        GPU = input.readLine();
        out.println("\nInserisci un numero di Giga per la RAM");
        out.println(ASK);
        RAM = Integer.parseInt(input.readLine());
        out.println("\nInserisci un numero di Giga per la memoria disco");
        out.println(ASK);
        memoriadisco = Integer.parseInt(input.readLine());
        out.println("\nVuoi la memoria secondaria? Rispondi true o false");
        out.println(ASK);
        memoriasecondaria = Boolean.parseBoolean(input.readLine());
        out.println("\nInserisci un numero di minuti per il periodo di utilizzo");
        out.println(ASK);
        periodoUso = Integer.parseInt(input.readLine());
        MacchinaVirtuale macchinaVirtuale = new MacchinaVirtuale(nomeRisorsa, GPU, RAM, CPU, memoriadisco, memoriasecondaria, periodoUso);
        boolean risultato=gestoreDB.creaMacchina(macchinaVirtuale);
        if (!risultato)
            out.println("\nRegistrazione fallita, macchina gia' esistente\n");
        else
            out.println("\nMacchina registrata con successo\n");
    }



    public void eliminaMacchina() throws Exception{
        //se non ho macchine virtuali
        ArrayList<MacchinaVirtuale> macchine=new ArrayList<MacchinaVirtuale>();
        gestoreDB.getTutteMacchine(macchine);
        if(macchine.size()==0){
            out.println("\nNon esistono macchine virtuali\n");
            return;
        }
        //altrimenti stampo la lista delle macchine virtuali
        stampaTutteMacchine(macchine);
        int numero;
        out.println("\nInserisci il numero della macchina che vuoi cancellare");
        out.println(ASK);
        numero = Integer.parseInt(input.readLine());
        int result=gestoreDB.eliminaMacchina(numero);
        if(result==-1){
            out.println("\nInserisci un numero valido");
            out.println("riprova\n");
            return;
        }
        out.println("\nMacchina eliminata con successo\n");
    }


    public void stampaTutteMacchine(ArrayList<MacchinaVirtuale> macchine){
        int i=1;
        Iterator<MacchinaVirtuale> macchinaVirtuale = macchine.iterator();
        while (macchinaVirtuale.hasNext()) {
            MacchinaVirtuale macchina = macchinaVirtuale.next();
            out.println("\t"+i+") "+macchina.toString());
            i++;
        }

    }




    @Override
    public void run() {
        while (true) {
            try {
                Menu();
            } catch (Exception e) { 
                out.println("\nRisposta non valida, riprovare");
                continue;
            }
            break; //se non ci sono eccezioni deve uscire
        }
    }
    
}
