import { readJSON } from "../utils.js";
import { randomUUID } from 'node:crypto';

const ingredients = readJSON('./ingredients.json');

export class IngredientModel {
    static async getAll({ search = "" }) {
        if (search) {
            const s = search.toLowerCase();
            return ingredients.filter(
                i => i.name.toLowerCase().includes(s)
            )
        } 

        return ingredients;
    }
}