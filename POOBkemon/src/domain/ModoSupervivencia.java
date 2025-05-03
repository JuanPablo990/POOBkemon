package domain;

public class ModoSupervivencia extends PvP {
    
    public ModoSupervivencia(Entrenador jugador1, Entrenador jugador2) {
        super(jugador1, jugador2);
    }
    
    @Override
    public void configurarPartida() {
        seleccionarPokemonAleatorio();
        asignarMovimientosPredefinidos();
        nivelarPokemonAMaximo();
        
        // Vaciar mochilas de items para este modo
        jugador1.getMochilaItems().clear();
        jugador2.getMochilaItems().clear();
    }
    
    private void seleccionarPokemonAleatorio() {
        // Implementar lógica de selección aleatoria
    }
    
    private void asignarMovimientosPredefinidos() {
        // Implementar asignación de movimientos
    }
    
    private void nivelarPokemonAMaximo() {
        // Implementar subida a nivel 100
    }
    
    // No es necesario sobreescribir finalizarPartida() porque ya está implementado en PvP
}