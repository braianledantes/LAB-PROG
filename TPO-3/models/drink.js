import { number } from 'zod';
import { readJSON } from '../utils.js';
import { randomUUID } from 'node:crypto';

// TODO convertir json con los datos necesarios
const drinks = readJSON('./drinks.json').map(d => {
    return {
        id: d.idDrink,
        name: d.strDrink,
        instructions: d.strInstructions,
        imageUrl: d.strDrinkThumb,
        ingredients: []
    }
});

export class DrinkModel {
    static async getAll({ search = "", page = 1, pageSize = 12 }) {
        const s = search.toLowerCase();
        const start = (page - 1) * pageSize;
        const end = start + pageSize;

        let drinksResult = drinks;

        if (search) {
            drinksResult = drinks.filter(
                drink => drink.name.toLowerCase().includes(s)
            );
        }

        const maxPages = Math.ceil(drinksResult.length / pageSize);

        if (start < drinksResult.length) {
            drinksResult = drinksResult.slice(start, end);
        } else {
            drinksResult = drinksResult.slice(-pageSize)
        }


        return {
            page,
            maxPages,
            pageSize: drinksResult.length,
            drinks: drinksResult
        };
    }

    static async getById({ id }) {
        const drink = drinks.find(drink => drink.id === id)
        return drink;
    }

    static async getConcurrents() {
        return drinks.slice(0, 12);
    }

    static async create({ input }) {
        const newDrink = {
            id: randomUUID(),
            ...input
        }

        drinks.push(newDrink);

        return newDrink;
    }

    static async delete({ id }) {
        const drinkIndex = drinks.findIndex(drink => drink.id === id);
        if (drinkIndex === -1) return false;

        drinks.splice(drinkIndex, 1);
        return true;
    }

    static async update({ id, input }) {
        const drinkIndex = drinks.findIndex(drink => drink.id === id);
        if (drinkIndex === -1) return false;

        console.log("model", "input", input);

        drinks[drinkIndex] = {
            ...drinks[drinkIndex],
            ...input
        }

        return drinks[drinkIndex];
    }
}