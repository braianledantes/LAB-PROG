import z from 'zod';

const drinkSchema = z.object({
    name: z.string(),
    instructions: z.string(),
    imageUrl: z.string().url(),
    ingredients: z.array(z.string())
})

export function validateDrink(input) {
    return drinkSchema.safeParse(input);
}

export function validatePartialDrink(input) {
    return drinkSchema.partial().safeParse(input);
}

const drinkSearchSchema = z.object({
    search: z.string(),
    page: z.number().positive(),
    pageSize: z.number().positive().max(32)
})

export function validateDrinkSearch(input) {
    return drinkSearchSchema.safeParse(input);
}