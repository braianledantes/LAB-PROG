package caballero;

public abstract class CaballeroDecorator implements Caballero {

    private Caballero caballero;

    public CaballeroDecorator(Caballero caballero) {
        this.caballero = caballero;
    }

    public Caballero getCaballero() {
        return caballero;
    }

    @Override
    public void atacar(Caballero caballeroAtacado) {
        this.caballero.atacar(caballeroAtacado);
    }

    @Override
    public void recibirDanio(int danio) {
        this.caballero.recibirDanio(danio);
    }

    @Override
    public int getAtaqueBase() {
        return this.caballero.getAtaqueBase();
    }

    @Override
    public void curar() {
        this.caballero.curar();
    }

    @Override
    public boolean estaVivo() {
        return this.caballero.estaVivo();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "caballero=" + caballero +
                '}';
    }
}
