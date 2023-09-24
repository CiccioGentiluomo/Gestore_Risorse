package risorse.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import org.bson.types.ObjectId;


public class Utente extends Persona{


    private ArrayList<ObjectId> listaid_MacchineVirtuali;

    //costruttore per creare un utente
    public Utente(String nome, String cognome, LocalDate dataDiNascita, String username, String password){
        super(nome, cognome, dataDiNascita, username, password);
        this.listaid_MacchineVirtuali=new ArrayList<ObjectId>();

    }


    public Utente(String nome, String cognome, LocalDate dataDiNascita, String username, String password, ArrayList<ObjectId> listaid_MacchineVirtuali){
        super(nome, cognome, dataDiNascita, username, password);
        this.listaid_MacchineVirtuali=listaid_MacchineVirtuali;

    }



    public ArrayList<ObjectId> getListaId_MacchineVirtuali(){
        return listaid_MacchineVirtuali;
    }

    public void avviaMacchineUtente(ArrayList<MacchinaVirtuale> macchineVirtuali){
                // Crea un iteratore per l'ArrayList
        Iterator<MacchinaVirtuale> iteratore = macchineVirtuali.iterator();

        // Scorrere gli elementi con l'iteratore
        while (iteratore.hasNext()) {
            MacchinaVirtuale macchina = iteratore.next();
            macchina.avviaMacchina();
        }
    }

    public void terminaMacchina(MacchinaVirtuale macchinaVirtuale){
        macchinaVirtuale.terminaMacchina();

    }

    //tostring
    @Override
    public String toString(){
        return this.getNome()+" "+this.getCognome();
    }
}   