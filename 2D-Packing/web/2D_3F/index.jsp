<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" 
    integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>2D Packing</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <div class="col-8 mb-4 d-flex flex-row justify-content-evenly">
            <a href="./form-container.jsp">Conteneur</a>
            <a href="./form-rectangle.jsp">Inserer Rectangle</a>
            <a href="./form-cercle.jsp">Inserer Cercle</a>
            <a href="./form-triangle.jsp">Inserer Triangle</a>
            <a href="./index.jsp">Choisir algorithme</a>
        </div>
      <form method="get" action="3Forme?forme=cercle" class="row g-3 p-3 needs-validation" novalidate>
        <h1 class="h1 text-center">2D Packing</h1>
          <div class="">
            <label for="validationCustom04" class="form-label">Choix d'algorithme</label>
            <select name="algo" class="form-select" id="validationCustom04" required>
              <option value="h">Heuristique</option>
              <option value="bf">Brute force</option>
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
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js" integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous"></script>
</body>
</html>