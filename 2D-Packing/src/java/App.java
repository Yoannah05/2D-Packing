import java.util.ArrayList;
import java.util.List;

import models.Algorithme;
import models.Bac;
import models.Objet1D;
import models.Objet2D;
import models.Rect;

public class App {
    public static void main(String[] args) throws Exception {
        // List<Objet1D> objetsList = new ArrayList<>();
        // Objet1D objet1 = new Objet1D(1, 100);
        // Objet1D objet2 = new Objet1D(2, 200);
        // Objet1D objet3 = new Objet1D(3, 400);
        // // Add objects to the list
        // objetsList.add(objet1);
        // objetsList.add(objet2);
        // objetsList.add(objet3);

        // List<Bac> bacs = Algorithme.bestFit(objetsList);
        // for (Bac bac : bacs) {
        //     System.out.println(bac.getObjets().size());
        //     System.out.println(bac.getSpaceLeft());
        // }

        List<Objet2D> objetsList = new ArrayList<>();
        Rect.setHeight(500);
        Rect.setWidth(500);
        objetsList.add(new Objet2D(1, 100, 150)); // id, width, height
        objetsList.add(new Objet2D(2, 200, 120));
        objetsList.add(new Objet2D(3, 150, 200));
        objetsList.add(new Objet2D(4, 250, 100));
    }
}
