import java.util.* ; 
import java.util.Random;

public class Plateau{

    private int nbLig ;
    private int nbCol ;
    private int nbCases; 
    private Case [][] plateau;
    private PNJ [] pNJs;
    private PJ [] pJs;
    private Objet [] objets; 
    
    //Constructeur
    
    public Plateau(int n, int m) {
        this.nbLig = n;
        this.nbCol = m;
        this.nbCases = n*m;
        this.plateau = new Case[nbLig][nbCol]; 
        this.pJs = new PJ[5];

        int numcase = 1;
        for(int i = 0; i < this.nbLig ; i++) {
            for(int j = 0 ; j < this.nbCol ; j++){ 
            	if (i == 0 || i == this.nbLig - 1 || j == 0 || j == this.nbCol - 1) 
            		this.plateau[i][j] = new Case(i, j, new Mur(), numcase); 
                else 
                	this.plateau[i][j] = new Case(i, j, numcase); 
                numcase ++;
        	}
        }
        for(int i = 0; i < 35; i++){
            Mur mur = new Mur() ; 
            Mur mur2 = new Mur() ;
            Mur mur3 = new Mur() ;
            Mur mur4 = new Mur() ;
            placerMur(3, i+6, mur) ; 
            placerMur(10, i+17, mur2) ;
            placerMur(21, i+3, mur3) ;
            placerMur(32, i+14, mur4) ;
            
        }
        for(int i = 0; i < 8; i++){
            Mur mur = new Mur() ; 
            Mur mur2 = new Mur() ;
            Mur mur3 = new Mur() ;
            Mur mur4 = new Mur() ;
            placerMur(4+i, 7, mur) ; 
            placerMur(11 +i ,30 , mur2) ;
            placerMur(22 + i , 22 , mur3) ;
            placerMur(21+i ,45 , mur4) ;
           }
        for(int i = 0; i < 4; i++){
             Mur mur = new Mur() ;
             placerMur(1+i ,50 , mur) ;
             Mur mur2 = new Mur() ;
             placerMur(35+i ,57 , mur2) ;
             Mur mur3 = new Mur() ;
             placerMur(36 ,1+i , mur3) ;
             Mur mur4 = new Mur() ;
             placerMur(11+i ,34 , mur4) ;
             Mur mur5 = new Mur() ;
             placerMur(23 ,i + 23 , mur5) ;
        }
    }

    //Getteurs, Setteurs
    
    public int getLigne() {
    	return this.nbLig ; 
    }
    
    public int getColonne() {
    	return this.nbCol ; 
    }
    
    public Case[][] getPlateau(){
    	return this.plateau ; 
    }
    
    public void setLigne(int n) {
    	this.nbLig = n ;
    }
    
    public void setColonne(int n) {
    	this.nbCol = n ; 
    }
    
    public void setPlateau(Case[][] n) {
    	this.plateau = n ; 
    }

    public void addPJ(PJ pJ, int i){
    	this.pJs[i] = pJ;
    }

    
    public boolean caseHautVide(Case c){
    	return plateau[c.getPositionX() - 1][c.getPositionY()].caseVide();
    }

    public Case getCaseHaut(Case c){
    	return plateau[c.getPositionX() - 1][c.getPositionY()];
    }

    public boolean caseBasVide(Case c){
    	return plateau[c.getPositionX() + 1][c.getPositionY()].caseVide();
    }

    public Case getCaseBas(Case c){
    	return plateau[c.getPositionX() + 1][c.getPositionY()];
    }
    
    public boolean caseDroiteVide(Case c){
    	return plateau[c.getPositionX()][c.getPositionY() + 1].caseVide();
    }

    public Case getCaseDroite(Case c){
    	return plateau[c.getPositionX()][c.getPositionY() + 1];
    }

    public boolean caseGaucheVide(Case c){
    	return plateau[c.getPositionX()][c.getPositionY() - 1].caseVide();
    }

    public Case getCaseGauche(Case c){
    	return plateau[c.getPositionX()][c.getPositionY() - 1];
    }

    public Case[] getCasesAutours(Case c){
        Case[] cases = new Case[4];  
        Case caseH = getCaseHaut(c);
        Case caseB = getCaseBas(c);
        Case caseD = getCaseDroite(c);
        Case caseG = getCaseGauche(c);
        cases[0] = caseH;
        cases[1] = caseD;
        cases[2] = caseB;
        cases[3] = caseG;
        return cases;      
    }

    //Method 
 
    public boolean deplacerPerso(int direction, Personnage perso) {
    	Case pos = perso.getPosition();
    	if (direction == 1){
    		if (caseHautVide(pos)) {
    			Case nouvPos = getCaseHaut(pos);
    			nouvPos.setContenu(perso);
                perso.setPosition(nouvPos);
    			pos.vider();
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

    	else if (direction == 2){
    		if (caseBasVide(pos)) {
    			Case nouvPos = getCaseBas(pos);
    			nouvPos.setContenu(perso);
                perso.setPosition(nouvPos);
    			pos.vider();
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

    	else if (direction == 3){
    		if (caseDroiteVide(pos)) {
    			Case nouvPos = getCaseDroite(pos);
    			nouvPos.setContenu(perso);
                perso.setPosition(nouvPos);
    			pos.vider();
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

    	else if (direction == 4){
    		if (caseGaucheVide(pos)) {
    			Case nouvPos = getCaseGauche(pos);
    			nouvPos.setContenu(perso);
                perso.setPosition(nouvPos);
    			pos.vider();
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	return false;
    }

    public void actionPNJ(ArrayList<PNJ> pNJs){
        for(int i = 0; i < pNJs.size(); i++){
            PNJ pNJ = pNJs.get(i);
            Case pos = pNJ.getPosition();
            Case[] casesAutour = this.getCasesAutours(pos);

            ArrayList<Case> casesPerso = new ArrayList<Case>();
            PJ perso = null;

            for (int x = 0; x < 4; x++){
                Object contenu = casesAutour[x].getContenu();

                if (contenu instanceof PJ){
                    casesPerso.add(casesAutour[x]);
                    perso = (PJ)(casesAutour[x].getContenu());
                }
            }

            int nbPersoAutour = casesPerso.size();

            if(nbPersoAutour == 0){
                int alea = (1 + (int)(Math.random() * ((4 - 1) + 1)));
                deplacerPerso(alea, pNJ);
            }
            else{
                int attaque = pNJ.getAttaque().calculScore();
                int defense = pNJ.getDefense().calculScore();
                if(attaque < defense){
                    for(int y = 0; y < 4; y++){
                        Boolean conf = deplacerPerso(y, pNJ);
                        if(conf == true)
                            break;

                    }
                }
                else
                    pNJ.attaquer(perso);
            }    
        }
    }

    public void ramasserObj(PJ perso, int coteCase){
        Case caseObjet = null;
        Case pos = perso.getPosition();
        if (coteCase == 0)
            caseObjet = getCaseHaut(pos);
        else if (coteCase == 1)
            caseObjet = getCaseDroite(pos);
        else if (coteCase == 2)
            caseObjet = getCaseBas(pos);
        else if (coteCase == 3)
            caseObjet = getCaseGauche(pos);
        perso.addObjetSac((Objet) (caseObjet.getContenu()));
        caseObjet.setContenu(null);
    }
    
    public void placerObjet(int i, int j , Objet o) {
    	if (this.plateau[i][j].getContenu() == null) {
    		this.plateau[i][j].setContenu(o); 
    		o.setPosition(this.plateau[i][j]);
    	}
    	else {
    		System.out.print("Erreur de placement") ; 
    		return ; 
    	}
    }

    public void placerPerso(int i, int j , Personnage p) {
    	if (this.plateau[i][j].getContenu() == null) {
    		this.plateau[i][j].setContenu(p); 
    		p.setPosition(this.plateau[i][j]);
    	}
    	else {
    		System.out.print("Erreur de placement " + p.toString()); 
    		return ; 
    	}
    }

    public void placerMur(int i, int j , Mur m) {
    	if (this.plateau[i][j].getContenu() == null) {
    		this.plateau[i][j].setContenu(m); 
    		m.setPosition(this.plateau[i][j]);
    	}
    	else {
    		System.out.print("Erreur de placement") ; 
    		return ; 
    	}
    }
    public void spawnAleatoire(PJ p) {
    	int i =(int)(Math.random() * ( (this.nbLig - 1))) ; 
    	int j =(int)(Math.random() * ( (this.nbCol - 1))) ;
    	while (!(plateau[i][j].getContenu() == null)) {
    		i =(int)(Math.random() * ( (this.nbLig - 1))) ; 
        	j =(int)(Math.random() * ( (this.nbCol - 1))) ;

    	}
    	placerPerso(i, j, (Personnage)(p));
    }

    public String afficher() {
    	String plat = new String("");
        for(int i = 0;i < nbLig ; i++) {
            for(int j = 0 ; j<nbCol ; j++) {
            	plat = plat + plateau[i][j].toString(); 
            }
            plat = plat + "\n"; 
            }
        return plat;
    }
}