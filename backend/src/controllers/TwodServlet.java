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
@WebServlet("/TwodServlet")
public class TwodServlet extends HttpServlet {
    static List<Objet1D> objet1ds;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  changements d'algorithme
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //  creation des objets
    }

    @Override
    public String getServletInfo() {
        return "Controller For 2D Packing";
    }// </editor-fold>

}
