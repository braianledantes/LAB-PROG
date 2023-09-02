import { Router } from "express";
import { IngredientController } from "../controllers/ingredients.js";

export const ingredientsRouter = Router();

ingredientsRouter.get('/', IngredientController.getAll);

ingredientsRouter.get('/:name', IngredientController.getByName);