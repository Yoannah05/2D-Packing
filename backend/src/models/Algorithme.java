package models;

import java.util.ArrayList;
import java.util.List;

public class Algorithme {
    static int widthBac = 0;

    public static void setWidthBac(int widthBac) {
        Algorithme.widthBac = widthBac;
    }

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
        bacs.add(new Bac(1, Algorithme.widthBac));
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

            if (objetOptimal != null) {
                objetOptimal.setId_bac(idBac);
                bac.diminueSpaceLeft(objetOptimal.getWidth());
                bac.getObjets().add(objetOptimal);
                //objetsList.remove(objetOptimal); // Retirez l'objet du liste des objets à placer
            } else {
                idBac++; // Incrémenter le numéro de bac si aucun objet ne peut être placé
                bacs.add(bac);
                bac = new Bac(idBac);
            }
        }
        bacs.add(bac); // Ajouter le dernier bac
        return bacs;
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
    
        public static List<Bac> bruteForce(List<Objet1D> objetsList) {
            int n = objetsList.size();
            int minBins = n;
            List<Bac> bestSolution = null;
    
            List<int[]> partitions = generatePartitions(n);
            for (int[] partition : partitions) {
                List<Bac> bacs = createBins(partition, objetsList);
                if (bacs.size() < minBins) {
                    minBins = bacs.size();
                    bestSolution = bacs;
                }
            }
    
            return bestSolution;
        }
    
        private static List<int[]> generatePartitions(int n) {
            List<int[]> partitions = new ArrayList<>();
            int[] partition = new int[n];
            generatePartitionsRecursive(partitions, partition, 0, 0);
            return partitions;
        }
    
        private static void generatePartitionsRecursive(List<int[]> partitions, int[] partition, int index, int maxPart) {
            if (index == partition.length) {
                partitions.add(partition.clone());
                return;
            }
            for (int i = 0; i <= maxPart; i++) {
                partition[index] = i;
                generatePartitionsRecursive(partitions, partition, index + 1, Math.max(maxPart, i + 1));
            }
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
                    // Manage case where item does not fit (this should not happen in a valid partition)
                    throw new IllegalStateException("Partition leads to invalid bin packing.");
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
                    return Algorithme.bestFit(objetsList);
                case "wf":
                    return Algorithme.worstFit(objetsList);
                case "brf":
                    return Algorithme.bruteForce(objetsList);
                default:
                    throw new IllegalArgumentException("Algorithme non supporté : " + algorithme);
            }
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

    private static void reinitializeObjetID(List<Objet1D> objetsList){
        for (Objet1D objet1D : objetsList) {
            objet1D.setId_bac(-1);
        }
    }

    public static List<Rect> NFDH(List<Objet2D> objetsList) {

        objetsList.sort((o1, o2) -> Integer.compare(o2.getHeight(), o1.getHeight()));
    
        List<Rect> rects = new ArrayList<>();
    
        for (Objet2D objet : objetsList) {
            boolean placed = false;
    

            for (Rect rect : rects) {
                if (rect.addObjetFF(objet)) {
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
            case "nfdh":
                return Algorithme.NFDH(objetsList);
            case "bf":
                return Algorithme.bestFit2D(objetsList);
            default:
                return rects;
        }
        //return rects;
    }

    private static List<Rect> bestFit2D(List<Objet2D> objetsList) {
        return null;
    }
}
