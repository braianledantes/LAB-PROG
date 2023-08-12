package caballero;

import utiles.Aleatorio;

public class EscudoDecorator extends CaballeroDecorator {

    public EscudoDecorator(Caballero caballero) {
        super(caballero);
    }

    @Override
    public void recibirDanio(int danio) {
        super.recibirDanio(reduccionDanioEscudo(danio));
    }

    /**
     * Reduce a la mitad el daño recibido aleatoriamente.
     *
     * @param danio daño a causar.
     * @return el daño que va a causar.
     */
    public int reduccionDanioEscudo(int danio) {
        return Aleatorio.intAleatorio(danio / 2, danio);
    }

}
