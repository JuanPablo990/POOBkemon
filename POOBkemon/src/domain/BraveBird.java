package domain;

/**
 * Movimiento Volador: Brave Bird
 * Retroceso del 33% del daño
 */
public class BraveBird extends Movimiento {
    public BraveBird() {
        super("Brave Bird", "Volador", 120, 100, 15, 0);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        boolean exito = super.ejecutar(usuario, objetivo, efectividad);
        if (exito && this.potencia > 0) {
            int danio = calcularDanio(usuario, objetivo, efectividad);
            int retroceso = danio / 3; // 33% del daño
            usuario.reducirPS(retroceso);
            System.out.println("¡" + usuario.getNombre() + " sufrió retroceso por el ataque!");
        }
        return exito;
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // El efecto principal ya se maneja en ejecutar()
    }
}
