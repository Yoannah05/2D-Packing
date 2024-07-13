package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rect extends Bac {
    protected static int height;
    protected List<Integer> heightLeft;
    protected List<Integer> widthLeft;
    protected HashMap<Integer, List<Objet2D>> lignes;

    public Rect(int id_bac) {
        super(id_bac);
        this.initSpaceLeft();
        this.lignes = new HashMap<>();
    }

    public Rect(int idBac, int widthBac, int height) {
        super(idBac, widthBac);
        Rect.height = height;
        this.heightLeft = new ArrayList<>();
        this.widthLeft = new ArrayList<>();
        this.lignes = new HashMap<>();
    }

    public static int getHeight() {
        return Rect.height;
    }

    public boolean addObjetFF(Objet2D objet) {
        if (lignes.isEmpty()) {
            createLine(objet);
            return true;
        }

        for (Map.Entry<Integer, List<Objet2D>> entry : lignes.entrySet()) {
            int ligne = entry.getKey();
            if (fitInLine(objet, ligne)) {
                lignes.get(ligne).add(objet);
                objet.setId_ligne(ligne);
                updateSpaceLeft(objet, ligne);
                return true;
            }
        }

        return false;
    }

    private boolean fitInLine(Objet2D objet, int ligne) {
        return objet.getWidth() <= widthLeft.get(ligne) && objet.getHeight() <= heightLeft.get(ligne);
    }

    private void createLine(Objet2D objet) {
        int newLineHeight = objet.getHeight();
        if (newLineHeight <= heightLeft.get(0)) {
            int newLineIndex = lignes.size();
            lignes.put(newLineIndex, new ArrayList<>());
            lignes.get(newLineIndex).add(objet);
            updateSpaceLeft(objet, newLineIndex);
            objet.setId_ligne(newLineIndex);
        }
    }

    public List<Integer> getHeightLeft() {
        return heightLeft;
    }

    public List<Integer> getWidthLeft() {
        return widthLeft;
    }

    public HashMap<Integer, List<Objet2D>> getLignes() {
        return lignes;
    }

    public void updateSpaceLeft(Objet2D obj, int ligne) {
        heightLeft.set(ligne, heightLeft.get(ligne) - obj.getHeight());
        widthLeft.set(ligne, widthLeft.get(ligne) - obj.getWidth());
    }

    public void initSpaceLeft() {
        this.spaceLeft = width;
        this.heightLeft = new ArrayList<>();
        this.widthLeft = new ArrayList<>();
        for (int i = 0; i < 3; i++) { // Adjust the number of initial lines as needed
            heightLeft.add(height);
            widthLeft.add(width);
        }
    }

    public void setHeightLeft(List<Integer> heightLeft) {
        this.heightLeft = heightLeft;
    }

    public void setWidthLeft(List<Integer> widthLeft) {
        this.widthLeft = widthLeft;
    }

    public static void setHeight(int height) {
        Rect.height = height;
    }
}
