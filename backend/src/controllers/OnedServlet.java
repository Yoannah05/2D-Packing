package controllers;
import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.annotation.WebServlet;
import models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yoannah
 */
@WebServlet("/OnedServlet")
public class OnedServlet extends HttpServlet {
    static List<Objet1D> objet1ds = new ArrayList<>();
    static int bacWidth = 0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  changements d'algorithme
        Algorithme.setWidthBac(bacWidth);
        List<Bac> bacs = Algorithme.pack1D(objet1ds, request.getParameter("algo"));

        //  redirection à la page du résultat
        request.setAttribute("listeBacs", bacs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("resultat.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  creation des objets
        int nbrObject = Integer.parseInt(request.getParameter("nbrObjet"));
        bacWidth = Integer.parseInt(request.getParameter("width"));
        for (int i = 1; i <= nbrObject; i++) {
            Objet1D.add(objet1ds, i, request.getParameter("nbr" + i));
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("choixAlgo.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller For 1D Packing ";
    }// </editor-fold>

}
