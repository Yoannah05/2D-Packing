package controllers;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author yoannah
 */
public class OnedServlet extends HttpServlet {
    static List<Objet1D> objet1ds;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  changements d'algorithme
        List<Bac> bacs = Algorithme.pack1D(objet1ds, request.getParameter("algo"));

        //  redirection à la page du résultat
        request.setAttribute("listeBacs", bacs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/resultat.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  creation des objets
        int nbrObject = (int) request.getParameter("nbrObjet");
        for (int i = 0; i < nbrObject; i++) {
            Objet1D.add(objet1ds, i+1, request.getParameter("nbr")+i+1);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/choixAlgo.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller For 1D Packing ";
    }// </editor-fold>

}
