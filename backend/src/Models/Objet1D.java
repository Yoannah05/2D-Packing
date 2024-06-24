package Models;

public class Objet1D {
    private int id_obj;
    private int width;
    private int id_bac;


    public Objet1D(int id_obj, int width) {
        this.id_obj = id_obj;
        this.width = width;
        this.id_bac = -1;
    }
    
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getId_bac() {
        return id_bac;
    }
    public void setId_bac(int id_bac) {
        this.id_bac = id_bac;
    }
    public int getId_obj() {
        return id_obj;
    }
    public void setId_obj(int id_obj) {
        this.id_obj = id_obj;
    }
}
