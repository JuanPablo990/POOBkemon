package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pokemon {
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
    
    // Etapas de los stats (sin límites)
    protected int etapaAtaque;
    protected int etapaDefensa;
    protected int etapaVelocidad;
    protected int etapaAtaqueEspecial;
    protected int etapaDefensaEspecial;

    // Multiplicador base para las etapas
    private static final double MULTIPLICADOR_BASE = 1.5;
    private static final double MULTIPLICADOR_MINIMO = 0.25;
    private static final double MULTIPLICADOR_MAXIMO = 4.0;

    public Pokemon(String nombre, String tipoPrincipal, String tipoSecundario, 
                   int psMaximos, int ataqueBase, int defensaBase, int velocidadBase, 
                   int ataqueEspecialBase, int defensaEspecialBase) {
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
        
        // Inicializar etapas en 0 (multiplicador neutro)
        this.etapaAtaque = 0;
        this.etapaDefensa = 0;
        this.etapaVelocidad = 0;
        this.etapaAtaqueEspecial = 0;
        this.etapaDefensaEspecial = 0;
    }

    // --- Métodos para PS (vida) ---
    public int aumentarPS(int cantidad) {
        if (cantidad <= 0) return 0;
        
        int psAntes = this.ps;
        this.ps = Math.min(this.ps + cantidad, this.psMaximos);
        return this.ps - psAntes;
    }

    public int reducirPS(int cantidad) {
        if (cantidad <= 0) return 0;
        
        int psAntes = this.ps;
        this.ps = Math.max(this.ps - cantidad, 0);
        return psAntes - this.ps;
    }

    public boolean estaDebilitado() {
        return this.ps <= 0;
    }

    // --- Métodos para modificar etapas de stats (sin límites) ---
    public void aumentarAtaque(int cantidad) {
        this.etapaAtaque += cantidad;
    }

    public void disminuirAtaque(int cantidad) {
        this.etapaAtaque -= cantidad;
    }

    public void aumentarDefensa(int cantidad) {
        this.etapaDefensa += cantidad;
    }

    public void disminuirDefensa(int cantidad) {
        this.etapaDefensa -= cantidad;
    }

    public void aumentarVelocidad(int cantidad) {
        this.etapaVelocidad += cantidad;
    }

    public void disminuirVelocidad(int cantidad) {
        this.etapaVelocidad -= cantidad;
    }

    public void aumentarAtaqueEspecial(int cantidad) {
        this.etapaAtaqueEspecial += cantidad;
    }

    public void disminuirAtaqueEspecial(int cantidad) {
        this.etapaAtaqueEspecial -= cantidad;
    }

    public void aumentarDefensaEspecial(int cantidad) {
        this.etapaDefensaEspecial += cantidad;
    }

    public void disminuirDefensaEspecial(int cantidad) {
        this.etapaDefensaEspecial -= cantidad;
    }

    // --- Métodos para calcular multiplicadores sin límites ---
    private double calcularMultiplicador(int etapa) {
        double multiplicador;
        
        if (etapa > 0) {
            multiplicador = 1.0 + (Math.abs(etapa) * (MULTIPLICADOR_BASE - 1.0));
        } else if (etapa < 0) {
            multiplicador = 1.0 / (1.0 + (Math.abs(etapa) * (MULTIPLICADOR_BASE - 1.0)));
        } else {
            return 1.0; // Etapa 0 = multiplicador neutro
        }
        
        // Aplicar límites absolutos
        return Math.max(MULTIPLICADOR_MINIMO, Math.min(MULTIPLICADOR_MAXIMO, multiplicador));
    }

    // --- Métodos para obtener stats actuales ---
    public int getAtaque() {
        return (int)(this.ataqueBase * calcularMultiplicador(this.etapaAtaque));
    }

    public int getDefensa() {
        return (int)(this.defensaBase * calcularMultiplicador(this.etapaDefensa));
    }

    public int getVelocidad() {
        return (int)(this.velocidadBase * calcularMultiplicador(this.etapaVelocidad));
    }

    public int getAtaqueEspecial() {
        return (int)(this.ataqueEspecialBase * calcularMultiplicador(this.etapaAtaqueEspecial));
    }

    public int getDefensaEspecial() {
        return (int)(this.defensaEspecialBase * calcularMultiplicador(this.etapaDefensaEspecial));
    }

    // --- Getters básicos ---
    public String getNombre() {
        return nombre;
    }

    public String getTipoPrincipal() {
        return tipoPrincipal;
    }

    public String getTipoSecundario() {
        return tipoSecundario;
    }

    public int getPs() {
        return ps;
    }

    public int getPsMaximos() {
        return psMaximos;
    }

       // Tabla de compatibilidad de tipos para movimientos
    private static final Map<String, List<String>> COMPATIBILIDAD_MOVIMIENTOS = new HashMap<>();
    
    static {
        // Inicializar la tabla de compatibilidad
        COMPATIBILIDAD_MOVIMIENTOS.put("Acero", Arrays.asList("Acero", "Roca", "Tierra"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Agua", Arrays.asList("Agua", "Hielo"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Bicho", Arrays.asList("Bicho", "Planta", "Veneno"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Dragón", Arrays.asList("Dragón", "Agua", "Eléctrico"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Eléctrico", Arrays.asList("Eléctrico", "Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Fantasma", Arrays.asList("Fantasma", "Siniestro"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Fuego", Arrays.asList("Fuego", "Volador", "Acero"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Hada", Arrays.asList("Hada", "Psíquico", "Planta"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Hielo", Arrays.asList("Hielo", "Agua"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Lucha", Arrays.asList("Lucha", "Roca", "Siniestro"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Normal", Arrays.asList("Normal"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Planta", Arrays.asList("Planta", "Bicho", "Hada"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Psíquico", Arrays.asList("Psíquico", "Hada"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Roca", Arrays.asList("Roca", "Tierra", "Acero"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Siniestro", Arrays.asList("Siniestro", "Fantasma", "Lucha"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Tierra", Arrays.asList("Tierra", "Roca", "Acero"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Veneno", Arrays.asList("Veneno", "Planta", "Bicho"));
        COMPATIBILIDAD_MOVIMIENTOS.put("Volador", Arrays.asList("Volador", "Normal", "Lucha"));
    }

    // Lista de movimientos del Pokémon
    private List<Movimiento> movimientos = new ArrayList<>();

    /**
     * Asigna movimientos al Pokémon, verificando las restricciones:
     * - Entre 1 y 4 movimientos
     * - Compatibilidad con los tipos del Pokémon según la tabla
     * 
     * @param nuevosMovimientos Lista de movimientos a asignar
     * @throws IllegalArgumentException Si no se cumplen las restricciones
     */
    public void asignarMovimientos(List<Movimiento> nuevosMovimientos) {
        if (nuevosMovimientos == null || nuevosMovimientos.isEmpty() || nuevosMovimientos.size() > 4) {
            throw new IllegalArgumentException("Un Pokémon debe tener entre 1 y 4 movimientos");
        }

        // Verificar compatibilidad de tipos
        for (Movimiento movimiento : nuevosMovimientos) {
            if (!esMovimientoCompatible(movimiento)) {
                throw new IllegalArgumentException("El movimiento " + movimiento.getNombre() + 
                    " no es compatible con los tipos del Pokémon");
            }
        }

        // Si pasa todas las validaciones, asignar los movimientos
        this.movimientos = new ArrayList<>(nuevosMovimientos);
    }

    /**
     * Verifica si un movimiento es compatible con los tipos del Pokémon
     * 
     * @param movimiento Movimiento a verificar
     * @return true si es compatible, false si no
     */
    private boolean esMovimientoCompatible(Movimiento movimiento) {
        String tipoMovimiento = movimiento.getTipo();
        
        // Verificar compatibilidad con el tipo principal
        if (COMPATIBILIDAD_MOVIMIENTOS.containsKey(tipoMovimiento)) {
            List<String> tiposCompatibles = COMPATIBILIDAD_MOVIMIENTOS.get(tipoMovimiento);
            
            if (tiposCompatibles.contains(this.tipoPrincipal) || 
                (this.tipoSecundario != null && tiposCompatibles.contains(this.tipoSecundario))) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Añade un movimiento individual al Pokémon, verificando las restricciones
     * 
     * @param movimiento Movimiento a añadir
     * @throws IllegalArgumentException Si no se cumplen las restricciones
     */
    public void aprenderMovimiento(Movimiento movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("El movimiento no puede ser nulo");
        }

        if (!esMovimientoCompatible(movimiento)) {
            throw new IllegalArgumentException("El movimiento " + movimiento.getNombre() + 
                " no es compatible con los tipos del Pokémon");
        }

        if (movimientos.size() >= 4) {
            throw new IllegalArgumentException("El Pokémon ya tiene 4 movimientos");
        }

        movimientos.add(movimiento);
    }

    /**
     * Reemplaza un movimiento existente por uno nuevo
     * 
     * @param indice Índice del movimiento a reemplazar (0-3)
     * @param nuevoMovimiento Nuevo movimiento
     * @throws IllegalArgumentException Si no se cumplen las restricciones
     */
    public void reemplazarMovimiento(int indice, Movimiento nuevoMovimiento) {
        if (indice < 0 || indice >= movimientos.size()) {
            throw new IllegalArgumentException("Índice de movimiento inválido");
        }

        if (!esMovimientoCompatible(nuevoMovimiento)) {
            throw new IllegalArgumentException("El movimiento " + nuevoMovimiento.getNombre() + 
                " no es compatible con los tipos del Pokémon");
        }

        movimientos.set(indice, nuevoMovimiento);
    }

    // Getter para los movimientos
    public List<Movimiento> getMovimientos() {
        return new ArrayList<>(movimientos); // Devuelve una copia para proteger la encapsulación
    }
    
        // Dentro de la clase Pokemon (domain/Pokemon.java)
    public int getEtapaAtaque() {
        return this.etapaAtaque;
    }
    
    public int getEtapaDefensa() {
        return this.etapaDefensa;
    }
    
    public int getEtapaVelocidad() {
        return this.etapaVelocidad;
    }
    
    public int getEtapaAtaqueEspecial() {
        return this.etapaAtaqueEspecial;
    }
    
    public int getEtapaDefensaEspecial() {
        return this.etapaDefensaEspecial;
    }
    
    // --- Método toString actualizado ---
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
}