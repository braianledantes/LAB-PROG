import { IngredientModel } from "../models/ingredients.js";
import { validateDrink, validatePartialDrink } from "../schemas/drinks.js";

export class IngredientController {
    static async getAll(req, res) {
        const { search } = req.query;
        const ingredients = await IngredientModel.getAll({ search });
        res.json(ingredients);
    }
}