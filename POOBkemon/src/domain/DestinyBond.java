package domain;
/**
 * Movimiento Fantasma: Destiny Bond
 * Efecto secundario: Si el usuario cae, el rival también
 */
public class DestinyBond extends Movimiento {
    public DestinyBond() {
        super("Destiny Bond", "Fantasma", 0, 100, 5, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Activa el efecto Destiny Bond
        System.out.println("¡" + usuario.getNombre() + " vinculó su destino con " + objetivo.getNombre() + "!");
        // Aquí deberías implementar la lógica para marcar que si el usuario cae, el objetivo también
    }
}