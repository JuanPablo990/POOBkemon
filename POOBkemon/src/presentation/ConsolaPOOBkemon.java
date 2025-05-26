package presentation;

import domain.*;
import domain.items.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * ConsolaPOOBkemon es la clase principal para el juego de POOBkemon en consola.
 * Administra el flujo del juego, la interacción con el usuario y la lógica de batalla.
 */
public class ConsolaPOOBkemon {
    /** Manejador principal de la lógica del juego */
    private POOBkemon juego;
    /** Scanner para la entrada del usuario */
    private Scanner scanner;
    /** Generador de números aleatorios para selecciones aleatorias */
    private Random random;
    /** Entrenador máquina para oponentes controlados por IA */
    private Machine maquina;

    /**
     * Construye una nueva instancia de ConsolaPOOBkemon e inicializa el juego.
     */
    public ConsolaPOOBkemon() {
        this.juego = new POOBkemon();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    /**
     * Inicia el juego y muestra el menú principal para seleccionar el modo de juego.
     */
    public void iniciarJuego() {
        System.out.println("¡Bienvenido a POOBkemon!");
        System.out.println("1. Jugador vs Jugador");
        System.out.println("2. Jugador vs Máquina");
        System.out.println("3. Máquina vs Máquina");
        System.out.println("4. Salir");
        System.out.print("Seleccione un modo de juego: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        switch (opcion) {
            case 1 -> iniciarPvP();
            case 2 -> iniciarPvM();
            case 3 -> iniciarMvM();
            case 4 -> System.out.println("¡Hasta luego!");
            default -> {
                System.out.println("Opción inválida. Intente de nuevo.");
                iniciarJuego();
            }
        }
    }

    /**
     * Inicia el modo Jugador vs Jugador.
     */
    private void iniciarPvP() {
        System.out.print("Nombre del Jugador 1: ");
        String nombreJ1 = scanner.nextLine();
        System.out.print("Nombre del Jugador 2: ");
        String nombreJ2 = scanner.nextLine();

        Entrenador jugador1 = juego.crearEntrenador(nombreJ1);
        Entrenador jugador2 = juego.crearEntrenador(nombreJ2);

        // Configura equipos y movimientos aleatorios
        juego.generarEquipoAleatorioCompleto(jugador1);
        juego.generarEquipoAleatorioCompleto(jugador2);
        asignarMovimientosAleatorios(jugador1);
        asignarMovimientosAleatorios(jugador2);

        // Asigna ítems aleatorios
        jugador1.darItemsAleatorios();
        jugador2.darItemsAleatorios();

        juego.setJugadores(jugador1, jugador2);
        juego.prepararBatalla();
        iniciarBatalla();
    }

    /**
     * Inicia el modo Jugador vs Máquina.
     */
    private void iniciarPvM() {
        System.out.print("Nombre del Jugador: ");
        String nombreJugador = scanner.nextLine();

        System.out.println("Seleccione el tipo de entrenador máquina:");
        System.out.println("1. Entrenador Ofensivo");
        System.out.println("2. Entrenador Defensivo");
        System.out.println("3. Entrenador Cambiante");
        System.out.println("4. Entrenador Experto");
        System.out.print("Opción: ");
        int tipoMaquina = scanner.nextInt();
        scanner.nextLine();

        Entrenador jugador = juego.crearEntrenador(nombreJugador);
        this.maquina = crearMaquina(tipoMaquina);

        // Configura equipos y movimientos aleatorios
        juego.generarEquipoAleatorioCompleto(jugador);
        maquina.seleccionarEquipo();
        // Asegurar que la máquina tenga 6 Pokémon
        while (maquina.getEntrenador().getEquipoPokemon().size() < 6) {
            maquina.seleccionarEquipo();
        }
        
        asignarMovimientosAleatorios(jugador);
        maquina.seleccionarMovimientos();

        // Asigna ítems aleatorios
        jugador.darItemsAleatorios();
        maquina.seleccionarItems();

        juego.setJugadores(jugador, maquina.getEntrenador());
        juego.prepararBatalla();
        iniciarBatalla();
    }

    /**
     * Inicia el modo Máquina vs Máquina.
     */
    private void iniciarMvM() {
        System.out.println("Seleccione el tipo de entrenador máquina 1:");
        System.out.println("1. Entrenador Ofensivo");
        System.out.println("2. Entrenador Defensivo");
        System.out.println("3. Entrenador Cambiante");
        System.out.println("4. Entrenador Experto");
        System.out.print("Opción: ");
        int tipoMaquina1 = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Seleccione el tipo de entrenador máquina 2:");
        System.out.println("1. Entrenador Ofensivo");
        System.out.println("2. Entrenador Defensivo");
        System.out.println("3. Entrenador Cambiante");
        System.out.println("4. Entrenador Experto");
        System.out.print("Opción: ");
        int tipoMaquina2 = scanner.nextInt();
        scanner.nextLine();

        Machine maquina1 = crearMaquina(tipoMaquina1);
        Machine maquina2 = crearMaquina(tipoMaquina2);

        // Configura equipos, movimientos e ítems
        maquina1.seleccionarEquipo();
        // Asegurar que la máquina 1 tenga 6 Pokémon
        while (maquina1.getEntrenador().getEquipoPokemon().size() < 6) {
            maquina1.seleccionarEquipo();
        }
        
        maquina2.seleccionarEquipo();
        // Asegurar que la máquina 2 tenga 6 Pokémon
        while (maquina2.getEntrenador().getEquipoPokemon().size() < 6) {
            maquina2.seleccionarEquipo();
        }
        
        maquina1.seleccionarMovimientos();
        maquina2.seleccionarMovimientos();
        maquina1.seleccionarItems();
        maquina2.seleccionarItems();

        juego.setJugadores(maquina1.getEntrenador(), maquina2.getEntrenador());
        juego.prepararBatalla();
        iniciarBatallaAutomatica();
    }

    /**
     * Crea un entrenador máquina del tipo especificado.
     * @param tipo El tipo de entrenador máquina.
     * @return La instancia de Machine creada.
     */
    private Machine crearMaquina(int tipo) {
        String nombre = switch (tipo) {
            case 1 -> "Entrenador Ofensivo";
            case 2 -> "Entrenador Defensivo";
            case 3 -> "Entrenador Cambiante";
            case 4 -> "Entrenador Experto";
            default -> "Máquina";
        };

        return switch (tipo) {
            case 1 -> new AttackingTrainer();
            case 2 -> new DefensiveTrainer();
            case 3 -> new ChangingTrainer(nombre);
            case 4 -> new ExpertTrainer(nombre);
            default -> new ChangingTrainer(nombre);
        };
    }

    /**
     * Asigna movimientos aleatorios a cada Pokémon del equipo del entrenador.
     * @param entrenador El entrenador al que se le asignan los movimientos.
     */
    private void asignarMovimientosAleatorios(Entrenador entrenador) {
        List<String> todosMovimientos = juego.obtenerNombresMovimientosDisponibles();
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            List<String> movimientosSeleccionados = new ArrayList<>();
            for (int i = 0; i < 4 && i < todosMovimientos.size(); i++) {
                String movimiento = todosMovimientos.get(random.nextInt(todosMovimientos.size()));
                if (!movimientosSeleccionados.contains(movimiento)) {
                    movimientosSeleccionados.add(movimiento);
                }
            }
            pokemon.setMovimientosDesdeNombres(movimientosSeleccionados);
        }
    }

    /**
     * Inicia una batalla y gestiona la lógica de turnos para jugador y máquina.
     */
    private void iniciarBatalla() {
        Batalla batalla = juego.getBatallaActual();
        System.out.println("\n¡Comienza la batalla entre " +
            batalla.getEntrenador1().getNombre() + " y " +
            batalla.getEntrenador2().getNombre() + "!");
        
        while (!juego.isBatallaTerminada()) {
            Entrenador entrenadorEnTurno = juego.getEntrenadorEnTurno();
            System.out.println("\n--- Turno de " + entrenadorEnTurno.getNombre() + " ---");
            
            // Verificar si el entrenador tiene Pokémon disponibles
            if (!tienePokemonDisponibles(entrenadorEnTurno)) {
                System.out.println(entrenadorEnTurno.getNombre() + " no tiene Pokémon disponibles!");
                break;
            }
            
            if (entrenadorEnTurno.equals(juego.getEntrenador1())) {
                turnoJugador(entrenadorEnTurno);
            } else {
                if (maquina != null && entrenadorEnTurno.equals(maquina.getEntrenador())) {
                    turnoMaquina(entrenadorEnTurno);
                } else {
                    turnoJugador(entrenadorEnTurno);
                }
            }
            juego.cambiarTurno();
        }
        
        Entrenador ganador = determinarGanador();
        if (ganador != null) {
            System.out.println("\n¡" + ganador.getNombre() + " ha ganado la batalla!");
        } else {
            System.out.println("\nLa batalla ha terminado sin un ganador claro.");
        }
    }

    /**
     * Verifica si un entrenador tiene Pokémon disponibles.
     * @param entrenador El entrenador a verificar.
     * @return true si tiene Pokémon disponibles, false en caso contrario.
     */
    private boolean tienePokemonDisponibles(Entrenador entrenador) {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            if (pokemon.getPs() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determina el ganador de la batalla.
     * @return El entrenador ganador, o null si no hay un ganador claro.
     */
    private Entrenador determinarGanador() {
        boolean entrenador1TienePokemon = tienePokemonDisponibles(juego.getEntrenador1());
        boolean entrenador2TienePokemon = tienePokemonDisponibles(juego.getEntrenador2());
        
        if (!entrenador1TienePokemon && !entrenador2TienePokemon) {
            return null; // Empate
        } else if (!entrenador1TienePokemon) {
            return juego.getEntrenador2();
        } else if (!entrenador2TienePokemon) {
            return juego.getEntrenador1();
        }
        return null; // La batalla aún no ha terminado
    }

    /**
     * Inicia una batalla automática entre dos entrenadores máquina.
     */
    private void iniciarBatallaAutomatica() {
        Batalla batalla = juego.getBatallaActual();
        System.out.println("\n¡Comienza la batalla entre " +
            batalla.getEntrenador1().getNombre() + " y " +
            batalla.getEntrenador2().getNombre() + "!");
        
        while (!juego.isBatallaTerminada()) {
            Entrenador entrenadorEnTurno = juego.getEntrenadorEnTurno();
            System.out.println("\n--- Turno de " + entrenadorEnTurno.getNombre() + " ---");
            
            // Verificar si el entrenador tiene Pokémon disponibles
            if (!tienePokemonDisponibles(entrenadorEnTurno)) {
                System.out.println(entrenadorEnTurno.getNombre() + " no tiene Pokémon disponibles!");
                break;
            }
            
            turnoMaquina(entrenadorEnTurno);
            juego.cambiarTurno();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        Entrenador ganador = determinarGanador();
        if (ganador != null) {
            System.out.println("\n¡" + ganador.getNombre() + " ha ganado la batalla!");
        } else {
            System.out.println("\nLa batalla ha terminado sin un ganador claro.");
        }
    }

    /**
     * Gestiona la lógica del turno para un jugador humano.
     * @param entrenador El entrenador cuyo turno es.
     */
    private void turnoJugador(Entrenador entrenador) {
        Pokemon activo = juego.getPokemonActivo(entrenador);
        if (activo.getPs() <= 0) {
            System.out.println("¡" + activo.getNombre() + " está debilitado!");
            cambiarPokemonDebilitado(entrenador);
            return;
        }
        
        System.out.println("Pokémon activo: " + activo.getNombre() +
                          " (PS: " + activo.getPs() + "/" + activo.getPsMaximos() + ")");
        List<String> opciones = juego.getOpcionesTurno(entrenador);
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i+1) + ". " + opciones.get(i));
        }
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opciones.get(opcion-1)) {
            case "ATACAR" -> {
                List<Movimiento> movimientos = juego.getMovimientosDisponibles(activo);
                System.out.println("Movimientos disponibles:");
                for (int i = 0; i < movimientos.size(); i++) {
                    Movimiento m = movimientos.get(i);
                    System.out.println((i+1) + ". " + m.getNombre() +
                                      " (PP: " + m.getPp() + "/" + m.getPpMaximos() + ")");
                }
                System.out.print("Seleccione un movimiento: ");
                int movimientoIndex = scanner.nextInt();
                scanner.nextLine();
                if (movimientoIndex > 0 && movimientoIndex <= movimientos.size()) {
                    juego.getBatallaActual().ejecutarTurno(1);
                    System.out.println(activo.getNombre() + " usó " +
                                     movimientos.get(movimientoIndex-1).getNombre());
                }
            }
            case "CAMBIAR_POKEMON" -> {
                List<Pokemon> disponibles = juego.getPokemonsDisponiblesParaCambio(entrenador);
                System.out.println("Pokémon disponibles:");
                for (int i = 0; i < disponibles.size(); i++) {
                    Pokemon p = disponibles.get(i);
                    System.out.println((i+1) + ". " + p.getNombre() +
                                     " (PS: " + p.getPs() + "/" + p.getPsMaximos() + ")");
                }
                System.out.print("Seleccione un Pokémon: ");
                int pokemonIndex = scanner.nextInt();
                scanner.nextLine();
                if (pokemonIndex > 0 && pokemonIndex <= disponibles.size()) {
                    entrenador.setPokemonActivo(disponibles.get(pokemonIndex-1));
                    System.out.println("¡" + entrenador.getNombre() + " ha cambiado a " +
                                     disponibles.get(pokemonIndex-1).getNombre() + "!");
                }
            }
            case "USAR_ITEM" -> {
                List<Item> items = entrenador.getMochilaItems();
                System.out.println("Items disponibles:");
                for (int i = 0; i < items.size(); i++) {
                    System.out.println((i+1) + ". " + items.get(i).getClass().getSimpleName());
                }
                System.out.print("Seleccione un item: ");
                int itemIndex = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Pokémon disponibles:");
                List<Pokemon> equipo = entrenador.getEquipoPokemon();
                for (int i = 0; i < equipo.size(); i++) {
                    Pokemon p = equipo.get(i);
                    System.out.println((i+1) + ". " + p.getNombre() +
                                     " (PS: " + p.getPs() + "/" + p.getPsMaximos() + ")");
                }
                System.out.print("Seleccione un Pokémon: ");
                int pokemonIndex = scanner.nextInt();
                scanner.nextLine();
                if (itemIndex > 0 && itemIndex <= items.size() &&
                    pokemonIndex > 0 && pokemonIndex <= equipo.size()) {
                    String resultado = juego.usarItem(entrenador, itemIndex-1, pokemonIndex-1);
                    System.out.println(resultado);
                }
            }
        }
    }

    /**
     * Maneja el cambio de Pokémon cuando el actual está debilitado.
     * @param entrenador El entrenador que necesita cambiar de Pokémon.
     */
    private void cambiarPokemonDebilitado(Entrenador entrenador) {
        List<Pokemon> disponibles = juego.getPokemonsDisponiblesParaCambio(entrenador);
        if (disponibles.isEmpty()) {
            System.out.println("¡" + entrenador.getNombre() + " no tiene más Pokémon disponibles!");
            return;
        }
        
        System.out.println("Pokémon disponibles:");
        for (int i = 0; i < disponibles.size(); i++) {
            Pokemon p = disponibles.get(i);
            System.out.println((i+1) + ". " + p.getNombre() +
                             " (PS: " + p.getPs() + "/" + p.getPsMaximos() + ")");
        }
        System.out.print("Seleccione un Pokémon: ");
        int pokemonIndex = scanner.nextInt();
        scanner.nextLine();
        if (pokemonIndex > 0 && pokemonIndex <= disponibles.size()) {
            entrenador.setPokemonActivo(disponibles.get(pokemonIndex-1));
            System.out.println("¡" + entrenador.getNombre() + " ha cambiado a " +
                             disponibles.get(pokemonIndex-1).getNombre() + "!");
        }
    }

    /**
     * Gestiona la lógica del turno para un entrenador máquina.
     * @param entrenador El entrenador máquina cuyo turno es.
     */
    /**
     * Gestiona la lógica del turno para un entrenador máquina.
     * @param entrenador El entrenador máquina cuyo turno es.
     */
private void turnoMaquina(Entrenador entrenador) {
    Pokemon activo = juego.getPokemonActivo(entrenador);
    if (activo.getPs() <= 0) {
        System.out.println("¡" + activo.getNombre() + " está debilitado!");
        for (Pokemon p : entrenador.getEquipoPokemon()) {
            if (p.getPs() > 0) {
                entrenador.setPokemonActivo(p);
                System.out.println("¡" + entrenador.getNombre() + " ha cambiado a " + p.getNombre() + "!");
                break;
            }
        }
        return;
    }
    
    Entrenador oponente = juego.getEntrenador1().equals(entrenador) ? juego.getEntrenador2() : juego.getEntrenador1();
    Pokemon objetivo = oponente.getPokemonActivo();
    
    System.out.println("Pokémon activo: " + activo.getNombre() + " (PS: " + activo.getPs() + "/" + activo.getPsMaximos() + ")");
    System.out.println("Objetivo: " + objetivo.getNombre() + " (PS: " + objetivo.getPs() + "/" + objetivo.getPsMaximos() + ")");
    
    List<Movimiento> movimientos = juego.getMovimientosDisponibles(activo);
    if (!movimientos.isEmpty()) {
        Movimiento movimiento = movimientos.get(0);
        System.out.println("¡" + activo.getNombre() + " usa " + movimiento.getNombre() + "!");
        
        double efectividad = juego.getBatallaActual().calcularEfectividad(movimiento, objetivo);
        System.out.printf("[Multiplicador: x%.1f] %s%n", efectividad, 
                         getMensajeEfectividad(efectividad));
        
        int psAntes = objetivo.getPs();
        boolean exito = movimiento.ejecutar(activo, objetivo, efectividad);
        
        if (exito) {
            int danio = psAntes - objetivo.getPs();
            if (danio > 0) {
                System.out.println("¡El ataque hizo " + danio + " de daño!");
                System.out.println(objetivo.getNombre() + " ahora tiene " + objetivo.getPs() + "/" + 
                                 objetivo.getPsMaximos() + " PS");
            } else {
                System.out.println("¡El ataque no hizo daño directo!");
            }
            
            // Mostrar efectos secundarios
            if (movimiento.getNombre().equals("Will-O-Wisp") && objetivo.getPs() > 0) {
                System.out.println(objetivo.getNombre() + " fue quemado.");
            }
        } else {
            System.out.println("¡El ataque falló!");
        }
        
        if (objetivo.estaDebilitado()) {
            System.out.println("¡" + objetivo.getNombre() + " fue debilitado!");
        }
    } else {
        System.out.println("¡" + activo.getNombre() + " no tiene movimientos disponibles!");
    }
}

private String getMensajeEfectividad(double efectividad) {
    if (efectividad <= 0.0) return "INEFECTIVO";
    if (efectividad >= 2.0) return "SUPEREFECTIVO";
    if (efectividad == 0.5) return "POCO EFECTIVO";
    return "NEUTRAL";
}

    /**
     * Obtiene un mensaje de efectividad basado en el multiplicador.
     * @param efectividad El multiplicador de efectividad.
     * @return El mensaje descriptivo de la efectividad.
     */
    private String obtenerMensajeEfectividad(double efectividad) {
        String mensaje = String.format("[Multiplicador: x%.1f] ", efectividad);
        if (efectividad <= 0.0) {
            mensaje += "INEFECTIVO";
        } else if (efectividad >= 2.0) {
            mensaje += "SUPEREFECTIVO";
        } else if (efectividad == 0.5) {
            mensaje += "POCO EFECTIVO";
        } else {
            mensaje += "NEUTRAL";
        }
        return mensaje;
    }

    /**
     * Punto de entrada principal de la aplicación.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        ConsolaPOOBkemon consola = new ConsolaPOOBkemon();
        consola.iniciarJuego();
    }
}