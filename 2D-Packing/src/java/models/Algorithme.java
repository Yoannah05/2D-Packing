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

    // public static List<Bac> bruteForce(List<Objet1D> objetsList) {
    //     int n = objetsList.size();
    //     int minBins = n;
    //     List<Bac> bestSolution = null;
    //     List<int[]> partitions = generatePartitions(n);
    //     for (int[] partition : partitions) {
    //         List<Bac> bacs = createBins(partition, objetsList);
    //         if (bacs.size() < minBins) {
    //             minBins = bacs.size();
    //             bestSolution = bacs;
    //         }
    //     }

    //     return bestSolution;
    // }

    // private static List<int[]> generatePartitions(int n) {
    //     List<int[]> partitions = new ArrayList<>();
    //     int[] partition = new int[n];
    //     generatePartitionsRecursive(partitions, partition, 0, 0);
    //     return partitions;
    // }

    // private static void generatePartitionsRecursive(List<int[]> partitions, int[] partition, int index, int maxPart) {
    //     if (index == partition.length) {
    //         partitions.add(partition.clone());
    //         return;
    //     }
    //     for (int i = 0; i <= maxPart; i++) {
    //         partition[index] = i;
    //         generatePartitionsRecursive(partitions, partition, index + 1, Math.max(maxPart, i + 1));
    //     }
    // }

      // Fonction brute force pour le packing
      public static List<Bac> bruteForce(List<Objet1D> objets, List<Rect> bacs) {
        int[] meilleurPlacement = new int[objets.size()]; // Tableau pour le meilleur placement trouvé
        int[] meilleurEspaceRestant = new int[objets.size()]; // Tableau pour le meilleur espace restant trouvé
        int[] espaceRestantActuel = new int[bacs.size()]; // Tableau pour l'espace restant dans chaque bac actuel

        // Initialisation des tableaux
        for (int i = 0; i < bacs.size(); i++) {
            espaceRestantActuel[i] = bacs.get(i).getSpaceLeft();
        }

        // Appel à la fonction récursive pour explorer toutes les permutations des objets
        bruteForceRecursive(objets, bacs, 0, espaceRestantActuel, meilleurPlacement, meilleurEspaceRestant);

        // Placer les objets dans les bacs selon le meilleur placement trouvé
        for (int i = 0; i < objets.size(); i++) {
            objets.get(i).setId_bac(meilleurPlacement[i]);
        }
        return null;
    }

    // Fonction récursive pour explorer toutes les permutations des objets
    private static void bruteForceRecursive(List<Objet1D> objets, List<Rect> bacs, int index,
                                                   int[] espaceRestantActuel, int[] meilleurPlacement, int[] meilleurEspaceRestant) {
        // Base case: quand tous les objets ont été placés
        if (index == objets.size()) {
            // Calculer l'espace restant total dans les bacs pour cette permutation
            int espaceTotalRestant = 0;
            for (int espace : espaceRestantActuel) {
                espaceTotalRestant += espace;
            }

            // Mettre à jour le meilleur placement trouvé si cette permutation utilise moins d'espace
            if (espaceTotalRestant < calculerEspaceRestant(meilleurEspaceRestant)) {
                for (int i = 0; i < objets.size(); i++) {
                    meilleurPlacement[i] = objets.get(i).getId_bac();
                }
                copierTableau(espaceRestantActuel, meilleurEspaceRestant);
            }
            return;
        }

        // Essayer de placer l'objet index dans chaque bac disponible
        for (int i = 0; i < bacs.size(); i++) {
            if (objets.get(index).getWidth() <= espaceRestantActuel[i]) {
                // Placer l'objet index dans le bac i
                espaceRestantActuel[i] -= objets.get(index).getWidth();
                objets.get(index).setId_bac(i);

                // Appeler récursivement pour placer les objets suivants
                bruteForceRecursive(objets, bacs, index + 1, espaceRestantActuel, meilleurPlacement, meilleurEspaceRestant);

                // Retourner à l'état précédent pour essayer une autre possibilité
                espaceRestantActuel[i] += objets.get(index).getWidth();
                objets.get(index).setId_bac(-1);
            }
        }
    }

    // Fonction utilitaire pour copier un tableau
    private static void copierTableau(int[] source, int[] destination) {
        for (int i = 0; i < source.length; i++) {
            destination[i] = source[i];
        }
    }

    // Fonction utilitaire pour calculer l'espace restant total dans un tableau d'espace restant
    private static int calculerEspaceRestant(int[] espaceRestant) {
        int total = 0;
        for (int espace : espaceRestant) {
            total += espace;
        }
        return total;
    }

    private static List<Bac> createBins(int[] partition, List<Objet1D> items) {
        List<Bac> bacs = new ArrayList<>();
        for (int i = 0; i < partition.length; i++) {
            while (partition[i] >= bacs.size()) {
                bacs.add(new Bac(bacs.size() + 1));
            }
            Bac currentBac = bacs.get(partition[i]);
            Objet1D item = items.get(i);
            if (item.getWidth() <= currentBac.getSpaceLeft()) {
                currentBac.addObjetFF(item);
            } else {
                // Manage case where item does not fit (this should not happen in a valid
                // partition)
                throw new IllegalStateException("Partition leads to invalid bin packing.");
            }
        }
        return bacs;
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
                return Algorithme.bruteForce(objetsList, null);
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
    
