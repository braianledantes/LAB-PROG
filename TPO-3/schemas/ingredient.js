import z from 'zod';

const ingredientSchema = z.object({
    name: z.string(),
    imageUrl: z.string().url()
})

export function validateingredient(input) {
    return ingredientSchema.safeParse(input);
}

export function validatePartialingredient(input) {
    return ingredientSchema.partial().safeParse(input);
}

const ingredientSearchSchema = z.object({
    search: z.string(),
    page: z.number().positive(),
    pageSize: z.number().positive().max(32)
})

export function validateingredientSearch(input) {
    return ingredientSearchSchema.safeParse(input);
}