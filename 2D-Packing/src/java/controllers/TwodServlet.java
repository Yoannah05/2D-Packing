
package controllers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.*;

/**
 *
 * @author yoannah
 */

@WebServlet("/2D/TwodServlet")
public class TwodServlet extends HttpServlet {
    int width = 0;
    int height = 0;
    List<Objet2D> rectangles = null;
    // static List<Objet1D> objet1ds;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  changements d'algorithme
        // Reset rotations for all objects
        Objet2D.resetRotations(rectangles);
        Rect rect = null;
        boolean avecRotation = request.getParameter("rotation").equals("1");
        rect = Algorithme.pack2D(rectangles, request.getParameter("algo"), avecRotation);

        //  redirection à la page du résultat
        request.setAttribute("rect", rect);
        request.setAttribute("objs", rectangles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("resultat.jsp");
        dispatcher.forward(request, response);
    }

    @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
        //  creation des objets
        int nbrObject = Integer.parseInt(request.getParameter("nbrObjet"));
        this.width = Integer.parseInt(request.getParameter("cw"));
        this.height = Integer.parseInt(request.getParameter("ch"));
        Rect.setWidth(width);
        Rect.setHeight(height);
        this.rectangles = null;
        if (this.rectangles == null){
            this.rectangles = new ArrayList<>();
            for (int i = 1; i <= nbrObject; i++) {
                int w = Integer.parseInt(request.getParameter("width" + i));
                int h = Integer.parseInt(request.getParameter("height" + i));
                rectangles.add(new Objet2D(i, w, h));
            }
        }
        response.sendRedirect("index.jsp");
     }

     @Override
     public String getServletInfo() {
         return "Controller For 2D Packing";
     }
}
