import java.io.Serializable;

public class Case implements Serializable{
	private int positionX;
    private int positionY;
    private Object contenu;
    private char representation;
    private int numCase;

    public Case(int x, int y, int numCase){
    	this.positionX = x;
    	this.positionY = y;
    	this.contenu = null;
        this.representation = ' ';
        this.numCase = numCase;
    }

    public Case(int x, int y, Object contenu, int numCase){
    	this.positionX = x;
    	this.positionY = y;
    	this.contenu = contenu;
        setRepresentation();
        this.numCase = numCase;
    }

    public void setRepresentation(){
        PJ perso;
        if (this.contenu instanceof Armes)
            this.representation = '|';
        else if (this.contenu instanceof Vetements)
            this.representation = '*';
        else if (this.contenu instanceof Consommable)
            this.representation = '~';
        else if (this.contenu instanceof PNJ)
            this.representation = 'M';
        else if (this.contenu instanceof PJ){
            perso = (PJ)this.contenu;
            this.representation = String.valueOf(perso.getNumPJ()).charAt(0);
        }
        else if (this.contenu instanceof Mur)
            this.representation = '#';
        else if (this.contenu == null)
            this.representation = ' ';
    }

    public char getRepresentation(){
        return this.representation;
    }

    public int getPositionX(){
        return this.positionX;
    }

    public int getPositionY(){
        return this.positionY;
    }

    public Object getContenu(){
        return this.contenu;
    }

    public void setContenu(Object nouv){
        this.contenu = nouv;
        this.setRepresentation();
    }

    public int getNumCase(){
        return this.numCase;
    }

    public boolean caseVide(){
        if(this.contenu == null)
            return true;
        return false;
    }

    public void vider(){
        this.contenu = null;
        this.setRepresentation();
    }

    public String toString(){
        String s = String.valueOf(this.representation);
        return s;
    }

}