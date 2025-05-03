package domain;

public class Magikarp extends Pokemon {
    
    public Magikarp() {
        super("Magikarp", "Agua", "Normal", 50, 70, 50, 50, 50, 40);
    }
    
    // Hereda AUTOMÁTICAMENTE todos estos métodos de Poquemon:
    /*
    // Métodos de vida
    - aumentarPS(int cantidad)
    - reducirPS(int cantidad)
    - estaDebilitado()
    
    // Métodos de modificación de stats
    - aumentarAtaque(int cantidad)
    - disminuirAtaque(int cantidad)
    - aumentarDefensa(int cantidad)
    - disminuirDefensa(int cantidad)
    - aumentarVelocidad(int cantidad)
    - disminuirVelocidad(int cantidad)
    - aumentarAtaqueEspecial(int cantidad)
    - disminuirAtaqueEspecial(int cantidad)
    - aumentarDefensaEspecial(int cantidad)
    - disminuirDefensaEspecial(int cantidad)
    
    // Métodos de obtención de stats
    - getAtaque()
    - getDefensa()
    - getVelocidad()
    - getAtaqueEspecial()
    - getDefensaEspecial()
    
    // Getters
    - getNombre()
    - getTipoPrincipal()
    - getTipoSecundario()
    - getPs()
    - getPsMaximos()
    
    // Representación
    - toString()
    */
}