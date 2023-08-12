import caballero.Caballero;
import caballero.CaballeroBase;
import utiles.Aleatorio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Partida {
    private Jugador jugador1, jugador2;
    private Tienda tienda;
    private Turno turno;
    private Batalla batalla;

    public Partida(String nombreJugador1, String nombreJugador2) {
        tienda = new Tienda();
        turno = new Turno(2);
        batalla = new Batalla(turno);
        Caballero caballero1 = new CaballeroBase(Aleatorio.intAleatorio(10, 20));
        Caballero caballero2 = new CaballeroBase(Aleatorio.intAleatorio(15, 20));

        jugador1 = new Jugador(nombreJugador1, 1, 100, caballero1, batalla, tienda);
        jugador2 = new Jugador(nombreJugador2, 2, 100, caballero2, batalla, tienda);

        jugador1.setJugadorOponente(jugador2);
        jugador2.setJugadorOponente(jugador1);
    }

    public void inicar() {
//        Thread j1 = new Thread(jugador1);
//        Thread j2 = new Thread(jugador2);
//
//        j1.start();
//        j2.start();

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(jugador1);
        pool.submit(jugador2);

        pool.shutdown();
    }
}
