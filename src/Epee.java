import java.io.Serializable;

public class Epee extends Armes implements Serializable{
	public Epee(String nom, int CoutUtil, Niveaux Maniabilite, Niveaux Impact ) {
		super(nom, CoutUtil, Maniabilite, Impact ) ;
	}
}
