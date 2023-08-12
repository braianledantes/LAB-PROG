package caballero;

public interface Caballero {

    /**
     * El caballero realiza un ataque a otro caballero.
     *
     * @param caballeroAtacado caballero que será atacado.
     */
    void atacar(Caballero caballeroAtacado);

    /**
     * El caballero se defiende de un ataque.
     *
     * @param danio daño que podría recibir el caballero.
     */
    void recibirDanio(int danio);

    int getAtaqueBase();

    void curar();

    boolean estaVivo();
}
