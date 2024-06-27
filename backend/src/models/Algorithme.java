package models;

import java.util.ArrayList;
import java.util.List;

public class Algorithme {

    public static boolean isAllObjectPlaced(List<Objet1D> objets){
        for (Objet1D o : objets) {
            if (o.getId_bac() == -1) {
                return false;
            }
        }
        return true;
    }

    public static List<Bac> firstFit (List<Objet1D> objetsList) {
        List<Bac> bacs = new ArrayList<>();
        for (Objet1D objet : objetsList) {
            boolean placed = false;
            int nbBac = 1;
            for (Bac bac : bacs) {
                if (bac.addObjetFF(objet)) {
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                nbBac++;
                Bac newBac = new Bac(nbBac);
                newBac.addObjetFF(objet);
                bacs.add(newBac);
            }
        }
        return bacs;
    }

    public static List<Bac> bestFit(List<Objet1D> objetsList) {
        List<Bac> bacs = new ArrayList<>();
        int idBac = 0;
        Bac bac = new Bac(idBac);

        while (!objetsList.isEmpty()) {
            Objet1D objetOptimal = null;
            int minSpaceRemaining = Integer.MAX_VALUE;

            for (Objet1D o : objetsList) {
                //on verifie si l'objet n'appartient pas à un bac
                //puis on verifie si l'espace libre du bac peut contenir l'objet
                if (o.getId_bac() == -1 && o.getWidth() <= bac.getSpaceLeft()) {
                    int tempEspaceRestant = bac.getSpaceLeft() - o.getWidth();
                    if (tempEspaceRestant < minSpaceRemaining) {
                        objetOptimal = o;
                        minSpaceRemaining = tempEspaceRestant;
                    }
                }
            }

            if (objetOptimal!= null) {
                objetOptimal.setId_bac(idBac);
                bac.diminueSpaceLeft(objetOptimal.getWidth());
                bac.getObjets().add(objetOptimal);
                objetsList.remove(objetOptimal); // Retirez l'objet du liste des objets à placer
            } else {
                idBac++; // Incrémenter le numéro de bac si aucun objet ne peut être placé
                bacs.add(bac);
                bac = new Bac(idBac);
            }
        }
        bacs.add(bac); // Ajouter le dernier bac
        return bacs;
    }

    public static List<Bac> pack1D(List<Objet1D> objetsList, String algorithme) {
        List<Bac> bacs = new ArrayList<>();
        switch (algorithme) {
            case "ff":
                return Algorithme.firstFit(objetsList);
            case "bf":
                return Algorithme.bestFit(objetsList);
            case "wf":
                return Algorithme.worstFit(objetsList);
            case "brf":
                break;
        }
        return bacs;
    }

    private static List<Bac> worstFit(List<Objet1D> objetsList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'worstFit'");
    }

    //  2D PACKING ALGORITHM
    public static List<Rect> FFDH(List<Objet2D> objetsList) {
        // Sort the objects in decreasing order of height
        Objet2D.sort(objetsList);
        List<Rect> rects = new ArrayList<>();
        for (Objet2D objet : objetsList) {
            boolean placed = false;
            for (Rect Rect : rects) {
                if (Rect.addObjetFF(objet)) {
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                Rect newRect = new Rect(rects.size() + 1);
                newRect.addObjetFF(objet);
                rects.add(newRect);
            }
        }
        return rects;
    }

    public static List<Rect> pack2D(List<Objet2D> objetsList, String algorithme) {
        List<Rect> rects = new ArrayList<>();
        switch (algorithme) {
            case "ffdh":
                return Algorithme.FFDH(objetsList);
        }
        return rects;
    }
}
