import java.io.Serializable;

public abstract class Vetements extends Equipement implements Serializable{
    private Niveaux solidite;
    private Niveaux encombrement;

    public Vetements(String nom, int coutUtil, Niveaux solidite, Niveaux encombrement){
        super(nom, coutUtil);
        this.solidite = solidite;
        this.encombrement = encombrement;
    }


    Niveaux getSolidite(){
        return this.solidite;
    }

    void setManiabilite(Niveaux sol){
        this.solidite = sol;
    }

    Niveaux getEncombrement(){
        return this.encombrement;
    }

    void setEncombrement(Niveaux enc){
        this.encombrement = enc;
    }

    public String toString(){
        String s = new String("- " + this.getNom() + " (" + this.solidite +") " + this.getCoutUtil() + "PA");
        return s;
    }

}
