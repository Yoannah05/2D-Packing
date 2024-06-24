import java.util.ArrayList;
import java.util.List;

import Models.Algorithme;
import Models.Bac;
import Models.Objet1D;

public class App {
    public static void main(String[] args) throws Exception {
        List<Objet1D> objetsList = new ArrayList<>();
        Objet1D objet1 = new Objet1D(1, 100);
        Objet1D objet2 = new Objet1D(2, 200);
        Objet1D objet3 = new Objet1D(3, 400);
        // Add objects to the list
        objetsList.add(objet1);
        objetsList.add(objet2);
        objetsList.add(objet3);

        List<Bac> bacs = Algorithme.bestFit(objetsList);
        for (Bac bac : bacs) {
            System.out.println(bac.getObjets().size());
            System.out.println(bac.getSpaceLeft());
        }
    }
}
