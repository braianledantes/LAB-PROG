import caballero.ArmaduraDecorator;
import caballero.Caballero;
import caballero.EscudoDecorator;
import caballero.EspadaDecorator;

/**
 * UNA CLASE que simula ser una tienda
 * TIENE RECURSOS ILIMITADOS POR LO CUAL NO VAN A SER SINCRONIZADOS
 * SE HACE CON UNA ESTRUCTURA DE CASES DONDE LAS DIFERENTES OPCIONES SON DIFERENTES OBJETOS
 * O ACCIONES QUE QUIERE CADA JUGADOR
 * LOS JUGADORES VAN A INTERACTUAR CON ESTA PARA COMPRAR LOS
 * ACCESORIOS (DECORADORES) PARA SUS CABALLEROS.
 * EN LA TIENDA TAMBIEN PODRIAN RESTAURAR O "REVIVIR" A SUS CABALLEROS MUERTOS.
 */
public class Tienda {
    private final int costoEspada;
    private final int costoArmadura;
    private final int costoEscudo;

    public Tienda() {
        this.costoEspada = 120;
        this.costoArmadura = 150;
        this.costoEscudo = 80;
    }

    public Caballero comprarEspada(int oroJugador, Jugador jugador) {
        if (costoEspada < oroJugador) {
            jugador.decrementarOro(costoEspada);
            return new EspadaDecorator(jugador.getCaballero());
        }
        else
            return jugador.getCaballero();
    }

    public Caballero comprarEscudo(int oroJugador, Jugador jugador) {
        if (costoEscudo < oroJugador) {
            jugador.decrementarOro(costoEscudo);
            return new EscudoDecorator(jugador.getCaballero());
        }
        else
            return jugador.getCaballero();
    }

    public Caballero comprarArmadura(int oroJugador, Jugador jugador) {
        if (costoArmadura < oroJugador){
            jugador.decrementarOro(costoArmadura);
            return new ArmaduraDecorator(jugador.getCaballero());
        }
        else
            return jugador.getCaballero();
    }

    public void curarCaballero(Caballero caballero) {
        caballero.curar();
    }

}
