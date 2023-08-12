package caballero;

import utiles.Aleatorio;

public class ArmaduraDecorator extends CaballeroDecorator {


    public ArmaduraDecorator(Caballero caballero) {
        super(caballero);
    }

    @Override
    public void recibirDanio(int danio) {
        super.recibirDanio(reduccionDanio(danio));
    }

    /**
     * Un caballero con armadura puede reducir desde 1/4 a la mitad del daño total.
     *
     * @param danio daño a causar.
     * @return el daño que va a causar.
     */
    public int reduccionDanio(int danio) {
        return Aleatorio.intAleatorio(danio / 4, danio / 2);
    }


}
