import java.io.Serializable;

public class PotionForce extends Potion implements Serializable{
	
	public PotionForce(String nom, int cout) {
		super(nom, cout); 
	}
	
	public void appliquerEffet(PJ p) { 
		int force = p.getCaracteristique().getForce();
		p.getCaracteristique().setForce(force + 1);
	}
}
