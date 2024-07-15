package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rect extends Bac {
    protected static int height = 500;
    protected List<Integer> heightLeft;
    protected List<Integer> widthLeft;
    protected HashMap<Integer, List<Objet2D>> lignes;

    public Rect(int id_bac) {
        super(id_bac);
        this.initSpaceLeft();
        this.lignes = new HashMap<>();
    }
    public boolean addObjetBFDH(Objet2D objet) {
        if (widthLeft.get(0) < objet.getWidth() || heightLeft.get(0) < objet.getHeight()) {
            return false; // Not enough space
        }
        // Find the smallest rectangle that can accommodate the object
        // This is a simplified approach; in practice, you'd want to minimize wasted space
        for (int i = 0; i < widthLeft.size(); i++) {
            if (widthLeft.get(i) >= objet.getWidth() && heightLeft.get(i) >= objet.getHeight()) {
                // Found a suitable spot
                widthLeft.set(i, widthLeft.get(i) - objet.getWidth());
                heightLeft.set(i, heightLeft.get(i) - objet.getHeight());
                this.lignes.computeIfAbsent(i, k -> new ArrayList<>()).add(objet);
                objet.setId_ligne(i);
                return true;
            }
        }
    
        return false; // No suitable spot found
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
    public boolean fitsIn(Objet2D objet) {
        // Vérifier si l'objet peut s'insérer dans au moins une ligne du bac
        for (Map.Entry<Integer, List<Objet2D>> entry : lignes.entrySet()) {
            int ligne = entry.getKey();
            if (fitInLine(objet, ligne)) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour placer un objet dans ce bac rectangulaire
    @Override
    public void placeIn(Objet2D objet) {
        // Trouver et placer l'objet dans la première ligne disponible qui peut l'accueillir
        for (Map.Entry<Integer, List<Objet2D>> entry : lignes.entrySet()) {
            int ligne = entry.getKey();
            if (fitInLine(objet, ligne)) {
                lignes.get(ligne).add(objet);
                objet.setId_ligne(ligne);
                updateSpaceLeft(objet, ligne);
                break;
            }
        }
    }

}
