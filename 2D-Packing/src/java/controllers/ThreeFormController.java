
package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yoannah
 */

@WebServlet("/2D_3F/3Forme")
public class ThreeFormController extends HttpServlet {
    int width = 0;
    int height = 0;
    List<Cercle> cercles = null;
    List<Triangle> triangles = null;
    List<Objet2D> rectangles = null;
    // static List<Objet1D> objet1ds;

    @Override
    public void init() throws ServletException {
        this.cercles = new ArrayList<>();
        this.triangles = new ArrayList<>();
        this.rectangles = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  changements d'algorithme
        // Reset rotations for all objects
        List<Objet2D> allFormes = new ArrayList<>();
        allFormes.addAll(cercles);
        allFormes.addAll(triangles);
        allFormes.addAll(rectangles);

        Objet2D.resetRotations(rectangles);
        boolean avecRotation = request.getParameter("rotation").equals("1");
        Rect rect = Algorithme.pack2D3Forme(rectangles, request.getParameter("algo"), avecRotation);

        //  redirection à la page du résultat
        request.setAttribute("rect", rect);
        request.setAttribute("objs", allFormes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("resultat.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  creation des objets
        int nbrObject = (request.getParameter("nbrObjet") != null) ? Integer.parseInt(request.getParameter("nbrObjet")) : 0;
        if (request.getParameter("forme").equalsIgnoreCase("cercle")) {
            for (int i = 1; i <= nbrObject; i++) {
                double rayon = Double.parseDouble(request.getParameter("rayon" + i));
                this.cercles.add(new Cercle(i, rayon));
            }
        } else if (request.getParameter("forme").equalsIgnoreCase("triangle")) {
            for (int i = 1; i <= nbrObject; i++) {
                int base = Integer.parseInt(request.getParameter("base" + i));
                int hauteur = Integer.parseInt(request.getParameter("height" + i));
                this.triangles.add(new Triangle(i, base, hauteur));
            }
        } else if (request.getParameter("forme").equalsIgnoreCase("rectangle")) {
            for (int i = 1; i <= nbrObject; i++) {
                int w = Integer.parseInt(request.getParameter("width" + i));
                int h = Integer.parseInt(request.getParameter("height" + i));
                rectangles.add(new Objet2D(i, w, h));
            }
        } else if (request.getParameter("forme").equalsIgnoreCase("container")) {
            this.width = Integer.parseInt(request.getParameter("width"));
            this.height = Integer.parseInt(request.getParameter("height"));
            Rect.setWidth(width);
            Rect.setHeight(height);
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Controller For 2D Packing 3formes";
    }
}
