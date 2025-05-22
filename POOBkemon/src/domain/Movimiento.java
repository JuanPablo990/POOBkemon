package domain;

public abstract class Movimiento {
    protected String nombre;
    protected String tipo;
    protected int potencia;
    protected int precision;
    protected int pp;
    protected int ppMaximos;
    protected int prioridad;
    
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
        double stab = (this.tipo.equalsIgnoreCase(usuario.getTipoPrincipal()) || (usuario.getTipoSecundario() != null && this.tipo.equalsIgnoreCase(usuario.getTipoSecundario())) ? 1.5 : 1.0);
        double random = 0.85 + (Math.random() * 0.15);
        int danio = (int) ((this.potencia * ataque / defensa) * efectividad * stab * random);
        return Math.max(1, danio);
    }

    protected boolean esAtaqueFisico() {
        String[] tiposFisicos = {"normal", "lucha", "volador", "tierra", "roca", "bicho", "fantasma", "veneno", "acero"};
        for (String tipoFisico : tiposFisicos) {
            if (this.tipo.equalsIgnoreCase(tipoFisico)) {
                return true;
            }
        }
        return false;
    }
    
    protected abstract void aplicarEfectoSecundario(Pokemon usuario, Pokemon objetivo);
    
    public void restaurarPP() {
        this.pp = this.ppMaximos;
    }
    
    public int restaurarPP(int cantidad) {
        int ppAntes = this.pp;
        this.pp = Math.min(this.pp + cantidad, this.ppMaximos);
        return this.pp - ppAntes;
    }
    
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
    
    public int reducirPP(int cantidad) {
        int ppAntes = this.pp;
        this.pp = Math.max(this.pp - cantidad, 0);
        return ppAntes - this.pp;
    }

    @Override
    public String toString() {
        return String.format("%s [Tipo: %s]\n" +"Potencia: %d | Precisión: %d%%\n" +"PP: %d/%d | Prioridad: %d",nombre, tipo, potencia, precision, pp, ppMaximos, prioridad
        );
    }
}