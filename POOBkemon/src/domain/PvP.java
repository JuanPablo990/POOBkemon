package domain;

public abstract class PvP implements Juego {
    protected Entrenador jugador1;
    protected Entrenador jugador2;
    protected Batalla batalla;
    
    public PvP(Entrenador jugador1, Entrenador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }
    
    @Override
    public abstract void configurarPartida();
    
    @Override
    public void iniciarPartida() {
        batalla = new Batalla(jugador1, jugador2);
        batalla.iniciarBatalla();
    }
    
    @Override
    public boolean esPartidaTerminada() {
        return batalla != null && batalla.batallaTerminada();
    }
    
    @Override
    public void finalizarPartida() {
        if (batalla != null) {
            System.out.println("Partida finalizada. Resultados:");
            System.out.println(jugador1.getNombre() + ": " + 
                (jugador1.tienePokemonDisponibles() ? "GANADOR" : "PERDEDOR"));
            System.out.println(jugador2.getNombre() + ": " + 
                (jugador2.tienePokemonDisponibles() ? "GANADOR" : "PERDEDOR"));
        }
    }
    
    protected void seleccionarPokemonInicial() {
        // Lógica común para selección inicial
    }
}