import java.io.Serializable;
import java.util.*;

public abstract class Objet implements Serializable{
    private String nom;
    private int coutUtil;
    private Case position = null;

    public Objet(){
        this.nom = null;
        this.coutUtil = 0;
    }

    public Objet(String nom, int cout){
        this.nom = nom;
        this.coutUtil = cout;
    }


    String getNom(){
        return this.nom;
    }

    void setNom(String nom){
        this.nom = nom;
    }

    int getCoutUtil() {
        return this.coutUtil;
    }

    void setCoutUtil(int value) {
        this.coutUtil = value;
    }

    Case getPosition(){
        return this.position;
    }

    void setPosition(Case pos){
        this.position = pos;
    }


}
