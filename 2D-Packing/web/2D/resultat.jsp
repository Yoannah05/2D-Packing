<%@ page import="models.Rect" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Objet2D" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Rect rects = (Rect) request.getAttribute("rect");
    List<Objet2D> ls = (List<Objet2D>) request.getAttribute("objs");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" 
    integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>Resultat 1D Packing</title>
    <link rel="stylesheet" href="./css/resultat.css?v=2">
</head>
<body>
    <h1 class="h1 text-center m-4">Resultat 2D Packing</h1>
    <form method="get" action="TwodServlet" class="row g-3 p-3 needs-validation" novalidate>
        <h1 class="h1 text-center">2D Packing</h1>
        <div class="">
            <label for="validationCustom04" class="form-label">Choix d'algorithme</label>
            <select name="algo" class="form-select" id="validationCustom04" required>
                <option value="nfdh">NFDH</option>
                <option value="bf">Best Fit</option>
                <option value="ffdh">FFDH</option>
            </select>
            <div class="invalid-feedback"></div>
        </div>
        <div class="">
            <label for="rotation" class="form-label">Avec rotation</label>
            <select name="rotation" class="form-select" id="rotation" required>
                <option value="1">Oui</option>
                <option value="0">Non</option>
            </select>
            <div class="invalid-feedback"></div>
        </div>

        <div class="col-12">
            <button class="btn btn-primary" type="submit">Voir resultat</button>
        </div>
    </form>
    <div class="container">
        <a href="formulaire.jsp">Inserer données</a>
        <div class="rectangle" style="width: <%= Rect.getWidth() %>px; height: <%= Rect.getHeight() %>px;">
            <% for (Integer integer : rects.getLignes().keySet()) { %>
                <div class="ligne" style="width: 100%;">
                    <% for (Objet2D o : rects.getLignes().get(integer)) { %>
                        <div class="objet text-center"
                             style="background-color:<%= o.getColor() %>; width: <%= o.getWidth() %>px; height: <%= o.getHeight() %>px;">
                            <%= o.getId_obj() %>
                        </div>
                    <% } %>
                </div>
            <% } %>
        </div>        
    </div>

    <div class="info">
        <h2>Rect details</h2>
        <p>Width :  <%= Rect.getWidth() %></p>
        <p>Height :  <%= Rect.getHeight() %></p>
        <h2>Objects details</h2>
        <ul>
            <% for (Integer integer : rects.getLignes().keySet()) {
                out.print("ligne :" + integer);
                for (Objet2D o : rects.getLignes().get(integer)) { %>
            <li>obj <%= o.getId_obj() %> - Width : <%= o.getWidth() %> - Height : <%= o.getHeight() %> - Rotation : <%= o.getRotation() %></li>
            <% } } %>
        </ul>
        <ul>
            <% for (Objet2D o : ls) { %>
            <li>obj <%= o.getId_obj() %> - Width : <%= o.getWidth() %> - Height : <%= o.getHeight() %></li>
            <% } %>
        </ul>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js" integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous"></script>
</body>
</html>