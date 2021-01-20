import java.io.Serializable;

public abstract class Equipement extends Objet implements Serializable {
    public Equipement(String nom, int coutUtil){
        super(nom, coutUtil);
    }
}
