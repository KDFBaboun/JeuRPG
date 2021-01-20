import java.io.Serializable;

public class Bombe extends Explosif implements Serializable{
	public Bombe(String nom, int cout) {
		super(nom, cout); 
	}
	
	public void appliquerEffet(Personnage cible) { 
		cible.setBlessure(cible.getBlessure() - this.getCoutUtil());
	}
}


