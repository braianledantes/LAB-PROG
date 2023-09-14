import express, { json } from "express";
import bodyParser from 'body-parser';
import { corsMiddleware } from "./middlewares/cors.js";
import { drinksRouter } from "./routes/drink.js";
import { ingredientsRouter } from "./routes/ingredients.js";

const app = express();
app.use(json());
app.use(corsMiddleware())
app.disable('x-powered-by');

// para servir el sitio estático
app.use(express.static('public'));

app.use('/api/drinks', drinksRouter);
app.use('/api/ingredients', ingredientsRouter);

const PORT = process.env.PORT ?? 8080;

app.listen(PORT, () => {
    console.log(`Server listening on port http://localhost:${PORT}`);
})