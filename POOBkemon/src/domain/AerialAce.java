package domain;

public class AerialAce extends Movimiento {
    public AerialAce() {
        super("Aerial Ace", "Volador", 60, 100, 20, 0);
    }

    @Override
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }
        this.pp--;

        // Ignora precisión (como "no falla")
        int danio = calcularDanio(usuario, objetivo, efectividad);
        objetivo.reducirPS(danio);
        System.out.println("¡Aerial Ace hizo " + danio + " de daño sin fallar!");
        aplicarEfectoSecundario(usuario, objetivo);
        return true;
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Sin efecto adicional
    }
}
