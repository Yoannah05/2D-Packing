package models;

import java.util.List;
import java.util.Random;

public class Objet1D {
    private int id_obj;
    protected int width;
    private int id_bac;
    String color;

    public static void add(List<Objet1D> list, int id_obj, String width) {
        Objet1D objet1 = new Objet1D(id_obj , Integer.parseInt(width));
        list.add(objet1);
    }
    public Objet1D(int id_obj, int width) {
        this.id_obj = id_obj;
        this.width = width;
        this.id_bac = -1;
        this.generateRandomColor();
    }

    public void  generateRandomColor() {
        Random rand = new Random();
        StringBuilder color = new StringBuilder("#");
        // Generate random RGB values
        for (int i = 0; i < 3; i++) {
            int r = rand.nextInt(256); // Generate a random integer between 0 and 255
            // Ensure the value is within the hexadecimal range (00-FF)
            color.append(String.format("%02X", r));
        }
        this.color = color.toString();
    }
    public int getWidth() {
        return width;
    }

    public String getColor() {
        return color;
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
