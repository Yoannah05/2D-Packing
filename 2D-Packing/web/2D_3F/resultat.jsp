<%@ page import="models.Rect" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Objet2D" %>
<%@ page import="models.Triangle" %>
<%@ page import="models.Cercle" %>
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
    <form method="get" action="3Forme" class="row g-3 p-3 needs-validation" novalidate>
        <h1 class="h1 text-center">2D Packing</h1>
        <div class="">
            <label for="validationCustom04" class="form-label">Choix d'algorithme</label>
            <select name="algo" class="form-select" id="validationCustom04" required>
                <option value="h">Heuristique</option>
                <option value="bf">Best Fit</option>
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
            <a href="form-rectangle.jsp">Inserer données</a>
            <button class="btn btn-primary" type="submit">Voir resultat</button>
        </div>
    </form>
    <div class="container">
        <%= ls.size() %>
        <div class="rectangle" style="width: <%= Rect.getWidth() %>px; height: <%= Rect.getHeight() %>px;">
            <% for (Integer integer : rects.getLignes().keySet()) { %>
            <div class="ligne" style="width: 100%;">
                <% for (Objet2D o : rects.getLignes().get(integer)) {
                    String forme = "";
                    if(o instanceof Triangle){
                        forme = "triangle";
                    } else if(o instanceof Cercle){
                        forme = "cercle";
                    } else {
                        forme = "objet";
                    }
                %>
                <div class="<%= forme %> text-center"
                     style="background-color:<%= o.getColor() %>; width: <%= o.getWidth() %>px; height: <%= o.getHeight() %>px;">
                    <%= o.getId_obj() %>
                </div>
                <% } %>
            </div>
            <% } %>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js" integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous"></script>
</body>
</html>

<%--
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Organisation de Formes</title>
</head>
<body>
<canvas id="monCanvas" width="800" height="600"></canvas>
<script>
    const canvas= document.getElementById("monCanvas");
    const ctx = canvas.getContext('2d');

    function dessinerCercle(ctx, x, y, rayon, couleur) {
        ctx.beginPath();
        ctx.arc(x, y, rayon, 0, Math.PI*2, false); // Dessine un cercle complet
        ctx.fillStyle = couleur;
        ctx.fill();
    }
    function dessinerTriangle(ctx, x1, y1, x2, y2, x3, y3, couleur) {
        ctx.beginPath();
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.lineTo(x3, y3);
        ctx.closePath();
        ctx.fillStyle = couleur;
        ctx.fill();
    }
    function dessinerRectangle(ctx, x, y, largeur, hauteur, couleur) {
        ctx.fillStyle = couleur;
        ctx.fillRect(x, y, largeur, hauteur);
    }
    let positionY = 0; // Commencer au bas du canvas

    function ajouterForme(forme, x, largeur, hauteur, couleur) {
        switch (forme) {
            case 'cercle':
                dessinerCercle(ctx, x, positionY, 50, couleur);
                break;
            case 'triangle':
                dessinerTriangle(ctx, x, positionY, x+50, positionY+50, x+100, positionY, couleur);
                break;
            case 'rectangle':
                dessinerRectangle(ctx, x, positionY, largeur, hauteur, couleur);
                break;
        }

        positionY += hauteur; // Mettre à jour la position Y après avoir dessiné la forme
    }

    // Exemple d'utilisation
    ajouterForme('rectangle', 50, 100, 50, 'red');
    ajouterForme('cercle', 200, 0, 50, 'blue');
    ajouterForme('triangle', 350, 0, 50, 'green');

</script>
</body>
</html>
--%>
