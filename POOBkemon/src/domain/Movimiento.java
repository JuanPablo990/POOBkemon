package domain;

/**
 * Clase abstracta que representa un movimiento Pokémon.
 * Define los atributos básicos de todos los movimientos y
 * permite implementar efectos secundarios específicos.
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
     * Constructor para crear un nuevo movimiento.
     * 
     * @param nombre Nombre del movimiento
     * @param tipo Tipo del movimiento (Fuego, Agua, etc.)
     * @param potencia Potencia base del movimiento (0 para movimientos de estado)
     * @param precision Precisión del movimiento (100 = 100% de probabilidad de acierto)
     * @param pp Puntos de poder (número de veces que se puede usar)
     * @param prioridad Prioridad del movimiento (-7 a 7, mayor prioridad va primero)
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
    
    protected int calcularDanio(Pokemon usuario, Pokemon objetivo, double efectividad) {
        int ataque = esAtaqueFisico() ? usuario.getAtaque() : usuario.getAtaqueEspecial();
        int defensa = esAtaqueFisico() ? objetivo.getDefensa() : objetivo.getDefensaEspecial();
        
        // STAB (Same Type Attack Bonus)
        double stab = (this.tipo.equalsIgnoreCase(usuario.getTipoPrincipal()) || 
                     (usuario.getTipoSecundario() != null && 
                      this.tipo.equalsIgnoreCase(usuario.getTipoSecundario())) ? 1.5 : 1.0);
        
        double random = 0.85 + (Math.random() * 0.15);
        
        int danio = (int) ((this.potencia * ataque / defensa) * efectividad * stab * random);
        return Math.max(1, danio);
    }
    
    
    /**
     * Determina si el movimiento es físico o especial.
     * Por defecto, se basa en el tipo, pero puede ser sobrescrito.
     * 
     * @return true si es un ataque físico, false si es especial
     */
    protected boolean esAtaqueFisico() {
        // Por defecto, basado en tipos físicos tradicionales (simplificado)
        String[] tiposFisicos = {"normal", "lucha", "volador", "tierra", "roca", "bicho", "fantasma", "veneno", "acero"};
        
        for (String tipoFisico : tiposFisicos) {
            if (this.tipo.equalsIgnoreCase(tipoFisico)) {
                return true;
            }
        }
        
        return false; // Tipo especial
    }
    
    /**
     * Método abstracto para aplicar efectos secundarios del movimiento.
     * Cada subclase debe implementar sus propios efectos.
     * 
     * @param usuario El Pokémon que usa el movimiento
     * @param objetivo El Pokémon objetivo del movimiento
     */
    protected abstract void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo);
    
    /**
     * Restaura los PP del movimiento al máximo.
     */
    public void restaurarPP() {
        this.pp = this.ppMaximos;
    }
    
    /**
     * Incrementa los PP actuales en la cantidad especificada, sin exceder el máximo.
     * 
     * @param cantidad Cantidad de PP a restaurar
     * @return Cantidad real de PP restaurados
     */
    public int restaurarPP(int cantidad) {
        int ppAntes = this.pp;
        this.pp = Math.min(this.pp + cantidad, this.ppMaximos);
        return this.pp - ppAntes;
    }
    
    // Getters
    
    public String getNombre() {
        return nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public int getPotencia() {
        return potencia;
    }
    
    public int getPrecision() {
        return precision;
    }
    
    public int getPp() {
        return pp;
    }
    
    public int getPpMaximos() {
        return ppMaximos;
    }
    
    public int getPrioridad() {
        return prioridad;
    }
    
        /**
     * Reduce los PP actuales en la cantidad especificada, sin que bajen de 0.
     *
     * @param cantidad Cantidad de PP a reducir
     * @return Cantidad real de PP reducidos
     */
    public int reducirPP(int cantidad) {
        int ppAntes = this.pp;
        this.pp = Math.max(this.pp - cantidad, 0);
        return ppAntes - this.pp;
    }

    
    @Override
    public String toString() {
        return String.format(
            "%s [Tipo: %s]\n" +
            "Potencia: %d | Precisión: %d%%\n" +
            "PP: %d/%d | Prioridad: %d",
            nombre, tipo, potencia, precision, pp, ppMaximos, prioridad
        );
    }
}