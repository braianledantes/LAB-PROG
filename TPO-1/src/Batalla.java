import caballero.Caballero;

public class Batalla {
    private Turno turno;

    public Batalla(Turno turno) {
        this.turno = turno;
    }

    public synchronized void resolverBatalla(int turnoJugador, Caballero caballeroAtacante, Caballero caballeroAtacado) {
        try {
            while (!turno.esTurno(turnoJugador) ) {
                wait();
            }
            caballeroAtacante.atacar(caballeroAtacado);
            System.out.println(Thread.currentThread().getName() +
                    " atacando, turno: " + turno.getTurnoActual() +
                    ", turnoJugador: " + turnoJugador +
                    ", Jugador atacado: " + caballeroAtacado);
            //System.out.println(caballeroAtacante.toString() + " VS " + caballeroAtacado.toString());

            System.out.println("------------------------------------------------");

            turno.siguienteTurno();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }

}
