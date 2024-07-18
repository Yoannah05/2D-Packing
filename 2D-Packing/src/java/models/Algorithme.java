package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithme {
    static int widthBac = 0;

    public static void setWidthBac(int widthBac) {
        Algorithme.widthBac = widthBac;
    }

    public static boolean isAllObjectPlaced(List<Objet1D> objets) {
        for (Objet1D o : objets) {
            if (o.getId_bac() == -1) {
                return false;
            }
        }
        return true;
    }

    public static List<Bac> firstFit(List<Objet1D> objetsList) {
        List<Bac> bacs = new ArrayList<>();
        Bac.setWidth(widthBac);
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
        Algorithme.reinitializeObjetID(objetsList);
        List<Bac> bacs = new ArrayList<>();
        int idBac = 1;
        Bac bac = new Bac(idBac);
        while (!Algorithme.isAllObjectPlaced(objetsList)) {
            Objet1D objetOptimal = null;
            int minSpaceRemaining = Integer.MAX_VALUE;
            for (Objet1D o : objetsList) {
                // on verifie si l'objet n'appartient pas à un bac
                // puis on verifie si l'espace libre du bac peut contenir l'objet
                if (o.getId_bac() == -1 && o.getWidth() <= bac.getSpaceLeft()) {
                    int tempEspaceRestant = bac.getSpaceLeft() - o.getWidth();
                    if (tempEspaceRestant < minSpaceRemaining) {
                        objetOptimal = o;
                        minSpaceRemaining = tempEspaceRestant;
                    }
                }
            }
            if (objetOptimal != null) {
                objetOptimal.setId_bac(idBac);
                bac.diminueSpaceLeft(objetOptimal.getWidth());
                bac.getObjets().add(objetOptimal);
                // objetsList.remove(objetOptimal); // Retirez l'objet du liste des objets à
                // placer
            } else {
                idBac++; // Incrémenter le numéro de bac si aucun objet ne peut être placé
                bacs.add(bac);
                bac = new Bac(idBac);
            }
        }
        bacs.add(bac); // Ajouter le dernier bac
        return bacs;
    }

    private static void reinitializeObjetID(List<Objet1D> objetsList) {
        for (Objet1D objet1D : objetsList) {
            objet1D.setId_bac(-1);
        }
    }

    public static List<Bac> worstFit(List<Objet1D> objetsList) {
        List<Bac> bacs = new ArrayList<>();
        int idBac = 0;

        for (Objet1D item : objetsList) {
            Bac worstBac = null;
            int maxSpace = -1;

            for (Bac bac : bacs) {
                int remainingSpace = bac.getSpaceLeft();
                if (item.getWidth() <= remainingSpace && remainingSpace > maxSpace) {
                    maxSpace = remainingSpace;
                    worstBac = bac;
                }
            }
            if (worstBac != null) {
                worstBac.addObjetFF(item);
            } else {
                Bac newBac = new Bac(++idBac);
                newBac.addObjetFF(item);
                bacs.add(newBac);
            }
        }

        return bacs;
    }

    public static List<Bac> bruteForce(List<Objet1D> items) {
        List<List<Objet1D>> permutations = generatePermutations(items);
        List<Bac> bestSolution = null;
        int minBinsUsed = Integer.MAX_VALUE;

        for (List<Objet1D> permutation : permutations) {
            List<Bac> bacs = new ArrayList<>();
            Bac.setWidth(widthBac);
            for (Objet1D item : permutation) {
                boolean placed = false;
                for (Bac bac : bacs) {
                    if (bac.addObjetFF(item)) {
                        placed = true;
                        break;
                    }
                }
                if (!placed) {
                    Bac newBac = new Bac(bacs.size() + 1);
                    newBac.addObjetFF(item);
                    bacs.add(newBac);
                }
            }
            if (bacs.size() < minBinsUsed) {
                minBinsUsed = bacs.size();
                bestSolution = bacs;
            }
        }

        return bestSolution;
    }

    private static List<List<Objet1D>> generatePermutations(List<Objet1D> items) {
        List<List<Objet1D>> permutations = new ArrayList<>();
        generatePermutationsHelper(items, 0, permutations);
        return permutations;
    }

    private static void generatePermutationsHelper(List<Objet1D> items, int index, List<List<Objet1D>> permutations) {
        if (index == items.size() - 1) {
            permutations.add(new ArrayList<>(items));
        } else {
            for (int i = index; i < items.size(); i++) {
                Collections.swap(items, i, index);
                generatePermutationsHelper(items, index + 1, permutations);
                Collections.swap(items, i, index);
            }
        }
    }
    public static List<Bac> pack1D(List<Objet1D> objetsList, String algorithme) {
        switch (algorithme) {
            case "ff":
                return Algorithme.firstFit(objetsList);
            case "bf":
                return Algorithme.bestFit(objetsList);
            case "wf":
                return Algorithme.worstFit(objetsList);
            case "brf":
                return Algorithme.bruteForce(objetsList);
            default:
                throw new IllegalArgumentException("Algorithme non supporté : " + algorithme);
        }
    }

    // 2D PACKING ALGORITHM
    public static Rect FFDH(List<Objet2D> objetsList, boolean considerRotation) {
        // Sort the objects in decreasing order of height
        Objet2D.sort(objetsList);
        Rect rect = new Rect(1);
        for (Objet2D objet : objetsList) {
            if (!rect.addObjetFF(objet, considerRotation)) {
                break;
            }
        }
        return rect;
    }

    public static Rect BFDH(List<Objet2D> objetsList, boolean considerRotation) {
        // Sort the objects in decreasing order of width
        Objet2D.sort(objetsList);
        Rect rect = new Rect(1);
        for (Objet2D objet : objetsList) {
            if (!rect.addObjetBF(objet, considerRotation)) {
                break;
            }
        }
        return rect;
    }

    public static Rect NFDH(List<Objet2D> objetsList, boolean considerRotation) {
        // Sort the objects in decreasing order of height
        Objet2D.sort(objetsList);
        Rect rect = new Rect(1);
        for (Objet2D objet : objetsList) {
            if (!rect.addObjetNF(objet, considerRotation)) {
                break;
            }
        }
        return rect;
    }

    public static Rect pack2D(List<Objet2D> objetsList, String algorithme, boolean considerRotation) {
        switch (algorithme) {
            case "ffdh":
                return Algorithme.FFDH(objetsList, considerRotation);
            case "bf":
                return Algorithme.BFDH(objetsList, considerRotation);
            case "nfdh":
                return Algorithme.NFDH(objetsList, considerRotation);
            default:
                throw new IllegalArgumentException("Algorithme non supporté : " + algorithme);
        }
    }
    public static Rect heuristique(List<Objet2D> objetsList, boolean considerRotation) {
        // Sort the objects in decreasing order of height
        Objet2D.sort(objetsList);
        Rect rect = new Rect(1);
        for (Objet2D objet : objetsList) {
            if (!rect.addObjetBF(objet, considerRotation)) {
                break;
            }
        }
        return rect;
    }
    public static Rect pack2D3Forme(List<Objet2D> objetsList, String algorithme, boolean considerRotation) {
        switch (algorithme) {
            case "h":
                return Algorithme.heuristique(objetsList, considerRotation);
            case "bf":
                return Algorithme.BFDH(objetsList, considerRotation);
            default:
                throw new IllegalArgumentException("Algorithme non supporté : " + algorithme);
        }
    }
}
    
