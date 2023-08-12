package caballero;

import utiles.Aleatorio;

public class EspadaDecorator extends CaballeroDecorator {
    
     public EspadaDecorator(Caballero caballero) {
        super(caballero);
    }

    @Override
    public void atacar(Caballero caballeroAtacado) {
        ataqueAumentado(caballeroAtacado);
    }

    /**
     * Aumenta la posibilidad de daño entre su ataque más 5 y el doble del ataque.
     * @param caballeroAtacado el caballero que será atacado
     */
    public void ataqueAumentado(Caballero caballeroAtacado) {
        int ataque = getCaballero().getAtaqueBase();
        int danio = Aleatorio.intAleatorio(ataque + 5,ataque * 2);
        caballeroAtacado.recibirDanio(danio);
    }

}
