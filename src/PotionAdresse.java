import java.io.Serializable;

public class PotionAdresse extends Potion implements Serializable{
	public PotionAdresse(String nom, int cout) {
		super(nom, cout); 
	}
	
	public void appliquerEffet(PJ p) { 
		int adresse = p.getCaracteristique().getAdresse();
		p.getCaracteristique().setAdresse(adresse + 1);
	}
}

