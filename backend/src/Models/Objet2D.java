package Models;

public class Objet2D extends Objet1D{
    private int height;
    private int rotation;
    public Objet2D(int id_obj, int width) {
        super(id_obj, width);
        //TODO Auto-generated constructor stub
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getRotation() {
        return rotation;
    }
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
