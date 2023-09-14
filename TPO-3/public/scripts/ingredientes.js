const inputSearch = document.getElementById('inputNombreIngrediente');
const btnSearch = document.getElementById('btnBuscar');
const currentPage = document.getElementById('currentPage');
const btnNext = document.getElementById('btnNext');
const btnPrev = document.getElementById('btnPrev');

let page = 1;
let pageSize = 12;
let maxPages = Number.MAX_VALUE;

searchIngredient(inputSearch.value);

btnSearch.addEventListener('click', () => {
    page = 1;
    searchIngredient(inputSearch.value)
})

btnNext.addEventListener('click', () => {
    if (page < maxPages) {
        page++;
        searchIngredient(inputSearch.value);
    }
})

btnPrev.addEventListener('click', () => {
    if (page > 1) {
        page--;
        searchIngredient(inputSearch.value);
    }
})

function searchIngredient(query) {
    removeIngredientesItems();

    fetch(`/api/ingredients?search=${query}&page=${page}&pageSize=${pageSize}`)
        .then(response => response.json())
        .then(json => initializeIngredientes(json))

    function initializeIngredientes(response) {
        response.ingredients.forEach(ingrediente => {
            renderIngredienteItem(ingrediente)
        });

        page = response.maxPages == 0 ? 0 : response.page;
        maxPages = response.maxPages;

        currentPage.innerHTML = `${page} / ${maxPages}`;
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

