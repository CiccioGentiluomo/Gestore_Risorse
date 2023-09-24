package risorse.Data;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public abstract class Persona{
    private String nome, cognome;
    private final LocalDate dataDiNascita;
    private final String username;
    private String password;


    public Persona(String nome, String cognome, LocalDate dataDiNascita, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    //getdataDiNascita
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    //tostring
    public String toString() {
        return this.nome + " "+ this.cognome + " " + "nato il: " + this.dataDiNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Persona persona = (Persona) obj;
        return this.nome.equals(persona.getNome()) && this.cognome.equals(persona.getCognome()) && this.dataDiNascita.equals(persona.getDataDiNascita());
    }
 




}