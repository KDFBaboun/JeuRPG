import java.io.Serializable;

public class Habit extends Vetements implements Serializable{
	public Habit(String nom, int coutUtil, Niveaux solidite, Niveaux encombrement) {
		super(nom, coutUtil, solidite, encombrement) ; 
	}
}

