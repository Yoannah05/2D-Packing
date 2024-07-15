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
    public static Rect FFDH(List<Objet2D> objetsList) {
        // Sort the objects in decreasing order of height
        Objet2D.sort(objetsList);
        Rect rect = new Rect(1);
        for (Objet2D objet : objetsList) {
            if (!rect.addObjetFF(objet)) {
                break;
            }
        }
        return rect;
    }
    public static Rect BFDH(List<Objet2D> objetsList) {
        // Sort the objects in decreasing order of width
        Objet2D.sort(objetsList);
        Rect rect = new Rect(1);
        for (Objet2D objet : objetsList) {
            if (!rect.addObjetBFDH(objet)) { // Assuming addObjetBFDH is implemented to handle BFDH logic
                break;
            }
        }
        return rect;
    }
    public static Rect NFDH(List<Objet2D> objetsList) {
        // Sort the objects in decreasing order of height
        Objet2D.sort(objetsList);
        Rect rect = new Rect(1);
        for (Objet2D objet : objetsList) {
            if (!rect.addObjetFF(objet)) {
                break;
            }
        }
        return rect;
    }

    public static Rect pack2D(List<Objet2D> objetsList, String algorithme) {
        switch (algorithme) {
            case "ffdh":
                return Algorithme.FFDH(objetsList);
            default:
                throw new IllegalArgumentException("Algorithme non supporté : " + algorithme);
        }
    }


    public static void FFDH(List<Objet2D> objets2D, List<Bac> bacsDisponibles) {
        // Trier les objets2D par une propriété pertinente (comme le rayon pour les cercles)
        objets2D.sort((a, b) -> {
            if (a instanceof Cercle && b instanceof Cercle) {
                return Double.compare(((Cercle) b).getRayon(), ((Cercle) a).getRayon());
            } else {
                return Integer.compare(b.getHeight(), a.getHeight());
            }
        });
        
        for (Objet2D objet : objets2D) {
            boolean placed = false;
            for (Bac bac : bacsDisponibles) {
                if (objet.fitsIn(bac)) {
                    objet.placeIn(bac);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                // Créer un nouveau bac si aucune place n'a été trouvée
                Bac newBac = new Bac(getNextBacId(bacsDisponibles));  // Utilisez une méthode pour obtenir le prochain ID de bac
                objet.placeIn(newBac);
                bacsDisponibles.add(newBac);  // Ajouter le nouveau bac à la liste des bacs disponibles
            }
        }
    }

    // Méthode pour obtenir le prochain ID de bac
    private static int getNextBacId(List<Bac> bacs) {
        int maxId = 0;
        for (Bac bac : bacs) {
            if (bac.getId_bac() > maxId) {
                maxId = bac.getId_bac();
            }
        }
        return maxId + 1;  // Retourne l'ID suivant après le plus grand ID actuellement utilisé
    }
    

}
