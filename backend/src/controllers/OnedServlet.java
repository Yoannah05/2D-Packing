package controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Bac;
import models.Objet1D;
import models.Algorithme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/OnedServlet")
public class OnedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static List<Objet1D> objet1ds = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String algo = request.getParameter("algo");
        List<Bac> bacs = Algorithme.pack1D(objet1ds, algo);

        // Convert list of Bacs to JSON using json-simple
        JSONArray jsonArray = new JSONArray();
        for (Bac bac : bacs) {
            JSONObject jsonBac = new JSONObject();
            jsonBac.put("id_bac", bac.getId_bac());
            JSONArray objetsArray = new JSONArray();
            for (Objet1D objet : bac.getObjets()) {
                JSONObject jsonObjet = new JSONObject();
                jsonObjet.put("id_objet", objet.getId_obj());
                jsonObjet.put("largeur", objet.getWidth());
                objetsArray.add(jsonObjet);
            }

            jsonBac.put("objets", objetsArray);
            jsonArray.add(jsonBac);
        }

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Send JSON response back to client
        response.getWriter().write(jsonArray.toJSONString());
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/resultat.html");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Create objects based on POST parameters
        int nbrObject = Integer.parseInt(request.getParameter("nbrObjet"));
        for (int i = 0; i < nbrObject; i++) {
            Objet1D.add(objet1ds, i + 1, request.getParameter("nbr") + (i + 1));
        }

        // Forward to the algorithm selection page (assuming choixAlgo.html)
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/choixAlgo.html");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller For 1D Packing ";
    }
}
