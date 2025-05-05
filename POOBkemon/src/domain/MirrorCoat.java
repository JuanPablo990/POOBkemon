package domain;
/**
 * Movimiento Psíquico: Mirror Coat
 * Efecto secundario: Devuelve 2x daño especial recibido
 */
public class MirrorCoat extends Movimiento {
    public MirrorCoat() {
        super("Mirror Coat", "Psíquico", 0, 100, 20, -5); // Prioridad -5
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Este movimiento requiere lógica especial para contrarrestar el daño especial recibido
        System.out.println("¡" + usuario.getNombre() + " se prepara para reflejar el daño especial!");
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        // Este movimiento normalmente no se usa directamente, sino en respuesta a un ataque especial
        System.out.println("¡Mirror Coat debe usarse como respuesta a un ataque especial!");
        return false;
    }
}