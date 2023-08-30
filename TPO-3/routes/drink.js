import { Router } from 'express';
import { DrinkController } from '../controllers/drinks.js';

export const drinksRouter = Router();

drinksRouter.get('/', DrinkController.getAll);
drinksRouter.get('/concurrents', DrinkController.getConcurrents);
drinksRouter.post('/', DrinkController.create);

drinksRouter.get('/:id', DrinkController.getById);
drinksRouter.delete('/:id', DrinkController.delete);
drinksRouter.patch('/:id', DrinkController.update);