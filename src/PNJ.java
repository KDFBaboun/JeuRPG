import java.util.*;

public class PNJ extends Personnage {
	private int puissance;
	private Objet drop;

    public PNJ(String nom, int puissance, ArrayList<Objet> objets){
        super(nom, 100*puissance);
        this.puissance = puissance;
        this.setDrop(objets);
        setCompetences();
    }

	int getPuissance(){
		return this.puissance;
	}

    public void mourir() {
    	Case pos = this.getPosition();
    	pos.setContenu(drop);
    }

    public void setCompetences(){
    	int puiss = this.puissance;
        Niveaux initiative = new Niveaux((1 + (int)(Math.random() * ((3 - 1) + 1)))*puiss); 
        Niveaux attaque = new Niveaux((1 + (int)(Math.random() * ((3 - 1) + 1)))*puiss);
        Niveaux esquive = new Niveaux((1 + (int)(Math.random() * ((3 - 1) + 1)))*puiss);
        Niveaux defense = new Niveaux((1 + (int)(Math.random() * ((3 - 1) + 1)))*puiss);
        Niveaux degat = new Niveaux((1 + (int)(Math.random() * ((3 - 1) + 1)))*puiss);

        this.setInitiative(initiative);
        this.setAttaque(attaque);
        this.setEsquive(esquive);
        this.setDefense(defense);
        this.setDegat(degat);
    }

    public void setDrop(ArrayList<Objet> objets){
        int nbAlea = (int)(Math.random() * ((objets.size()-1) + 1));
    	this.drop = objets.get(nbAlea);
    }

    public boolean attaquer(PJ cible) {
        int atk = this.getAttaque().calculScore();
        int esq = cible.getEsquive().calculScore();
        if  (atk > esq) {
            int deg = this.getDegat().calculScore(); 
            int def = cible.getDefense().calculScore();
            int attaque = deg - def; 
            if (attaque > 0){
                int nbBlessure = attaque / 3;
                System.out.println(atk + " " + esq + " " + deg + " " + def + " " + nbBlessure);
                cible.setBlessure(cible.getBlessure()-nbBlessure);
                return true;
            }
        }
        return false;
    }

    public String toString(){
        String s = new String(this.getNom() + " Puissance : " + this.puissance + "\n Drop : " + this.drop + "\n"
                            + "+ Initiative : " + this.getInitiative() + "\n"
                            + "+ Attaque    : " + this.getAttaque() + "\n"
                            + "+ Esquive    : " + this.getEsquive() + "\n"
                            + "+ Defense    : " + this.getDefense() + "\n"
                            + "+ Degat      : " + this.getDegat() + "\n");
        return s;
    }

}
