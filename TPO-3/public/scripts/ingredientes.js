const input = document.getElementById('inputNombreIngrediente');
const button = document.getElementById('btnBuscar');

buscarbuscarTrago(input.value);

button.addEventListener('click', () => {
    console.log('click en el boton' + input.value);
    buscarbuscarTrago(input.value)
})

function buscarbuscarTrago(query) {
    removeIngredientesItems();

    // TODO cambiar URL a la nueva API (ver que no devuelva un array de ingredientes)
    const url = 'http://localhost:8080/api/ingredients?search='

    fetch(`${url}${query}`)
        .then(response => response.json())
        .then(json => initializeIngredientes(json))

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
        img.src = ingrediente.srcImage;
        img.alt = `Ingrediente ${ingrediente.name}`;
        p.setAttribute('class', 'ingrediente__descripcion');
        p.textContent = ingrediente.name;

        article.appendChild(img);
        article.appendChild(img);
        article.appendChild(p);

        listaTragos.appendChild(article);
    }
}

