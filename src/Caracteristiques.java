import java.util.*;
import java.io.Serializable;

public class Caracteristiques implements Serializable {
    private int force;
    private int adresse;
    private int resistance;
    
    public Caracteristiques() {
    	choixCarac();
        }

    public Caracteristiques(int f, int a, int r) {
        this.force = f;
        this.adresse = a;
        this.resistance = r;
        }

    public void choixCarac(){
        Scanner scanner = new Scanner( System.in );
        int degres = 18;

        while (true) {
        System.out.println( "Vous disposez de 18 degres a repartir entre 3 Caracteristiques : " );
        System.out.print("Force : ");
        int f = scanner.nextInt();
        this.force = f;
        System.out.println(" ");

        System.out.print("Adresse : ");
        int a = scanner.nextInt();
        this.adresse = a;
        System.out.println(" ");

        System.out.print("Resistance : ");
        int r = scanner.nextInt();
        this.resistance = r;
        System.out.println(" ");

        System.out.print("Validez(V) ou Recommencer(R) : ");
        char rep = scanner.next().charAt(0);
        System.out.println(" ");
        if (rep == 'V')
            break;
        
        }
    }

    int getForce() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.force;
    }

    void setForce(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.force = value;
    }

    int getAdresse() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.adresse;
    }

    void setAdresse(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.adresse = value;
    }

    int getResistance() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.resistance;
    }

    void setResistance(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.resistance = value;
    }

    public void Operation() {
    }

}
