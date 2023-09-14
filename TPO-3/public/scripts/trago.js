const params = {};
getParams();
const idDrink = params['id'];

function getParams() {
    var paramstr = window.location.search.substr(1);
    var paramarr = paramstr.split("&");

    for (var i = 0; i < paramarr.length; i++) {
        var tmparr = paramarr[i].split("=");
        params[tmparr[0]] = tmparr[1];
    }
}

const btnDelete = document.getElementById("btnDelete");
const deleteDialog = document.getElementById("deleteDialog");
const btnConfirm = document.getElementById("confirmBtn");
const btnCancel = document.getElementById("cancelBtn");
const tragoDetalles = document.querySelector(".trago_detalles");
const contentDrink = document.querySelector(".trago_content");

btnDelete.addEventListener("click", () => {
    deleteDialog.showModal();
});

deleteDialog.addEventListener("close", () => {
    if (deleteDialog.returnValue && deleteDialog.returnValue !== "default") {
        tragoDetalles.innerHTML = deleteDialog.returnValue;
    }
});

btnConfirm.addEventListener("click", (event) => {
    event.preventDefault();
    fetch(`/api/drinks/${idDrink}`, { method: "DELETE" })
        .then(res => {
            if (res.status === 200)
                deleteDialog.close("Trago eliminado exitosamente");
            else
                deleteDialog.close("El trago no pudo ser eliminado");
        })
        .catch(e => {
            console.error(e);
        });
});

function renderError(error) {
    console.log(error);
    error.json().then((body) => {
        //Here is already the payload from API
        console.error(body);
        tragoDetalles.innerHTML = "<h2>Error al traer trago con id " + idDrink + "</h2>"
        tragoDetalles.innerHTML += "<p>" + body.message + "</p>"
    });
}

function renderDrink(drink) {
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
    h3.setAttribute('class', 'ingredients__title');
    h3.textContent = "Ingredientes";

    if (Array.isArray(drink.ingredients)) {
        drink.ingredients.forEach(async ingredientName => {
            try {
                console.log(`/api/ingredients/${ingredientName.replace(" ", "%20")}`);
                const res = await fetch(`/api/ingredients/${ingredientName.replace(" ", "%20")}`)
                const ingredient = await res.json()
                if (ingredient) {
                    const articleIngredient = document.createElement("article");
                    articleIngredient.setAttribute('class', 'ingrediente');

                    const imgIng = document.createElement("img");
                    imgIng.setAttribute('class', 'ingrediente__img');
                    imgIng.src = ingredient.srcImage;
                    imgIng.alt = `${ingredient.name}`;

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
    }
    else {
        h3.textContent = "No tiene ingredientes"
    }

    article.appendChild(h2);
    article.appendChild(img);
    article.appendChild(p);

    contentDrink.appendChild(article);
    contentDrink.appendChild(h3)
    contentDrink.appendChild(divIngredientes);
}

fetch(`/api/drinks/${idDrink}`)
    .then(res => {
        if (!res.ok) throw res;
        return res.json();
    })
    .then(drink => renderDrink(drink))
    .catch(err => renderError(err))
