package Models;

import java.util.List;

public class Bac {
    private int id_bac;
    private static int width;
    private List<Objet1D> objets;
    public int getId_bac() {
        return id_bac;
    }
    public void setId_bac(int id_bac) {
        this.id_bac = id_bac;
    }
    public static int getWidth() {
        return width;
    }
    public static void setWidth(int width) {
        Bac.width = width;
    }
    public List<Objet1D> getObjets() {
        return objets;
    }
    public void setObjets(List<Objet1D> objets) {
        this.objets = objets;
    }
}
