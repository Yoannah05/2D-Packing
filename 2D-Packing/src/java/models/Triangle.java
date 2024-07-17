package models;

public class Triangle extends Objet2D {
    private int base;

    public Triangle(int id_obj, int base, int hauteur) {
        super(id_obj, base, hauteur); // Base et hauteur du triangle isocèle
        this.base = base;
    }
    public int getBase() {
        return base;
    }
    public void setBase(int base) {
        this.base = base;
    }
    @Override
    public int getWidth() {
        return (getRotation() == 1) ? getHeight() : base;
    }
    @Override
    public int getHeight() {
        return (getRotation() == 1) ? base : super.getHeight();
    }
    
}
