import fs from 'node:fs'

async function traernIgredientes() {
    const response = await fetch("https://www.thecocktaildb.com/api/json/v1/1/list.php?i=list")
    const ingredients = await response.json();
    const myIngredients = ingredients.drinks.map(i => {
        const srcImage = `https://www.thecocktaildb.com/images/ingredients/${i.strIngredient1.replace(" ", "%20")}.png`
        return {
            "name": i.strIngredient1,
            srcImage
        }
    })
    
    const json = JSON.stringify(myIngredients);

    fs.writeFile("ingredients.json", json, 'utf-8', ()=>{})
}

traernIgredientes();