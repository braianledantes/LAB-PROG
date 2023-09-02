const params = {};
getParams();
const idDrink = params['id'];

const drickElement = document.createElement('article',)

function getParams() {
    var paramstr = window.location.search.substr(1);
    var paramarr = paramstr.split("&");

    for (var i = 0; i < paramarr.length; i++) {
        var tmparr = paramarr[i].split("=");
        params[tmparr[0]] = tmparr[1];
    }
}

fetch(`http://localhost:8080/api/drinks/${idDrink}`)
    .then(res => res.json())
    .then(drink => renderDrink(drink))

async function renderDrink(drink) {
    const trago_detalles = document.querySelector(".trago_detalles");


    const article = document.createElement("article");
    article.setAttribute('class', 'drink');

    const img = document.createElement("img");
    img.setAttribute('class', 'drink__img');
    img.src = drink.imageUrl;
    img.alt = `Trago ${drink.name}`;

    const h2 = document.createElement("h2");
    h2.setAttribute('class', 'drink__name');
    h2.textContent = drink.name;

    const p = document.createElement("p");
    p.setAttribute('class', 'drink__instructions');
    p.textContent = drink.instructions;

    const divIngredientes = document.createElement("div");
    divIngredientes.setAttribute('class', 'ingredients');
    
    const h3 = document.createElement("h3");
    h3.textContent = "Ingredientes";

   

    drink.ingredients.forEach(async ingredientName => {
        try {
            console.log(`http://localhost:8080/api/ingredients/${ingredientName.replace(" ", "%20")}`);
            const res = await fetch(`http://localhost:8080/api/ingredients/${ingredientName.replace(" ", "%20")}`)
            const ingredient = await res.json()
            if (ingredient) {
                const articleIngredient = document.createElement("article");
                articleIngredient.setAttribute('class', 'ingrediente');
        
                const imgIng = document.createElement("img");
                imgIng.setAttribute('class', 'ingrediente__img');
                imgIng.src = ingredient.srcImage;
                imgIng.alt = `Trago ${ingredient.name}`;
        
                const pIng = document.createElement("p");
                pIng.setAttribute('class', 'ingrediente__descripcion');
                pIng.textContent = ingredientName;
        
                articleIngredient.appendChild(imgIng);
                articleIngredient.appendChild(pIng);
        
                divIngredientes.appendChild(articleIngredient)
            }
        } catch (e) {

        }

        
    });

    article.appendChild(h2);
    article.appendChild(img);
    article.appendChild(p);

    trago_detalles.appendChild(article);
    trago_detalles.appendChild(h3)
    trago_detalles.appendChild(divIngredientes);



}