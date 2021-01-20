import java.io.Serializable;

public class PotionSoin extends Potion implements Serializable{
	
	public PotionSoin(String nom, int cout) {
		super(nom, cout); 
	}
	
	public void appliquerEffet(PJ p) { 
		p.setBlessure(p.getBlessure() + 1);
	}
}
