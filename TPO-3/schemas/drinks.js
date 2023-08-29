import z from 'zod';

const drinkSchema = z.object({
    strDrink: z.string(),
    strInstructions: z.string(),
    strDrinkThumb: z.string().url()
})

export function validateDrink(input) {
    return drinkSchema.safeParse(input);
}

export function validatePartialDrink(input) {
    return drinkSchema.partial().safeParse(input);
}