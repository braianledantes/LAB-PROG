fetch('http://localhost:8080/api/drinks/concurrents')
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
        img.src = trago.imageUrl;
        img.alt = `Trago ${trago.name}`;
        p.setAttribute('class', 'trago__descripcion');
        p.textContent = trago.name;
        
        article.appendChild(img);
        article.appendChild(img);
        article.appendChild(p);
        
        const anchor = document.createElement("a")
        anchor.setAttribute('href', `./pages/trago.html?id=${trago.id}`)
        anchor.setAttribute('target', `_black`)
        anchor.setAttribute('rel', `noopener noreferrer`)
        anchor.appendChild(article);

        listaTragos.appendChild(anchor);
}