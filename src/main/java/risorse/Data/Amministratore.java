package risorse.Data;
import java.time.LocalDate;

public class Amministratore extends Persona {
    public Amministratore(String nome, String cognome, LocalDate dataDiNascita, String username, String password){
        super(nome, cognome, dataDiNascita, username, password);
    }




    
    //tostring
    @Override
    public String toString(){
        return this.getNome()+" "+this.getCognome();
    }
}
