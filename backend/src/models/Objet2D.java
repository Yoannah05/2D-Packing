package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Objet2D extends Objet1D{
    private int id_ligne;
    private int height;
    private int rotation;
    public Objet2D(int id_obj, int width, int height) {
        super(id_obj, width);
        this.setHeight(height);
    }

    // Sort the objects in decreasing order of height
    public static void sort(List<Objet2D> objet2ds) {
        Collections.sort(objet2ds, new Comparator<Objet2D>() {
            @Override
            public int compare(Objet2D o1, Objet2D o2) {
                return Integer.compare(o2.getHeight(), o1.getHeight());
            }
        });
    }
    //  Getters
    public int getId_ligne() {
        return id_ligne;
    }
    public int getRotation() {
        return rotation;
    }
    public int getHeight() {
        return height;
    }
    //  Setters
    public void setHeight(int height) {
        this.height = height;
    }
    public void setId_ligne(int id_ligne) {
        this.id_ligne = id_ligne;
    }
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
    
}
