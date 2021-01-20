import java.io.Serializable;

public class PotionResistance extends Potion implements Serializable{
	public PotionResistance(String nom, int cout) {
		super(nom, cout); 
	}
	
	public void appliquerEffet(PJ p) { 
		int resist = p.getCaracteristique().getResistance();
		p.getCaracteristique().setResistance(resist + 1);
	}

}

