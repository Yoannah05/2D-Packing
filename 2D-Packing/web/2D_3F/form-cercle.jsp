<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>Form-2D Packing</title>
    <link rel="stylesheet" href="./css/style.css">
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
        <div class="col-5 mb-4">
            <label for="nbrObjet" class="form-label">Combien de cercle voulez-vous inserer ?</label>
            <input type="number" class="form-control" id="nbrObjet" required>
            <div class="valid-feedback"></div>
        </div>
        
        <form id="form" action="3Forme?forme=cercle" method="post" class="row g-3 needs-validation" novalidate></form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
        integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
        integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ"
        crossorigin="anonymous"></script>

        <script>
            const nbr = document.getElementById("nbrObjet");
            const form = document.getElementById("form");
            if (nbr) {
                nbr.onblur = generateForm
            }

            function generateForm(){
                let formContent = `<h1 class="h1 text-center">2D Packing, 3 formes</h1>`;

                    if (nbr.value !== "") {
                        for (let i = 1; i <= nbr.value; i++) {
                            formContent += `
                            <input type="hidden" name="nbrObjet" value="` + nbr.value + `"/>
                            <div class="col-12">
                                <label for="rayon`+i +`" class="form-label">Rayon du cercle`+i +`</label>
                                <input type="number" name="rayon`+i + `" class="form-control" id="rayon`+i + `"  required>
                                <div class="valid-feedback"></div>
                            </div>`;
                        }
                    }
                    formContent += `
                        <div class="col-12">
                            <button class="btn btn-primary" type="submit">Inserer</button>
                        </div>`
                    form.innerHTML = formContent;
            }
        </script>
</body>

</html>