import java.io.Serializable;

public class Niveaux implements Serializable {

    private int de;
    private int ajout;

    public Niveaux(){
        this.de = 0;
        this.ajout = 0;
    }

    public Niveaux(int degres){
        this.de = degres/3;
        this.ajout = degres%3;
    }

    public Niveaux(int de, int ajout){
        this.de = de;
        this.ajout = ajout;
    }

    public Niveaux(Niveaux niv){
        this.de = niv.de;
        this.ajout = niv.ajout;
    }

    int getDe() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.de;
    }

    void setDe(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.de = value;
    }

    int getAjout() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.ajout;
    }

    void setAjout(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.ajout = value;
    }

    public Niveaux addiNiv(Niveaux niv){
        Niveaux nivAddi = new Niveaux(this.getDe() + niv.getDe(), this.getAjout() + niv.getAjout());
        return nivAddi;
    }

    public Niveaux soustracNiv(Niveaux niv){
        int newDe = this.getDe() - niv.getDe();
        int newAjout = this.getAjout() - niv.getAjout();
        if (newDe < 0)
            newDe = 0;
        if (newAjout < 0)
            newAjout = 0;
        return new Niveaux (newDe, newAjout);
    }

    public int calculScore() {
        int score = this.ajout;
        for (int i=0; i<this.de; i++){
            score += (1 + (int)( Math.random()*((6-1)) + 1));
        } 
        return score;

    }

    public String toString(){
        String s = new String(this.de + "D + " + this.ajout);
        return s;
    }

}
