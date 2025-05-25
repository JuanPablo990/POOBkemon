package domain;

    /**
     * Clase abstracta que representa un movimiento de un Pokémon.
	 * Contiene atributos y métodos comunes para todos los movimientos.	
     */
public abstract class Movimiento {
    protected String nombre;
    protected String tipo;
    protected int potencia;
    protected int precision;
    protected int pp;
    protected int ppMaximos;
    protected int prioridad;
    
    /**
	 * Constructor de la clase Movimiento.
	 * @param nombre Nombre del movimiento.
	 * @param tipo Tipo del movimiento.
	 * @param potencia Potencia del movimiento.
	 * @param precision Precisión del movimiento.
	 * @param pp Puntos de poder del movimiento.
	 * @param prioridad Prioridad del movimiento.
	 */
    public Movimiento(String nombre, String tipo, int potencia, int precision, int pp, int prioridad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.precision = precision;
        this.pp = pp;
        this.ppMaximos = pp;
        this.prioridad = prioridad;
    }
    
    /**
     * Ejecuta el movimiento.
     * @param usuario
     * @param objetivo
     * @param efectividad
     * @return
     */
    public boolean ejecutar(Pokemon usuario, Pokemon objetivo, double efectividad) {
        if (this.pp <= 0) {
            System.out.println("¡No quedan PP para este movimiento!");
            return false;
        }
        this.pp--;
        if (Math.random() * 100 > this.precision) {
            System.out.println("¡El ataque falló!");
            return false;
        }
        if (this.potencia > 0) {
            int danio = calcularDanio(usuario, objetivo, efectividad);
            objetivo.reducirPS(danio);
            System.out.println("¡El ataque hizo " + danio + " de daño!");
        }
        aplicarEfectoSecundario(usuario, objetivo);
        return true;    
    }
    
    /**
	 * Calcula el daño del movimiento.
	 * @param usuario
	 * @param objetivo
	 * @param efectividad
	 * @return
	 */
    protected int calcularDanio(Pokemon usuario, Pokemon objetivo, double efectividad) {
        int ataque = esAtaqueFisico() ? usuario.getAtaque() : usuario.getAtaqueEspecial();
        int defensa = esAtaqueFisico() ? objetivo.getDefensa() : objetivo.getDefensaEspecial();
        double stab = (this.tipo.equalsIgnoreCase(usuario.getTipoPrincipal()) || (usuario.getTipoSecundario() != null && this.tipo.equalsIgnoreCase(usuario.getTipoSecundario())) ? 1.5 : 1.0);
        double random = 0.85 + (Math.random() * 0.15);
        int danio = (int) ((this.potencia * ataque / defensa) * efectividad * stab * random);
        return Math.max(1, danio);
    }

    /**
     * Verifica si el movimiento es de tipo físico.
     * @return
     */
    protected boolean esAtaqueFisico() {
        String[] tiposFisicos = {"normal", "lucha", "volador", "tierra", "roca", "bicho", "fantasma", "veneno", "acero"};
        for (String tipoFisico : tiposFisicos) {
            if (this.tipo.equalsIgnoreCase(tipoFisico)) {
                return true;
            }
        }
        return false;
    }
    
    /**
	 * Aplica el efecto secundario del movimiento.
	 * @param usuario
	 * @param objetivo
	 */
    protected abstract void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo);
    
    /**
     * Restaura los puntos de poder (PP) del movimiento al máximo.
     */
    public void restaurarPP() {
        this.pp = this.ppMaximos;
    }
    
    /**
     * Restaura una cantidad específica de puntos de poder (PP) del movimiento.
     * @param cantidad
     * @return
     */
    public int restaurarPP(int cantidad) {
        int ppAntes = this.pp;
        this.pp = Math.min(this.pp + cantidad, this.ppMaximos);
        return this.pp - ppAntes;
    }
    
    /**
     * Devuelve el nombre del movimiento.
     * @return
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
	 * Devuelve el tipo del movimiento.
	 * @return
	 */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Devuelve la potencia del movimiento.
     * @return
     */
    public int getPotencia() {
        return potencia;
    }
    
    /**
	 * Devuelve la precisión del movimiento.
	 * @return
	 */
    public int getPrecision() {
        return precision;
    }
    
    /**
     * Devuelve los puntos de poder (PP) restantes del movimiento.
     * @return
     */
    public int getPp() {
        return pp;
    }
    
    /**
	 * Devuelve los puntos de poder (PP) máximos del movimiento.
	 * @return
	 */
    public int getPpMaximos() {
        return ppMaximos;
    }
    
    /**
     * Devuelve la prioridad del movimiento.
     * @return
     */
    public int getPrioridad() {
        return prioridad;
    }
    
    /**
	 * Reduce los puntos de poder (PP) del movimiento en una cantidad específica.
	 * @param cantidad
	 * @return
	 */
    public int reducirPP(int cantidad) {
        int ppAntes = this.pp;
        this.pp = Math.max(this.pp - cantidad, 0);
        return ppAntes - this.pp;
    }

    /**
     * Devuelve una representación en cadena del movimiento.
     */
    @Override
    public String toString() {
        return String.format("%s [Tipo: %s]\n" +"Potencia: %d | Precisión: %d%%\n" +"PP: %d/%d | Prioridad: %d",nombre, tipo, potencia, precision, pp, ppMaximos, prioridad
        );
    }
}