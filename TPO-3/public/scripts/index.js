fetch('http://localhost:8080/api/drinks')
    .then(response => response.json())
    .then(json => initializeTragos(json))

function initializeTragos(tragos) {
    tragos.forEach(trago => {
        renderTragoItem(trago)
    });
}

function renderTragoItem(trago) {
    const listaTragos = document.getElementById("lista-tragos");

    const article = document.createElement("article");
    const img = document.createElement("img");
    const p = document.createElement("p");

    article.setAttribute('class', 'trago');
    img.setAttribute('class', 'trago__img');
    img.src = trago.strDrinkThumb;
    img.alt = `Trago ${trago.strDrink}`;
    p.setAttribute('class', 'trago__descripcion');
    p.textContent = trago.strDrink;

    article.appendChild(img);
    article.appendChild(img);
    article.appendChild(p);

    listaTragos.appendChild(article);
}