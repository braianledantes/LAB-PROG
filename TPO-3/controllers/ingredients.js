import { IngredientModel } from "../models/ingredients.js";
import { validateingredient, validatePartialingredient, validateingredientSearch } from "../schemas/ingredient.js";

export class IngredientController {
    static async getAll(req, res) {
        const { search = "", page = 1, pageSize = 12 } = req.query;
        const result = validateingredientSearch({
            search,
            page: Number.parseInt(page),
            pageSize: Number.parseInt(pageSize)
        })

        if (!result.success) {
            return res.status(400).json({ message: JSON.parse(result.error.message) });
        }

        const ingredients = await IngredientModel.getAll(result.data);
        res.json(ingredients);
    }
}