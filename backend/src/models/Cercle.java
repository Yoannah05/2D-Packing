package models;

public class Cercle extends Objet2D {
    private double rayon;

    public Cercle(int id_obj, double rayon) {
        super(id_obj, (int) (2 * rayon), (int) (2 * rayon)); // Casting double to int for width and height
        this.rayon = rayon;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }
}
