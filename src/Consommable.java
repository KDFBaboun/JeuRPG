import java.io.Serializable;

public abstract class Consommable extends Objet implements Serializable {
	public Consommable(String nom, int coutUtil){
		super(nom, coutUtil);
	}

}
