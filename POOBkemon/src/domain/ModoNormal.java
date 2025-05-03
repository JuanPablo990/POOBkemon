package domain;

public class ModoNormal extends PvP {
    
    public ModoNormal(Entrenador jugador1, Entrenador jugador2) {
        super(jugador1, jugador2);
    }
    
    @Override
    public void configurarPartida() {
        seleccionarPokemonManual();
        seleccionarMovimientos();
        seleccionarItems();
    }
    
    private void seleccionarPokemonManual() {
        // Implementar selección manual
    }
    
    private void seleccionarMovimientos() {
        // Implementar selección de movimientos
    }
    
    private void seleccionarItems() {
        // Implementar selección de items
    }
    
    // No es necesario sobreescribir finalizarPartida() porque ya está implementado en PvP
}