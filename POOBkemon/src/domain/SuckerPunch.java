package domain;
/**
 * Movimiento Sucker Punch - Tipo Siniestro
 */
public class SuckerPunch extends Movimiento {
    public SuckerPunch() {
        super("Sucker Punch", "Siniestro", 70, 100, 5, 1); // Prioridad 1
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto de fallar si el rival no ataca se manejaría en la lógica de batalla
        // No podemos implementarlo aquí porque no tenemos acceso al contexto del turno
    }
}