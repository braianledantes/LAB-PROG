const inputSearch = document.getElementById('inputNombreTrago');
const btnSearch = document.getElementById('btnBuscar');
const currentPage = document.getElementById('currentPage');
const btnNext = document.getElementById('btnNext');
const btnPrev = document.getElementById('btnPrev');

let page = 1;
let pageSize = 16;
let maxPages = Number.MAX_VALUE;

buscarTrago(inputSearch.value);

btnSearch.addEventListener('click', () => {
    page = 1;
    buscarTrago(inputSearch.value)
})

btnNext.addEventListener('click', () => {
    if (page < maxPages) {
        page++;
        buscarTrago(inputSearch.value);
    }
})

btnPrev.addEventListener('click', () => {
    if (page > 1) {
        page--;
        buscarTrago(inputSearch.value);
    }
})

function buscarTrago(query) {
    removeTragosItems();

    fetch(`http://localhost:8080/api/drinks?search=${query}&page=${page}&pageSize=${pageSize}`)
        .then(response => response.json())
        .then(json => initializeTragos(json))

    function initializeTragos(response) {
        response.drinks.forEach(drink => {
            renderTragoItem(drink)
        });
        page = response.maxPages == 0 ? 0 : response.page;
        maxPages = response.maxPages;

        currentPage.innerHTML = `${page} / ${maxPages}`;
    }

    function removeTragosItems() {
        const listaTragos = document.getElementById("lista-tragos");
        while (listaTragos.firstChild) {
            listaTragos.removeChild(listaTragos.firstChild);
        }
    }

    function renderTragoItem(trago) {
        const listaTragos = document.getElementById("lista-tragos");

        const article = document.createElement("article");
        const img = document.createElement("img");
        const p = document.createElement("p");
        
        article.setAttribute('class', 'trago');
        img.setAttribute('class', 'trago__img');
        img.src = trago.imageUrl;
        img.alt = `Trago ${trago.name}`;
        p.setAttribute('class', 'trago__descripcion');
        p.textContent = trago.name;
        
        article.appendChild(img);
        article.appendChild(img);
        article.appendChild(p);
        
        const anchor = document.createElement("a")
        anchor.setAttribute('href', `./trago.html?id=${trago.id}`)
        anchor.setAttribute('target', `_black`)
        anchor.setAttribute('rel', `noopener noreferrer`)
        anchor.appendChild(article);

        listaTragos.appendChild(anchor);
    }
}

