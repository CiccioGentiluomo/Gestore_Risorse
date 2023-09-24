package risorse.Data;


public abstract class RisorsaVirtuale {
    private String nomeRisorsa;

    //costruttore
    public RisorsaVirtuale(String nomeRisorsa) {
        this.nomeRisorsa = nomeRisorsa;
    }

    //getter
    public String getNomeRisorsa(){
        return nomeRisorsa;
    }
}
