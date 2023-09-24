package risorse.Data;
import java.util.Timer;
import java.util.TimerTask;

import org.bson.types.ObjectId;

public class MacchinaVirtuale extends RisorsaVirtuale {
    private int RAM;
    private String GPU, CPU;
    private int memoriadisco;
    private boolean memoriasecondaria;
    private Timer periodo;
    private boolean inUso;
    private int periodouso;
    private ObjectId id_proprietario;
    
    public MacchinaVirtuale(String nomeRisorsa, String GPU, int RAM, String CPU, int memoriadisco, boolean memoriasecondaria, int periodouso) {
        super(nomeRisorsa);
        this.GPU = GPU;
        this.RAM = RAM;
        this.CPU = CPU;
        this.memoriadisco=memoriadisco;
        this.memoriasecondaria=memoriasecondaria;
        this.inUso = false;
        this.periodo = new Timer();
        this.periodouso=periodouso;
    }

    public MacchinaVirtuale(String nomeRisorsa, String GPU, int RAM, String CPU, int memoriadisco, boolean memoriasecondaria, int periodouso, ObjectId id_proprietario) {
        super(nomeRisorsa);
        this.GPU = GPU;
        this.RAM = RAM;
        this.CPU = CPU;
        this.memoriadisco=memoriadisco;
        this.memoriasecondaria=memoriasecondaria;
        this.inUso = false;
        this.periodo = new Timer();
        this.periodouso=periodouso;
        this.id_proprietario=id_proprietario;
    }

    public ObjectId getidproprietario(){
        return this.id_proprietario;
    }

    public int getPeriodoUso() {
        return periodouso;
    }

    public void avviaMacchina(){
        this.inUso = true;
        this.periodo.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("La macchina virtuale " + getNomeRisorsa() + " è terminata");
                terminaMacchina();
            }
        }, getPeriodoUso()*1000*60);
    }
    
    public void terminaMacchina(){
        this.inUso = false;
        this.periodo.cancel();
    }

    public boolean isInUso() {
        return inUso;
    }

    public void setInUso(boolean inUso) {
        this.inUso = inUso;
    }

    public Timer getPeriodo() {
        return periodo;
    }

    public String getGPU() {
        return GPU;
    }

    public int getRAM() {
        return RAM;
    }

    public String getCPU() {
        return CPU;
    }

    public float getMemoriaDisco() {
        return memoriadisco;
    }

    public boolean getMemoriaSecondaria() {
        return memoriasecondaria;
    }

    @Override
    public String toString(){
        return "nome="+ this.getNomeRisorsa() + " GPU=" + this.getGPU() + " RAM=" + this.getRAM() 
        + "GB CPU=" + this.getCPU() + " memoria disco=" + this.getMemoriaDisco() 
        + "GB memoria secondaria=" + this.getMemoriaSecondaria() + " periodo_uso=" + this.getPeriodoUso() + " minuti\n";
    }

}
