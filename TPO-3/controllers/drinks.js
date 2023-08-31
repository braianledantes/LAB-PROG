import { DrinkModel } from '../models/drink.js';
import { validateDrink, validatePartialDrink, validateDrinkSearch } from '../schemas/drinks.js';

export class DrinkController {
    static async getAll(req, res) {
        const { search = "", page = 1, pageSize = 12 } = req.query;
        const result = validateDrinkSearch({
            search,
            page: Number.parseInt(page),
            pageSize: Number.parseInt(pageSize)
        })

        if (!result.success) {
            return res.status(400).json({ message: JSON.parse(result.error.message) });
        }

        const drinks = await DrinkModel.getAll(result.data);
        res.json(drinks);
    }

    static async getById(req, res) {
        const { id } = req.params;
        const drink = await DrinkModel.getById({ id });
        if (drink) return res.json(drink);

        res.status(404).json(({ message: 'Drink not fount' }));
    }

    static async getConcurrents(req, res) {
        const drinks = await DrinkModel.getConcurrents();
        res.json(drinks);
    }

    static async create(req, res) {
        const result = validateDrink(req.body);

        if (!result.success) {
            return res.status(400).json({ message: JSON.parse(result.error.message) });
        }

        const newDrink = await DrinkModel.create({ input: result.data })

        res.status(201).json(newDrink);
    }

    static async delete(req, res) {
        const { id } = req.params;

        const result = await DrinkModel.delete({ id })

        if (result === false) {
            return res.status(404).json({ message: 'Drink not found' })
        }

        return res.json({ message: `Drink ${id} deleted` })
    }

    static async update(req, res) {
        const result = validatePartialDrink(req.body);

        if (!result.success) {
            return res.status(400).json({ error: JSON.parse(result.error.message) });
        }

        const { id } = req.params;

        const updatedDrink = await DrinkModel.update({ id, input: result.data });

        return res.json(updatedDrink);
    }
}