import java.util.*;

public class Jeux{
	public static void main(String[] args){
		/*
		Armes a1 = new Armes("AK 47", 3, new Niveaux(1, 3), new Niveaux(2, 4));
		Armes a2 = new Armes("Excalibure", 4, new Niveaux(1, 1), new Niveaux(1, 1));
		Armes a3 = new Armes("B", 1, new Niveaux(2, 1), new Niveaux(0, 4));
		Armes a4 = new Armes("C", 2, new Niveaux(1, 0), new Niveaux(2, 2));
		Armes a5 = new Armes("D", 2, new Niveaux(3, 3), new Niveaux(5, 1));
		ArrayList<Objet> sac = new ArrayList<Objet>();
		sac.add(a3);
		sac.add(a4);
		sac.add(a5);
		PJ p1 = new PJ(sac, a1, a2);
		System.out.println(p1);

		Case c1 = new Case(2, 4, a1);
		System.out.println(c1);

		Mur m1 = new Mur();

		plateau(5, 5)
		*/
		PJ pJ = new PJ("Matt", 10, 4, 4);
		Plateau plat = nouvPlateau(pJ);

		Objet a1 = new Armes("AK 47", 3, new Niveaux(1, 3), new Niveaux(2, 4));
		plat.placerObjet(4, 5, a1);
		System.out.println(plat.afficher());
		Case pos = pJ.getPosition();

    	Case[] casesAutour = plat.getCasesAutours(pos);
    	Case caseObj = new Case(-1, -1, -1);
    	int coteCase = 0;
    	for (int i = 0; i < 4; i++){
    		Object contenu = casesAutour[i].getContenu();
    		if (contenu instanceof Objet){
    			caseObj = casesAutour[i];
    			coteCase = i;
    			System.out.println(caseObj.toString() + " " + coteCase);
    		}
    	}
		plat.ramasserObj(pJ, coteCase);
		pJ.ramasser();
	}

	public static Plateau nouvPlateau(PJ pJ){
		Plateau plat1 = new Plateau(15, 20);
		plat1.placerPerso(5,5, pJ);

		return plat1;
	}
}