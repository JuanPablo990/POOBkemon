package domain;
/**
 * Movimiento Volador: Roost
 * Cura 50% de la vida máxima
 */
public class Roost extends Movimiento {
    public Roost() {
        super("Roost", "Volador", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        int curacion = usuario.getPsMaximos() / 2;
        usuario.aumentarPS(curacion);
        System.out.println("¡" + usuario.getNombre() + " descansó y recuperó salud!");
    }
}