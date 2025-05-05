package domain;


/**
 * Movimiento Solar Beam: Potente ataque que requiere un turno de carga.
 */
public class SolarBeam extends Movimiento {
    private boolean cargando = false;

    public SolarBeam() {
        super("Solar Beam", "Planta", 120, 100, 10, 0);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }

        if (!cargando) {
            // Primer turno: carga
            System.out.println(usuario.getNombre() + " está absorbiendo luz solar!");
            this.pp--;
            cargando = true;
            return true;
        } else {
            // Segundo turno: ataque
            this.pp--;
            boolean exito = super.ejecutar(usuario, objetivo, efectividad);
            cargando = false;
            return exito;
        }
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Este movimiento no tiene efecto secundario adicional
    }
}
