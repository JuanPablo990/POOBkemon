package domain;


/**
 * Movimiento Spore: Duerme al Pokémon objetivo con 100% de precisión.
 */
public class Spore extends Movimiento {
    public Spore() {
        super("Spore", "Planta", 0, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Dormir al objetivo
        System.out.println(objetivo.getNombre() + " se ha dormido!");
        // En una implementación completa, aquí se aplicaría el estado de sueño
    }
}
