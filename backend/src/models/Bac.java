package models;

import java.util.ArrayList;
import java.util.List;

public class Bac {
    private int id_bac;
    protected static int width;
    protected int spaceLeft;
    private List<Objet1D> objets;

    //  constructor
    public Bac(int id_bac) {
        this.id_bac = id_bac;
        this.initSpaceLeft();
        this.objets = new ArrayList<>();
    }

    public Bac(int idBac, int widthBac) {
        this.id_bac = idBac;
        Bac.width = widthBac;
        this.initSpaceLeft();
        this.objets = new ArrayList<>();
    }

    //  add object with first fit algorithm
    public boolean addObjetFF(Objet1D objet) {
        if (objet.getWidth() <= getSpaceLeft()) { // Check if the object fits within the current width of the bac
            objets.add(objet); // Add the object to the beginning of the list
            objet.setId_bac(this.getId_bac());
            setSpaceLeft(this.spaceLeft - objet.getWidth());
            return true;
        } 
        return false;
    }

    //  add object with best fit algorithm
    //  add object with worst fit algorithm

    // ------ GETTERS -----------
    public int getSpaceLeft() {
        return spaceLeft;
    }
    public int getId_bac() {
        return id_bac;
    }
    public static int getWidth() {
        return width;
    }
    public List<Objet1D> getObjets() {
        return objets;
    }
    
    // ------ SETTERS -----------
    public void setSpaceLeft(int spaceLeft) {
        this.spaceLeft = spaceLeft;
    }
    public void setId_bac(int id_bac) {
        this.id_bac = id_bac;
    }
    public static void setWidth(int width) {
        Bac.width = width;
    }
    public void initSpaceLeft() {
        this.spaceLeft = width;
    }
    public void setObjets(List<Objet1D> objets) {
        this.objets = objets;
    }
    public void diminueSpaceLeft(int nbr){
        this.spaceLeft -= nbr;
    }
}
