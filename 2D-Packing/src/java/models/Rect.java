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
        this.lignes = new HashMap<>();
        this.initAllSpaceLeft();
    }

    public boolean addObjetBF(Objet2D objet, boolean considerRotation) {
        int bestLine = -1;
        int minSpaceLeft = width;
        // Iterate through existing lines to find the best fit
        for (int i = 0; i < widthLeft.size(); i++) {
            if (considerRotation) {
                bestRotation(objet, i); // Rotate to best fit in this line
            }
            int spaceLeft = widthLeft.get(i) - objet.getWidth();
            if (widthLeft.get(i) >= objet.getWidth() && heightLeft.get(i) >= objet.getHeight()
                    && spaceLeft < minSpaceLeft) {
                bestLine = i;
                minSpaceLeft = spaceLeft;
            }
        }
        // If a suitable line is found, place the object there
        if (bestLine != -1) {
            lignes.get(bestLine).add(objet);
            objet.setId_ligne(bestLine);
            updateSpaceLeft(objet, bestLine);
            return true;
        } else {
            return createLine(objet, lignes.size());
        }
    }
    public boolean addObjetFF(Objet2D objet, boolean considerRotation) {
        // Try to place the object on existing lines
        for (Map.Entry<Integer, List<Objet2D>> entry : lignes.entrySet()) {
            int ligne = entry.getKey();
            if (considerRotation) {
                bestRotation(objet, ligne); // Rotate to best fit in this line
            }
            if (fitInLine(objet, ligne)) {
                lignes.get(ligne).add(objet);
                objet.setId_ligne(ligne);
                updateSpaceLeft(objet, ligne);
                return true;
            }
        }
        // If the object doesn't fit in any existing line, create a new line
        return createLine(objet, lignes.size());
    }
    public boolean addObjetNF(Objet2D objet, boolean considerRotation) {
        int lastline = lignes.size() - 1;
        // Check if the object fits in the last line
        if (lastline >= 0) {
            if (considerRotation) {
                bestRotation(objet, lastline); // Rotate to best fit in this line
            }
            if (objet.getWidth() <= widthLeft.get(lastline)) {
                lignes.get(lastline).add(objet);
                objet.setId_ligne(lastline);
                updateSpaceLeft(objet, lastline);
                return true;
            }
        }
        // Try to create a new line for the object
        return createLine(objet, lignes.size());
    }

    private boolean createLine(Objet2D objet, int newLineIndex) {
        if (objet.getWidth() <= Rect.width && getTotalHeight() + objet.getHeight() <= Rect.height) {
            lignes.put(newLineIndex, new ArrayList<>());
            lignes.get(newLineIndex).add(objet);
            heightLeft.add(objet.getHeight()); 
            widthLeft.add(Rect.width - objet.getWidth()); 
            objet.setId_ligne(newLineIndex);
            return true;
        }
        return false;
    }


    private void bestRotation(Objet2D objet, int ligne) {
        int minSpaceLeft = Integer.MAX_VALUE;
        int bestRotation = 0;
        for (int angle : new int[]{0, 90, 180}) {
            objet.rotate(angle);
            if (fitInLine(objet, ligne)) {
                int spaceLeft = widthLeft.get(ligne) - objet.getWidth();
                if (spaceLeft < minSpaceLeft) {
                    minSpaceLeft = spaceLeft;
                    bestRotation = angle;
                }
            }
            objet.rotate(360 - angle); // Reset rotation
        }
        // Apply the best rotation found
        objet.rotate(bestRotation);
    }

    private boolean fitInLine(Objet2D objet, int ligne) {
        return objet.getWidth() <= widthLeft.get(ligne) && objet.getHeight() <= heightLeft.get(ligne);
    }

    // Getters
    private int getTotalHeight() {
        return heightLeft.stream().mapToInt(Integer::intValue).sum();
    }
    public List<Integer> getHeightLeft() {
        return heightLeft;
    }

    public static int getHeight() {
        return Rect.height;
    } 
    public List<Integer> getWidthLeft() {
        return widthLeft;
    }

    public HashMap<Integer, List<Objet2D>> getLignes() {
        return lignes;
    }

    // Setters
    public void updateSpaceLeft(Objet2D obj, int ligne) {
        widthLeft.set(ligne, widthLeft.get(ligne) - obj.getWidth());
        // Update height left based on the objects in the line
        List<Objet2D> objectsInLine = lignes.get(ligne);
        int minHeight = Rect.height;
        for (Objet2D currentObject : objectsInLine) {
            int currentHeight = currentObject.getHeight();
            if (currentHeight < minHeight) {
                minHeight = currentHeight;
            }
        }
        heightLeft.set(ligne, minHeight); // Keep track of the minimum height in the line
    }


    private void initAllSpaceLeft() {
        this.spaceLeft = width;
        this.heightLeft = new ArrayList<>();
        this.widthLeft = new ArrayList<>();
        // initial line
        heightLeft.add(height);
        widthLeft.add(width);
        lignes.put(0, new ArrayList<>());
    }

    public void setHeightLeft(List<Integer> heightLeft) {
        this.heightLeft = heightLeft;
    }
    public static void setHeight(int height) {
        Rect.height = height;
    }
    public void setWidthLeft(List<Integer> widthLeft) {
        this.widthLeft = widthLeft;
    }
}
