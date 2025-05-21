package domain;

/**
 * Movimiento que reduce el ataque del oponente
 */
public class BajarAtaque extends Movimiento {
    public BajarAtaque() {
    	super("Bajar Ataque", "normal", 0, 100, 20, 0);
    }

    @Override
    protected void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo) {
        objetivo.disminuirAtaque(1);
        System.out.println(objetivo.getNombre() + " bajó su ataque!");
    }
}