package models;

import java.util.Collections;
import java.util.List;

public class Objet2D extends Objet1D {
    private int id_ligne;
    private int height;
    private int rotation; // 0: no rotation, 1: 90°, 2: 180°

    public Objet2D(int id_obj, int width, int height) {
        super(id_obj, width);
        this.setHeight(height);
        this.setRotation(0);
    }

    // Sort the objects in decreasing order of height
    public static void sort(List<Objet2D> objet2ds) {
        Collections.sort(objet2ds, (Objet2D o1, Objet2D o2) -> Integer.compare(o2.getWidth(), o1.getWidth()));
        Collections.sort(objet2ds, (Objet2D o1, Objet2D o2) -> Integer.compare(o2.getHeight(), o1.getHeight()));
    }


    // Rotate the object by the specified angle
    public void rotate(int angle) {
        rotation = switch (angle) {
            case 90 -> 1;
            case 180 -> 2;
            default -> 0;
        };
    }

    // Getters
    public int getId_ligne() {
        return id_ligne;
    }

    public int getRotation() {
        return rotation;
    }

    @Override
    public int getWidth() {
        if (rotation == 1) {
            return height;
        } else {
            return width;
        }
    }

    public int getHeight() {
        if (rotation == 1) { // If the object is rotated 90 degrees, width becomes its height
            return width;
        } else {
            return height;
        }
    }

    // Setters
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
