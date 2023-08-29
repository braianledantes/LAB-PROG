const input = document.getElementById('inputNombreIngrediente');
const button = document.getElementById('btnBuscar');

buscarIngrediente(input.value);

button.addEventListener('click', () => {
    console.log('click en el boton' + input.value);
    buscarIngrediente(input.value)
})

function buscarIngrediente(query) {
    removeIngredientesItems();

    // TODO cambiar URL a la nueva API (ver que no devuelva un array de ingredientes)
    const url = 'https://www.thecocktaildb.com/api/json/v1/1/search.php?i='

    fetch(`${url}${query}`)
        .then(response => response.json())
        .then(json => initializeIngredientes(json.ingredients))

    function initializeIngredientes(ingredientes) {
        console.log(ingredientes);
        ingredientes.forEach(ingrediente => {
            renderIngredienteItem(ingrediente)
        });
    }

    function removeIngredientesItems() {
        const listaIngredientes = document.getElementById("lista-ingredientes");
        while (listaIngredientes.firstChild) {
            listaIngredientes.removeChild(listaIngredientes.firstChild);
        }
    }

    function renderIngredienteItem(ingrediente) {
        const listaTragos = document.getElementById("lista-ingredientes");

        const article = document.createElement("article");
        const img = document.createElement("img");
        const p = document.createElement("p");

        article.setAttribute('class', 'ingrediente');
        img.setAttribute('class', 'ingrediente__img');
        // TODO buscar la imagen en la nueva API
        img.src = `https://www.thecocktaildb.com/images/ingredients/${ingrediente.strIngredient}.png`
        img.alt = `Ingrediente ${ingrediente.strIngredient}`;
        p.setAttribute('class', 'ingrediente__descripcion');
        p.textContent = ingrediente.strIngredient;

        article.appendChild(img);
        article.appendChild(img);
        article.appendChild(p);

        listaTragos.appendChild(article);
    }
}

