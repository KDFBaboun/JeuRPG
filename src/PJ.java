import java.util.*;
import java.io.Serializable;

public class PJ extends Personnage implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numPJ;
    private int nbPA;
    public Caracteristiques caracteristiques;
    private ArrayList<Objet> sac;
    private Objet[] objEquiper;
    private Armes armeAtk;
    private int niveauxExp;
    
    public PJ() {
        super();
    	this.nbPA = 10;
    	this.caracteristiques = new Caracteristiques();
    	this.sac = new ArrayList<Objet>();
    	this.objEquiper = new Objet[2]; 
        this.objEquiper[0] = null;
        this.objEquiper[1] = null;
        this.armeAtk = null;
        setCompetences();
    }

    public PJ(String nom, int f, int a, int r, int numPJ) {
        super(nom);
        this.numPJ = numPJ;
        this.nbPA = 10;
        this.caracteristiques = new Caracteristiques(f, a ,r);
        this.sac = new ArrayList<Objet>();
        this.objEquiper = new Objet[2]; 
        this.objEquiper[0] = null;
        this.objEquiper[1] = null;
        this.armeAtk = null;
        setCompetences();
    }

    public PJ(String nom, int f, int a, int r, int numPJ, int exp) {
        super(nom, exp);
        this.numPJ = numPJ;
        this.nbPA = 10;
        this.caracteristiques = new Caracteristiques(f, a ,r);
        this.sac = new ArrayList<Objet>();
        this.objEquiper = new Objet[2]; 
        this.objEquiper[0] = null;
        this.objEquiper[1] = null;
        this.armeAtk = null;
        setCompetences();
        setNiveauExp(exp);
    }

    public PJ(String nom, int f, int a, int r, ArrayList<Objet> sac, Objet obj1, Objet obj2, int numPJ, int exp) {
        super(nom, exp);
        this.numPJ = numPJ;
        this.nbPA = 100;
        this.caracteristiques = new Caracteristiques(f, a ,r);
        this.sac = sac;
        this.objEquiper = new Objet[2]; 
        this.objEquiper[0] = obj1;
        this.objEquiper[1] = obj2;
        this.armeAtk = null;
        setCompetences();
        setNiveauExp(exp);
    }

    public PJ(ArrayList<Objet> sac, Objet obj1, Objet obj2) {
        super();
        this.nbPA = 10 ;
        this.caracteristiques = new Caracteristiques();
        this.sac = sac;
        this.objEquiper = new Objet[2]; 
        this.objEquiper[0] = obj1;
        this.objEquiper[1] = obj2;
        this.armeAtk = null;
        setCompetences();
    }

    public void setCompetences(){
        Niveaux encombrement = new Niveaux(); 
        Niveaux maniabilite = new Niveaux();
        Niveaux solidite = new Niveaux();
        Niveaux impact = new Niveaux();
        for (int i = 0; i < 2; i++){
            if (this.objEquiper[i] instanceof Vetements){
                solidite = solidite.addiNiv(((Vetements)this.objEquiper[i]).getSolidite());
                encombrement = encombrement.addiNiv(((Vetements)this.objEquiper[i]).getEncombrement());
            }
        }

        if (this.armeAtk != null){
            maniabilite = maniabilite.addiNiv(this.armeAtk.getManiabilite());
            impact = impact.addiNiv(this.armeAtk.getImpact());
        }

        Niveaux nivAdresse = new Niveaux(this.caracteristiques.getAdresse());
        Niveaux nivResistance = new Niveaux(this.caracteristiques.getResistance());
        Niveaux nivForce = new Niveaux(this.caracteristiques.getForce());
        this.setInitiative(nivAdresse.soustracNiv(encombrement));
        this.setAttaque(nivAdresse.addiNiv(maniabilite));
        this.setEsquive(nivAdresse.soustracNiv(encombrement));
        this.setDefense(nivResistance.addiNiv(solidite));
        this.setDegat(nivForce.addiNiv(impact));
    }

    Caracteristiques getCaracteristique(){
        return this.caracteristiques;
    }

    void setSac(ArrayList<Objet> value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.sac = value;
    }

    ArrayList<Objet> getSac() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.sac;
    }

    void addObjetSac(Objet obj){
        this.sac.add(obj);
    }

    void removeObjetSac(Objet obj){
        this.sac.remove(obj);
    }

    Objet[] getObjEquiper(){
        return this.objEquiper;
    }

    Objet getObjEquiper1() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.objEquiper[0];
    }

    void setObjEquiper1(Objet value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.objEquiper[0] = value;
    }

    Objet getObjEquiper2() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.objEquiper[1];
    }

    void setObjEquiper2(Objet value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.objEquiper[1] = value;
    }

    int getNbPA() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.nbPA;
    }

    void setNbPA(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.nbPA = value;
    }

    Armes getArmeAtk(){
        return this.armeAtk;
    }

    void setArmeAtk(Armes value) {
        this.armeAtk = value;
    }

    int getNumPJ(){
        return this.numPJ;
    }

    void setNumPJ(int value) {
        this.numPJ = value;
    }

    int getNiveauExp(){
        return this.niveauxExp;
    }

    void setNiveauExp(int exp){
        this.niveauxExp = this.getExperience()/20;
    }

    public boolean sacVide() {
        for (int i = 0; i < this.sac.size(); i++){
            if (sac.get(i) != null)
                return false;
        }
        return true;
    }

    public boolean objEquiperVide() {
        if (this.getObjEquiper1() == null && this.getObjEquiper2() == null)
            return true;
        return false;

    }

    public void deplacement(){
        this.nbPA = this.nbPA - 2;
    }

    public boolean attaquer(Personnage cible, Armes arme) {
        this.nbPA = this.nbPA - 3;
        int atk = this.getAttaque().calculScore();
        int esq = cible.getEsquive().calculScore();
        System.out.println(atk + " " + esq);
        if  (atk > esq) {
            int deg = this.getDegat().calculScore(); 
            int def = cible.getDefense().calculScore();
            int attaque = deg - def; 
            if (attaque > 0){
                int nbBlessure = attaque / 3;
                System.out.println(atk + " " + esq + " " + deg + " " + def + " " + nbBlessure);
                cible.setBlessure(cible.getBlessure()-nbBlessure);
                this.setExperience(this.getExperience()+1);
                return true;
            }
        }
        return false;

    }

    public void utiliser(Objet obj) {
        this.nbPA = this.nbPA - obj.getCoutUtil();
    }

    public void ramasser() {
        this.nbPA = this.nbPA - 2;
    }

    public void mourir(){
        ArrayList<Objet> toutObjetPJ = new ArrayList<Objet>();

        for(int i = 0; i < 2; i++)
            toutObjetPJ.add(this.objEquiper[i]);

        for(int i = 0; i < this.sac.size(); i++)
            toutObjetPJ.add(this.sac.get(i));

        Objet drop = toutObjetPJ.get(0);
        for(int i = 1; i < toutObjetPJ.size(); i++){
            Objet obj = toutObjetPJ.get(i);
            if(obj != null){
                if(drop.getCoutUtil() <= obj.getCoutUtil())
                    drop = obj;
            }
        }
        this.getPosition().setContenu(drop);

    }

    public String toString(){
        System.out.println(this.getNom());
        String objet = new String("Vos objet equipes :");
        for (int i = 0; i < 2; i++) {
            if (this.objEquiper[i] != null)
               objet = objet + "\n" + this.objEquiper[i];
            else 
               objet = objet + "\n" + "- Vide";
        }
        objet +=  "\n" + "Votre arme d'attaque :";
        if (armeAtk == null)
            objet +=  " - Vos poings \n";
        else
            objet += this.armeAtk;
        String sac = new String("Votre sac :");
        if (this.sac.size() > 0){
                for (int i = 0; i < this.sac.size(); i++) {
                sac = sac + "\n" + this.sac.get(i);
            }
        }
        else {
            sac = "Sac vide";
        }

        String carac = new String("Vos Caracteristiques :\n"
            + "- Force      : " + this.caracteristiques.getForce() + "\n"
            + "- Adresse    : " + this.caracteristiques.getAdresse() + "\n"
            + "- Resistance : " + this.caracteristiques.getResistance() + "\n"
            + "+ Initiative : " + this.getInitiative() + "\n"
            + "+ Attaque    : " + this.getAttaque() + "\n"
            + "+ Esquive    : " + this.getEsquive() + "\n"
            + "+ Defense    : " + this.getDefense() + "\n"
            + "+ Degat      : " + this.getDegat() + "\n");

        String sante = new String("\nVotre sante : ");
        if (this.getBlessure() == 6)
            sante += "Aucune blessure \n";
        else if (this.getBlessure() == 5)
            sante += "Blessures superficielles \n";
        else if (this.getBlessure() == 4)
            sante += "Legerement blesse \n";
        else if (this.getBlessure() == 3)
            sante += "Blesse \n";
        else if (this.getBlessure() == 2)
            sante += "Gravement blesse \n";
        else if (this.getBlessure() == 1)
            sante += "Inconscient \n";
        else if (this.getBlessure() == 0)
            sante += "Mort \n";

        String nbPA = new String("Vos points d'actions " + this.nbPA);

        String actionPossible = new String("Vous pouvez :" + "\n"
            + "1 - vous deplacer (2PA)" + "\n"
            + "2 - attaquer (3PA)" + "\n"
            + "3 - utiliser un objet (Variable)" + "\n"
            + "4 - ramasser/deposer un objet (2PA)" + "\n"
            + "5 - finir et garder les PA restants" + "\n"
            + "6 - pause" + "\n");

        String niveau = new String("Votre chevalier est niveau " + this.niveauxExp + "\n"
            + "7 - utiliser vos niveaux pour vous renforcez \n");

        String s = new String();
        s = objet + "\n" + sac + "\n" + carac + "\n"+ sante + "\n" + nbPA + "\n" + actionPossible + niveau;
        return s;
    }


}
