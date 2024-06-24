package models;

import java.util.ArrayList;
import java.util.List;

public class Algorithme {
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

    public static List<Bac> pack1D(List<Objet1D> objetsList, String algorithme) {
        List<Bac> bacs = new ArrayList<>();
        switch (algorithme) {
            case "ff":
                return Algorithme.firstFit(objetsList);
            case "bf":
                break;
            case "wf":
                break;
            case "brf":
                break;
        }
        return bacs;
    }
}
