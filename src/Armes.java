import java.io.Serializable;

public abstract class Armes extends Equipement implements Serializable {
    private Niveaux maniabilite;
    private Niveaux impact;

    public Armes(String nom, int coutUtil, Niveaux maniabilite, Niveaux impact){
    	super(nom, coutUtil);
    	this.maniabilite = maniabilite;
    	this.impact = impact;
    }


    Niveaux getManiabilite(){
    	return this.maniabilite;
    }

    void setManiabilite(Niveaux man){
    	this.maniabilite = man;
    }

    Niveaux getImpact(){
    	return this.impact;
    }

    void setImpact(Niveaux imp){
    	this.impact = imp;
    }

    public String toString(){
        String s = new String("- " + this.getNom() + " (" + this.impact +") " + this.getCoutUtil() + "PA");
        return s;
    }
}
