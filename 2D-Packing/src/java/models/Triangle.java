package models;

public class Triangle extends Objet2D {
    private int base;

    public Triangle(int id_obj, int base, int hauteur) {
        super(id_obj, base); // Base et hauteur du triangle isocèle
        this.base = base;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }
    
}
