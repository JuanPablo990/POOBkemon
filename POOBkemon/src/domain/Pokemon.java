package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    /**
     * Clase que representa un Pokémon.
     */
    
public class Pokemon implements Serializable {
	private static final long serialVersionUID = 1L;
    protected String nombre;
    protected String tipoPrincipal;
    protected String tipoSecundario;
    protected int ps;
    protected int psMaximos;
    protected int ataqueBase;
    protected int defensaBase;
    protected int velocidadBase;
    protected int ataqueEspecialBase;
    protected int defensaEspecialBase;
    protected int etapaAtaque;
    protected int etapaDefensa;
    protected int etapaVelocidad;
    protected int etapaAtaqueEspecial;
    protected int etapaDefensaEspecial;
    private static final double MULTIPLICADOR_BASE = 1.5;
    private static final double MULTIPLICADOR_MINIMO = 0.25;
    private static final double MULTIPLICADOR_MAXIMO = 4.0;
    private List<Movimiento> movimientos = new ArrayList<>();

    /**
	 * Constructor de la clase Pokemon.
	 *
	 * @param nombre Nombre del Pokémon.
	 * @param tipoPrincipal Tipo principal del Pokémon.
	 * @param tipoSecundario Tipo secundario del Pokémon.
	 * @param psMaximos PS máximos del Pokémon.
	 * @param ataqueBase Ataque base del Pokémon.
	 * @param defensaBase Defensa base del Pokémon.
	 * @param velocidadBase Velocidad base del Pokémon.
	 * @param ataqueEspecialBase Ataque especial base del Pokémon.
	 * @param defensaEspecialBase Defensa especial base del Pokémon.
	 */
    public Pokemon(String nombre,String tipoPrincipal,String tipoSecundario,int psMaximos,int ataqueBase,int defensaBase,int velocidadBase,int ataqueEspecialBase, int defensaEspecialBase) {
        this.nombre = nombre;
        this.tipoPrincipal = tipoPrincipal;
        this.tipoSecundario = tipoSecundario;
        this.psMaximos = psMaximos;
        this.ps = psMaximos;
        this.ataqueBase = ataqueBase;
        this.defensaBase = defensaBase;
        this.velocidadBase = velocidadBase;
        this.ataqueEspecialBase = ataqueEspecialBase;
        this.defensaEspecialBase = defensaEspecialBase;
        this.etapaAtaque = 0;
        this.etapaDefensa = 0;
        this.etapaVelocidad = 0;
        this.etapaAtaqueEspecial = 0;
        this.etapaDefensaEspecial = 0;
    }

   /**
	 * Constructor de la clase Pokemon.
	 *
	 * @param nombre Nombre del Pokémon.
	 * @param tipoPrincipal Tipo principal del Pokémon.
	 * @param tipoSecundario Tipo secundario del Pokémon.
	 * @param psMaximos PS máximos del Pokémon.
	 */
    public int aumentarPS(int cantidad) {
        if (cantidad <= 0) return 0;
        int psAntes = this.ps;
        this.ps = Math.min(this.ps + cantidad, this.psMaximos);
        return this.ps - psAntes;
    }

    /**
     * Reduce los PS del Pokémon.
     * @param cantidad
     * @return
     */
    public int reducirPS(int cantidad) {
        if (cantidad <= 0) return 0;
        int psAntes = this.ps;
        this.ps = Math.max(this.ps - cantidad, 0);
        return psAntes - this.ps;
    }

    /**
	 * Verifica si el Pokémon está debilitado.
	 * @return true si el Pokémon está debilitado, false en caso contrario.
	 */
    public boolean estaDebilitado() {
        return this.ps <= 0;
    }
    
    /**
     * Aumenta la etapa de ataque del Pokémon.
     * @param cantidad
     */
    public void aumentarAtaque(int cantidad) {
        this.etapaAtaque += cantidad;
    }

    /**
	 * Disminuye la etapa de ataque del Pokémon.
	 * @param cantidad
	 */
    public void disminuirAtaque(int cantidad) {
        this.etapaAtaque -= cantidad;
    }

    /**
     * Aumenta la etapa de defensa del Pokémon.
     * @param cantidad
     */
    public void aumentarDefensa(int cantidad) {
        this.etapaDefensa += cantidad;
    }

    /**
     * Disminuye la etapa de defensa del Pokémon.
     * @param cantidad
     */
    public void disminuirDefensa(int cantidad) {
        this.etapaDefensa -= cantidad;
    }

    /**
	 * Aumenta la etapa de velocidad del Pokémon.
	 * @param cantidad
	 */
    public void aumentarVelocidad(int cantidad) {
        this.etapaVelocidad += cantidad;
    }

    /**
     * Disminuye la etapa de velocidad del Pokémon.
     * @param cantidad
     */
    public void disminuirVelocidad(int cantidad) {
        this.etapaVelocidad -= cantidad;
    }

    /**
	 * Aumenta la etapa de ataque especial del Pokémon.
	 * @param cantidad
	 */
    public void aumentarAtaqueEspecial(int cantidad) {
        this.etapaAtaqueEspecial += cantidad;
    }

    /**
	 * Disminuye la etapa de ataque especial del Pokémon.
	 * @param cantidad
	 */
    public void disminuirAtaqueEspecial(int cantidad) {
        this.etapaAtaqueEspecial -= cantidad;
    }

    /**
     * Aumenta la etapa de defensa especial del Pokémon.
     * @param cantidad
     */
    public void aumentarDefensaEspecial(int cantidad) {
        this.etapaDefensaEspecial += cantidad;
    }

    /**
	 * Disminuye la etapa de defensa especial del Pokémon.
	 * @param cantidad
	 */
    public void disminuirDefensaEspecial(int cantidad) {
        this.etapaDefensaEspecial -= cantidad;
    }

    /**
     * Calcula el multiplicador de estadísticas según la etapa.
     * @param etapa
     * @return
     */
    private double calcularMultiplicador(int etapa) {
        double multiplicador;
        if (etapa > 0) {
            multiplicador = 1.0 + (Math.abs(etapa) * (MULTIPLICADOR_BASE - 1.0));
        } else if (etapa < 0) {
            multiplicador = 1.0 / (1.0 + (Math.abs(etapa) * (MULTIPLICADOR_BASE - 1.0)));
        } else {
            return 1.0; 
        }
        return Math.max(MULTIPLICADOR_MINIMO, Math.min(MULTIPLICADOR_MAXIMO, multiplicador));
    }

    /**
	 * Calcula el daño de un movimiento.
	 * @param movimiento
	 * @return
	 */
    public int getAtaque() {
        return (int)(this.ataqueBase * calcularMultiplicador(this.etapaAtaque));
    }

    /**
     * Calcula la defensa del Pokémon.
     * @return
     */
    public int getDefensa() {
        return (int)(this.defensaBase * calcularMultiplicador(this.etapaDefensa));
    }

    /**
	 * Calcula la velocidad del Pokémon.
	 * @return
	 */
    public int getVelocidad() {
        return (int)(this.velocidadBase * calcularMultiplicador(this.etapaVelocidad));
    }

    /**
     * Calcula el ataque especial del Pokémon.
     * @return
     */
    public int getAtaqueEspecial() {
        return (int)(this.ataqueEspecialBase * calcularMultiplicador(this.etapaAtaqueEspecial));
    }

    /**
	 * Calcula la defensa especial del Pokémon.
	 * @return
	 */
    public int getDefensaEspecial() {
        return (int)(this.defensaEspecialBase * calcularMultiplicador(this.etapaDefensaEspecial));
    }

    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @param movimiento
	 * @return
	 */
    public String getNombre() {
        return nombre;
    }

    /**
     * Verifica si el Pokémon puede aprender un movimiento.
     * @return
     */
    public String getTipoPrincipal() {
        return tipoPrincipal;
    }

    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @return
	 */
    public String getTipoSecundario() {
        return tipoSecundario;
    }

    /**
     * Verifica si el Pokémon puede aprender un movimiento.
     * @return
     */
    public int getPs() {
        return ps;
    }

    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @return
	 */
    public int getPsMaximos() {
        return psMaximos;
    }

    /**
     * Verifica si el Pokémon puede aprender un movimiento.
     */
    private static final Map<String, List<String>> COMPATIBILIDAD_MOVIMIENTOS = new HashMap<>();
    static {
        COMPATIBILIDAD_MOVIMIENTOS.put("Acero", Arrays.asList("Acero", "Roca", "Tierra", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Agua", Arrays.asList("Agua", "Hielo", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Bicho", Arrays.asList("Bicho", "Planta", "Veneno", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Dragón", Arrays.asList("Dragón", "Agua", "Eléctrico", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Eléctrico", Arrays.asList("Eléctrico", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Fantasma", Arrays.asList("Fantasma", "Siniestro", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Fuego", Arrays.asList("Fuego", "Volador", "Acero", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Hada", Arrays.asList("Hada", "Psíquico", "Planta", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Hielo", Arrays.asList("Hielo", "Agua", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Lucha", Arrays.asList("Lucha", "Roca", "Siniestro", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Normal", Arrays.asList("Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Planta", Arrays.asList("Planta", "Normal", "Bicho", "Hada"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Psíquico", Arrays.asList("Psíquico", "Hada", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Roca", Arrays.asList("Roca", "Tierra", "Acero", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Siniestro", Arrays.asList("Siniestro", "Fantasma", "Lucha", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Tierra", Arrays.asList("Tierra", "Roca", "Acero", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Veneno", Arrays.asList("Veneno", "Planta", "Bicho", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Volador", Arrays.asList("Volador", "Normal", "Lucha"));
    }

    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @param movimiento
	 * @return
	 */
    public void asignarMovimientos(List<Movimiento> nuevosMovimientos) {
        if (nuevosMovimientos == null || nuevosMovimientos.isEmpty() || nuevosMovimientos.size() > 4) {
            throw new IllegalArgumentException("Un Pokémon debe tener entre 1 y 4 movimientos");
        }
        this.movimientos = new ArrayList<>(nuevosMovimientos);
    }

    /**
     * Verifica si el Pokémon puede aprender un movimiento.
     * @param movimiento
     * @return
     */
    private boolean esMovimientoCompatible(Movimiento movimiento) {
        String tipoMovimiento = movimiento.getTipo();
        if (COMPATIBILIDAD_MOVIMIENTOS.containsKey(tipoMovimiento)) {
            List<String> tiposCompatibles = COMPATIBILIDAD_MOVIMIENTOS.get(tipoMovimiento);
            if (tiposCompatibles.contains(this.tipoPrincipal) || (this.tipoSecundario != null && tiposCompatibles.contains(this.tipoSecundario))) {
                return true;
            }
        }
        return false;
    }
    
    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @param movimiento
	 * @return
	 */
    public void aprenderMovimiento(Movimiento movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("El movimiento no puede ser nulo");
        }
        if (movimientos.size() >= 4) {
            throw new IllegalArgumentException("El Pokémon ya tiene 4 movimientos");
        }
        movimientos.add(movimiento);
    }

    /**
     * Reemplaza un movimiento en la lista de movimientos del Pokémon.
     * @param indice
     * @param nuevoMovimiento
     */
    public void reemplazarMovimiento(int indice, Movimiento nuevoMovimiento) {
        if (indice < 0 || indice >= movimientos.size()) {
        	throw new IllegalArgumentException("Índice de movimiento inválido");
        }
        movimientos.set(indice, nuevoMovimiento);
    }

    /**
	 * Elimina un movimiento de la lista de movimientos del Pokémon.
	 * @param indice
	 */
    public List<Movimiento> getMovimientos() {
        return new ArrayList<>(movimientos); 
    }
    
    /**
     * Verifica si el Pokémon puede aprender un movimiento.
     * @return
     */
    public int getEtapaAtaque() {
        return this.etapaAtaque;
    }
    
    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @return
	 */
    public int getEtapaDefensa() {
        return this.etapaDefensa;
    }
    
    /**
     * Verifica si el Pokémon puede aprender un movimiento.
     * @return
     */
    public int getEtapaVelocidad() {
        return this.etapaVelocidad;
    }
    
    /**
     * Verifica si el Pokémon puede aprender un movimiento.
     * @return
     */
    public int getEtapaAtaqueEspecial() {
        return this.etapaAtaqueEspecial;
    }
    
    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @return
	 */
    public int getEtapaDefensaEspecial() {
        return this.etapaDefensaEspecial;
    }
    
    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @return
	 */
    @Override
    public String toString() {
        return String.format(
            "Pokemon: %s [%s/%s]\n" +
            "PS: %d/%d\n" +
            "Ataque: %d (etapa %d)\n" +
            "Defensa: %d (etapa %d)\n" +
            "Velocidad: %d (etapa %d)\n" +
            "Ataque Especial: %d (etapa %d)\n" +
            "Defensa Especial: %d (etapa %d)",
            nombre, tipoPrincipal, tipoSecundario,
            ps, psMaximos,
            getAtaque(), etapaAtaque,
            getDefensa(), etapaDefensa,
            getVelocidad(), etapaVelocidad,
            getAtaqueEspecial(), etapaAtaqueEspecial,
            getDefensaEspecial(), etapaDefensaEspecial
        );
    }
    
    /**
	 * Verifica si el Pokémon puede aprender un movimiento.
	 * @param nombres
	 */
    public void setMovimientosDesdeNombres(List<String> nombres) {
        List<Movimiento> lista = new ArrayList<>();
        for (String nombre : nombres) {
            Movimiento m = Poquedex.getInstancia().crearMovimiento(nombre);
            if (m != null) {
                lista.add(m);
            }
        }
        asignarMovimientos(lista);
    }

}