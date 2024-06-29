document.addEventListener("DOMContentLoaded", function() {
    // Select the button and form
    var voirResultatBtn = document.getElementById('voirResultatBtn');
    var packingForm = document.getElementById('packingForm');

    // Add click event listener to the button
    voirResultatBtn.addEventListener('click', function() {
        // Serialize form data
        var formData = new FormData(packingForm);

        // Create a new XMLHttpRequest object
        var xhr = new XMLHttpRequest();

        // Configure the AJAX request
        xhr.open('GET', '/OnedServlet?' + new URLSearchParams(formData).toString(), true);
        // Define what happens on successful data submission
        xhr.onload = function() {
            if (xhr.status === 200) {
                // Parse the JSON response
                var bacs = JSON.parse(xhr.responseText);

                // Update the DOM with the retrieved data
                updateDOMWithBacs(bacs);
            }
        };
        // Send the request
        xhr.send();
        // Prevent default form submission
        return false;
    });

    // Function to update the DOM with retrieved bacs data
    function updateDOMWithBacs(bacs) {
        // Assuming bacs is an array of objects where each object represents a bac
        for (var i = 0; i < bacs.length; i++) {
            var bacId = 'bac' + (i + 1);
            var bacElement = document.getElementById(bacId);

            // Clear previous content
            bacElement.innerHTML = '';

            // Assuming each bac object has a list of objets
            var objets = bacs[i].objets;
            for (var j = 0; j < objets.length; j++) {
                var objetElement = document.createElement('div');
                objetElement.classList.add('objet');
                // Modify content of objetElement as needed

                bacElement.appendChild(objetElement);
            }
        }
    }
});
