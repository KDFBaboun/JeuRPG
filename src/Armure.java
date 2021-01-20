import java.io.Serializable;

public class Armure extends Vetements implements Serializable{
	public Armure(String nom, int coutUtil, Niveaux solidite, Niveaux encombrement) {
		super(nom, coutUtil, solidite, encombrement) ; 
	}
}

