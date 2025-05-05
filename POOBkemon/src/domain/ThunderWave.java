package domain; 

/**
 * Movimiento Thunder Wave: Paraliza al Pokémon objetivo.
 */
public class ThunderWave extends Movimiento {
    public ThunderWave() {
        super("Thunder Wave", "Eléctrico", 0, 90, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Paralizar al objetivo (en el juego real esto sería un estado, aquí simplificado)
        System.out.println(objetivo.getNombre() + " ha sido paralizado!");
        // En una implementación completa, aquí se aplicaría el estado de parálisis
    }
}
