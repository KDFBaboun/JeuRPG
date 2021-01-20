import java.io.Serializable;

public class Personnage implements Serializable{

    private String nom;
    private Niveaux initiative;
    private Niveaux attaque;
    private Niveaux esquive;
    private Niveaux defense;
    private Niveaux degat;
    private int blessure;
    private Case position;
    private int experience;

    public Personnage() {
        this.nom = "";
    	this.initiative = new Niveaux();
    	this.attaque = new Niveaux();
    	this.esquive = new Niveaux();
    	this.defense = new Niveaux();
    	this.degat = new Niveaux();
    	this.blessure = 6;
        this.experience = 0;
    }

    public Personnage(String nom) {
        this.nom = nom;
        this.initiative = new Niveaux();
        this.attaque = new Niveaux();
        this.esquive = new Niveaux();
        this.defense = new Niveaux();
        this.degat = new Niveaux();
        this.blessure = 6;
        this.experience = 0;
    }

    public Personnage(String nom, int exp) {
        this.nom = nom;
        this.initiative = new Niveaux();
        this.attaque = new Niveaux();
        this.esquive = new Niveaux();
        this.defense = new Niveaux();
        this.degat = new Niveaux();
        this.blessure = 6;
        this.experience = exp;
    }

    public Personnage(int force, int adr, int res) {
        this.initiative = new Niveaux(adr);
        this.attaque = new Niveaux(adr);
        this.esquive = new Niveaux(adr);
        this.defense = new Niveaux(res);
        this.degat = new Niveaux(force);
        this.blessure = 6;
        
    }

    String getNom(){
        return this.nom;
    }

    void setNom(String nom){
        this.nom = nom;
    }

    Niveaux getDefense() {
        return this.defense;
    }

    void setDefense(Niveaux value) {
        this.defense = value;
    }

    Niveaux getInitiative() {
        return this.initiative;
    }

    void setInitiative(Niveaux value) {
        this.initiative = value;
    }

    Niveaux getEsquive() {
        return this.esquive;
    }

    void setEsquive(Niveaux value) {
        this.esquive = value;
    }

    Niveaux getAttaque() {
         return this.attaque;
    }

    void setAttaque(Niveaux value) {
       this.attaque = value;
    }

    Niveaux getDegat() {
        return this.degat;
    }

    void setDegat(Niveaux value) {
        this.degat = value;
    }

    int getBlessure() {
        return this.blessure;
    }

    void setBlessure(int value) {
        this.blessure = value;
    }

    Case getPosition(){
        return this.position;
    }

    void setPosition(Case pos){
        this.position = pos;
    }

    int getExperience(){
        return this.experience;
    }

    void setExperience(int exp){
        this.experience = exp;
    }

}
