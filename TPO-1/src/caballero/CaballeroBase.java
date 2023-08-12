package caballero;

public class CaballeroBase implements Caballero {
    private int vida;
    private int ataqueBase;
    private static final int VIDA_MAX = 100;

    public CaballeroBase(int ataqueBase) {
        this.vida = VIDA_MAX;
        this.ataqueBase = ataqueBase;
    }

    @Override
    public void atacar(Caballero caballeroAtacado) {
        caballeroAtacado.recibirDanio(ataqueBase);
    }

    @Override
    public void recibirDanio(int danio) {
        this.vida -= danio;
        if (this.vida < 0) this.vida = 0;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    @Override
    public void curar() {
        this.vida = VIDA_MAX;
    }

    @Override
    public boolean estaVivo() {
        return this.vida > 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "vida=" + vida +
                //", ataqueBase=" + ataqueBase +
                '}';
    }
}
