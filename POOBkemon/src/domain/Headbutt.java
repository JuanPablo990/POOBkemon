package domain;
 

/**
 * Movimiento Headbutt: Ataque normal que puede aturdir al rival.
 */
public class Headbutt extends Movimiento {
    public Headbutt() {
        super("Headbutt", "Normal", 70, 100, 15, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // 30% de probabilidad de aturdir
        if (Math.random() < 0.3) {
            System.out.println(objetivo.getNombre() + " se ha aturdido!");
            // En una implementación completa, aquí se aplicaría el estado de aturdimiento
        }
    }
}
