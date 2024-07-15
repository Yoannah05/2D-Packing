package models;

public class Cercle extends Objet2D {
    private double rayon;

    public Cercle(double rayon) {
        super((int) (2 * rayon), (int) (2 * rayon)); // Appel du constructeur de Objet2D avec les dimensions calculées
        this.rayon = rayon;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }

    public boolean fitsIn(Bac bac) {
        // Vérifier si le diamètre du cercle est inférieur ou égal à la largeur du bac
        return (2 * rayon <= bac.getWidth());
    }

    @Override
    public void placeIn(Bac bac) {
        // Créer une instance de Objet2D avec les dimensions du cercle
        Objet2D objet2D = new Objet2D((int) (2 * rayon), (int) (2 * rayon));
        
        // Ajouter l'objet au bac en utilisant la méthode addObjetFF
        if (bac.addObjetFF(objet2D)) {
            // Définir l'ID du bac pour l'objet (si nécessaire)
            // objet2D.setId_bac(bac.getId_bac()); // Décommentez cette ligne si nécessaire
        } else {
            // Gérer le cas où l'objet ne peut pas être ajouté au bac
            System.out.println("L'objet ne peut pas être ajouté au bac.");
        }
    }
}
