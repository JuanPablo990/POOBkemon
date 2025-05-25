package presentation;

import domain.*;
import domain.items.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ConsolaPOOBkemon {
    private POOBkemon juego;
    private Scanner scanner;
    private Random random;
    private Machine maquina;

    public ConsolaPOOBkemon() {
        this.juego = new POOBkemon();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void iniciarJuego() {
        System.out.println("¡Bienvenido a POOBkemon!");
        System.out.println("1. Jugador vs Jugador");
        System.out.println("2. Jugador vs Máquina");
        System.out.println("3. Máquina vs Máquina");
        System.out.println("4. Salir");
        System.out.print("Seleccione un modo de juego: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

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

    private void iniciarPvP() {
        System.out.print("Nombre del Jugador 1: ");
        String nombreJ1 = scanner.nextLine();
        System.out.print("Nombre del Jugador 2: ");
        String nombreJ2 = scanner.nextLine();

        Entrenador jugador1 = juego.crearEntrenador(nombreJ1);
        Entrenador jugador2 = juego.crearEntrenador(nombreJ2);

        // Configurar equipos aleatorios
        juego.generarEquipoAleatorioCompleto(jugador1);
        juego.generarEquipoAleatorioCompleto(jugador2);

        // Asignar movimientos aleatorios
        asignarMovimientosAleatorios(jugador1);
        asignarMovimientosAleatorios(jugador2);

        // Dar items aleatorios
        jugador1.darItemsAleatorios();
        jugador2.darItemsAleatorios();

        juego.setJugadores(jugador1, jugador2);
        juego.prepararBatalla();
        iniciarBatalla();
    }

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

        // Configurar equipos aleatorios
        juego.generarEquipoAleatorioCompleto(jugador);
        maquina.seleccionarEquipo();

        // Asignar movimientos aleatorios al jugador
        asignarMovimientosAleatorios(jugador);
        maquina.seleccionarMovimientos();

        // Dar items aleatorios al jugador
        jugador.darItemsAleatorios();
        maquina.seleccionarItems();

        juego.setJugadores(jugador, maquina.getEntrenador());
        juego.prepararBatalla();
        iniciarBatalla();
    }

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

        // Configurar equipos y movimientos
        maquina1.seleccionarEquipo();
        maquina2.seleccionarEquipo();
        maquina1.seleccionarMovimientos();
        maquina2.seleccionarMovimientos();
        maquina1.seleccionarItems();
        maquina2.seleccionarItems();

        juego.setJugadores(maquina1.getEntrenador(), maquina2.getEntrenador());
        juego.prepararBatalla();
        iniciarBatallaAutomatica();
    }

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

    private void iniciarBatalla() {
        Batalla batalla = juego.getBatallaActual();
        System.out.println("\n¡Comienza la batalla entre " + 
            batalla.getEntrenador1().getNombre() + " y " + 
            batalla.getEntrenador2().getNombre() + "!");
        
        while (!juego.isBatallaTerminada()) {
            Entrenador entrenadorEnTurno = juego.getEntrenadorEnTurno();
            System.out.println("\n--- Turno de " + entrenadorEnTurno.getNombre() + " ---");
            
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
        
        Entrenador ganador = juego.getGanador();
        System.out.println("\n¡" + ganador.getNombre() + " ha ganado la batalla!");
    }

    private void iniciarBatallaAutomatica() {
        Batalla batalla = juego.getBatallaActual();
        System.out.println("\n¡Comienza la batalla entre " + 
            batalla.getEntrenador1().getNombre() + " y " + 
            batalla.getEntrenador2().getNombre() + "!");
        
        while (!juego.isBatallaTerminada()) {
            Entrenador entrenadorEnTurno = juego.getEntrenadorEnTurno();
            System.out.println("\n--- Turno de " + entrenadorEnTurno.getNombre() + " ---");
            
            turnoMaquina(entrenadorEnTurno);
            juego.cambiarTurno();
            
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        Entrenador ganador = juego.getGanador();
        System.out.println("\n¡" + ganador.getNombre() + " ha ganado la batalla!");
    }

    private void turnoJugador(Entrenador entrenador) {
        Pokemon activo = juego.getPokemonActivo(entrenador);
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

    private void turnoMaquina(Entrenador entrenador) {
        Pokemon activo = juego.getPokemonActivo(entrenador);
        System.out.println("Pokémon activo: " + activo.getNombre() + 
                          " (PS: " + activo.getPs() + "/" + activo.getPsMaximos() + ")");
        
        // La máquina toma su decisión automáticamente
        juego.getBatallaActual().ejecutarTurno(1);
    }

    public static void main(String[] args) {
        ConsolaPOOBkemon consola = new ConsolaPOOBkemon();
        consola.iniciarJuego();
    }
}