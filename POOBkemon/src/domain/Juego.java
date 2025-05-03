package domain;

public interface Juego {
    void iniciarPartida();
    void configurarPartida();
    void finalizarPartida();
    boolean esPartidaTerminada();
}