import java.io.Serializable;

public abstract class Potion extends Consommable implements Serializable{
	public Potion(String nom, int coutUtil){
		super(nom, coutUtil);
	}

	public abstract void appliquerEffet(PJ p);

	public String toString(){
        String s = new String("- " + this.getNom() + " " + this.getCoutUtil() + "PA");
        return s;
    }
}