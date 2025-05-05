package domain;
/**
 * Movimiento Psíquico: Rest
 * Efecto secundario: Cura al usuario pero lo duerme
 */
public class Rest extends Movimiento {
    public Rest() {
        super("Rest", "Psíquico", 0, 100, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Cura completamente al usuario
        int curacion = usuario.getPsMaximos() - usuario.getPs();
        usuario.aumentarPS(curacion);
        System.out.println("¡" + usuario.getNombre() + " se durmió y recuperó toda su salud!");
        // Aquí deberías implementar la lógica para aplicar el estado "dormido"
    }
}