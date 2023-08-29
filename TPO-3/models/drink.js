import { readJSON }from '../utils.js';
import { randomUUID } from 'node:crypto';

const drinks = readJSON('./drinks.json');

export class DrinkModel {
    static async getAll({ search = "" }) {
        const s = search.toLowerCase();
        if (search) {
            return drinks.filter(
                drink => drink.strDrink.toLowerCase().includes(s)
            )
        }

        return drinks;
    }

    static async getById({ id }) {
        const drink = drinks.find(drink => drink.idDrink === id)
        return drink;
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
        const drinkIndex = drinks.findIndex(drink => drink.idDrink === id);
        if (drinkIndex === -1) return false;

        drinks.splice(drinkIndex, 1);
        return true;
    }

    static async update({ id, input }) {
        const drinkIndex = drinks.findIndex(drink => drink.idDrink === id);
        if (drinkIndex === -1) return false;

        console.log("model", "input", input);

        drinks[drinkIndex] = {
            ...drinks[drinkIndex],
            ...input
        }

        return drinks[drinkIndex];
    }
}