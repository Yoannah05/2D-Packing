package controllers;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Bac;
import models.Objet1D;
import models.Algorithme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static List<Objet1D> objet1ds = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String algo = request.getParameter("algo");
        List<Bac> bacs = Algorithme.pack1D(objet1ds, algo);

        // Initialize Gson
        Gson gson = new Gson();

        // Convert list of Bacs to Map
        Map<String, String> bacsMap = new HashMap<>();
        for (Bac bac : bacs) {
            String jsonBac = gson.toJson(bac); // Serialize Bac object to JSON string
            bacsMap.put(String.valueOf(bac.getId_bac()), jsonBac); // Use id_bac as key
        }

        // Convert Map to JSON using Gson
        String jsonResponse = gson.toJson(bacsMap);

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Send JSON response back to client
        response.getWriter().write(jsonResponse);
        //RequestDispatcher dispatcher = request.getRequestDispatcher("pages/resultat.html");
        //dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Create objects based on POST parameters
        // int nbrObject = Integer.parseInt(request.getParameter("nbrObjet"));
        response.getWriter().write(request.getParameter("nbrObjet"));
        response.getWriter().write(request.getParameter("nbr") + (0 + 1));
        // for (int i = 0; i < nbrObject; i++) {
        //     Objet1D.add(objet1ds, i + 1, request.getParameter("nbr") + (i + 1));
        // }

        // Forward to the algorithm selection page (assuming choixAlgo.html)
        // RequestDispatcher dispatcher = request.getRequestDispatcher("pages/choixAlgo.html");
        // dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller For 1D Packing ";
    }
}