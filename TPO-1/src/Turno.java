public class Turno {

    private int turnoActual = 0;
    private int cantJugadores;

    public Turno(int cantJugadores) {
        this.cantJugadores = cantJugadores;
    }

    public synchronized void siguienteTurno() {
        this.turnoActual++;
    }

    public synchronized boolean esTurno(int turno) {
        return turnoActual % cantJugadores +1 == turno;
    }

    public synchronized int getTurnoActual() {
        return turnoActual +1;
    }

    public synchronized void reiniciar() {
        this.turnoActual = 0;
    }
}
