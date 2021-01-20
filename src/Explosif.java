import java.io.Serializable;

public abstract class Explosif extends Consommable implements Serializable{
	public Explosif(String nom, int coutUtil){
		super(nom, coutUtil);
	}

	public abstract void appliquerEffet(Personnage cible);

	public String toString(){
        String s = new String("- " + this.getNom() + " " + this.getCoutUtil() + "PA");
        return s;
    }
}