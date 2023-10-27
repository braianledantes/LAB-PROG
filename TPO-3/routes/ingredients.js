import { Router } from "express";
import { IngredientController } from "../controllers/ingredients.js";

export const ingredientsRouter = Router();

ingredientsRouter.get('/', IngredientController.getAll);
// Ruta de prueba
ingredientsRouter.get('/random', IngredientController.getRandom);
ingredientsRouter.get('/:name', IngredientController.getByName);