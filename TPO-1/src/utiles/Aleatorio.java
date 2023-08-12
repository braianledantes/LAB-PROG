/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

/**
 *
 * @author nacho
 */
public class Aleatorio {

    public static double doubleAleatorio(int min, int max) {
        return Math.random() * (max - min) + min;
    }

    public static int intAleatorio(int min, int max) {
        return (int) doubleAleatorio(min, max);
    }

    public static char charAleatorio() {
        char a;
        if (Math.random() >= 0.5) {
            // letras mayúsculas
            a = (char) intAleatorio(97, 123);
        } else {
            // letras minúsculas
            a = (char) intAleatorio(65, 91);
        }
        return a;
    }

    public static String stringAleatorio(int tam) {
        String res = "";
        for (int i = 0; i < tam; i++) {
            res = res + charAleatorio();
        }
        return res;
    }

    public static String nombreAleatorio() {
        // ejemplo para definir un valor aleatorio entre varias posibilidades
        // en este caso nombres
        String arr[] = {"Juan", "Carlos", "Pedro", "Tito", "Cacho"};
        return arr[intAleatorio(0, arr.length - 1)];
    }

    public static char operadorAleatorio() {
        char arr[] = {'+','-','*','+','*','-','*','-','+'};
        return arr[intAleatorio(0, arr.length - 1)];

    }
}
