package models;

public class Cercle extends Objet2D {
    private double rayon;
    private double diametre;

    public Cercle(int id_obj, double rayon) {
        super(id_obj, (int) (2 * rayon), (int) (2 * rayon)); // Casting double to int for width and height
        this.rayon = rayon;
        this.diametre = 2 * rayon;
        this.setWidth((int) diametre);
        this.setHeight((int) diametre);
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
        this.diametre = 2 * rayon;
        this.setWidth((int) diametre);
        this.setHeight((int) diametre);
    }

    public int getDiametre() {
        return (int)diametre;
    }

    public void setDiametre(double diametre) {
        this.diametre = diametre;
        this.rayon = diametre / 2;
        this.setWidth((int) diametre);
        this.setHeight((int) diametre);
    }

    @Override
    public int getWidth() {
        return (int) diametre;
    }

    @Override
    public int getHeight() {
        return (int) diametre;
    }
}
