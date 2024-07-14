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
        this.initSpaceLeft();
    }

    public boolean addObjetBF(Objet2D objet, boolean considerRotation) {
        int bestLine = -1;
        int minSpaceLeft = Integer.MAX_VALUE;
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
        int newLineHeight = objet.getHeight();
        if (newLineHeight <= Rect.height - getTotalHeight() && objet.getWidth() <= Rect.width) {
            lignes.put(newLineIndex, new ArrayList<>());
            lignes.get(newLineIndex).add(objet);
            heightLeft.add(newLineHeight);
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

    public List<Integer> getWidthLeft() {
        return widthLeft;
    }

    public HashMap<Integer, List<Objet2D>> getLignes() {
        return lignes;
    }

    // Setters
    // public void updateSpaceLeft(Objet2D obj, int ligne) {
    //     widthLeft.set(ligne, widthLeft.get(ligne) - obj.getWidth());
    //     heightLeft.set(ligne, heightLeft.get(ligne) - obj.getHeight());
    // }
    public void updateSpaceLeft(Objet2D obj, int ligne) {
        // Update the width left for the line
        widthLeft.set(ligne, widthLeft.get(ligne) - obj.getWidth());
        // Calculate the remaining height left for the line
        List<Objet2D> objectsInLine = lignes.get(ligne);
        int maxRemainingHeight = Rect.height;
        for (Objet2D currentObject : objectsInLine) {
            int remainingHeight = Rect.height - currentObject.getHeight();
            if (remainingHeight > maxRemainingHeight) {
                maxRemainingHeight = remainingHeight;
            }
        }
        heightLeft.set(ligne, maxRemainingHeight);
    }

    public void initSpaceLeft() {
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
