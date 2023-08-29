/**
 * Esta utilidad sirve para leer un un archivo .json y crear el objeto.
 */
import { createRequire } from 'node:module';
const require = createRequire(import.meta.url);

export const readJSON = (path) => require(path);