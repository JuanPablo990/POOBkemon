package domain;

public class MeteorMash extends Movimiento {
    public MeteorMash() {
        super("MeteorMash", "Acero", 90, 90, 10, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        // Efecto secundario: 20% de aumentar el Ataque del usuario
    }
}
