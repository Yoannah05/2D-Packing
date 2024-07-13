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

    // Trie les objets en ordre décroissant de hauteur (ou de rayon pour le cercle)
    private static void sortObjects(List<Objet2D> objetsList) {
        Collections.sort(objetsList, new Comparator<Objet2D>() {
            @Override
            public int compare(Objet2D o1, Objet2D o2) {
                // Comparaison basée sur la hauteur ou le rayon
                if (o1 instanceof Cercle && o2 instanceof Cercle) {
                    return Double.compare(((Cercle) o2).getRayon(), ((Cercle) o1).getRayon());
                } else {
                    return Integer.compare(o2.getHeight(), o1.getHeight());
                }
            }
        });
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
