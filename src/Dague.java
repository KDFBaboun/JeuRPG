import java.io.Serializable;

public class Dague extends Armes implements Serializable{
	public Dague(String nom, int CoutUtil, Niveaux Maniabilite, Niveaux Impact ) {
		super(nom, CoutUtil, Maniabilite, Impact ) ;
	}
}

