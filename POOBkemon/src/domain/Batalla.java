package domain;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.List;

public class Batalla {
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Scanner scanner;

    public Batalla(Entrenador entrenador1, Entrenador entrenador2) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.scanner = new Scanner(System.in);
    }

    public void iniciarBatalla() {
        System.out.println("¡Comienza la batalla entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre() + "!");

        // Enviar primer Pokémon de cada entrenador
        enviarPrimerPokemon(entrenador1);
        enviarPrimerPokemon(entrenador2);

        while (!batallaTerminada()) {
            System.out.println("\n=== NUEVO TURNO ===");
            mostrarEstadoCombate();

            // Turno del jugador 1
            System.out.println("\n=== TURNO DE " + entrenador1.getNombre() + " ===");
            ejecutarTurno(entrenador1, entrenador2);
            
            if (!batallaTerminada()) {
                // Turno del jugador 2
                System.out.println("\n=== TURNO DE " + entrenador2.getNombre() + " ===");
                ejecutarTurno(entrenador2, entrenador1);
            }
        }

        determinarGanador();
        scanner.close();
    }

    private void enviarPrimerPokemon(Entrenador entrenador) {
        Pokemon activo = entrenador.getEquipoPokemon().get(0);
        System.out.println("\n¡" + entrenador.getNombre() + " envía a " + activo.getNombre() + "!");
        entrenador.setPokemonActivo(activo);
    }

    private void ejecutarTurno(Entrenador atacante, Entrenador defensor) {
        System.out.println("\nOpciones:");
        System.out.println("1. Atacar");
        System.out.println("2. Usar item");
        System.out.println("3. Cambiar Pokémon");
        System.out.print("Elige una opción: ");
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                atacar(atacante, defensor);
                break;
            case 2:
                usarItem(atacante);
                break;
            case 3:
                cambiarPokemon(atacante);
                break;
            default:
                System.out.println("Opción inválida. Pierdes tu turno.");
        }
    }

    private void atacar(Entrenador atacante, Entrenador defensor) {
        Pokemon pokemonAtacante = atacante.getPokemonActivo();
        if (pokemonAtacante == null || pokemonAtacante.estaDebilitado()) {
            System.out.println("No tienes un Pokémon activo para atacar.");
            return;
        }
        Movimiento movimiento = seleccionarMovimiento(pokemonAtacante);
        if (movimiento == null) {
            System.out.println("No hay movimientos disponibles.");
            return;
        }
        Pokemon objetivo = defensor.getPokemonActivo();
        if (objetivo == null || objetivo.estaDebilitado()) {
            System.out.println("El oponente no tiene un Pokémon activo.");
            return;
        }
        String tipoAtaque = movimiento.getTipo().toLowerCase().replace("é", "e");
        String tipoPrincipalDefensa = objetivo.getTipoPrincipal().toLowerCase().replace("é", "e");
        double efectividad = Efectividad.calcular(tipoAtaque, tipoPrincipalDefensa);
        if (objetivo.getTipoSecundario() != null && !objetivo.getTipoSecundario().isEmpty()) {
            String tipoSecundarioDefensa = objetivo.getTipoSecundario().toLowerCase().replace("é", "e");
            efectividad *= Efectividad.calcular(tipoAtaque, tipoSecundarioDefensa);
        }
        System.out.println("\n¡" + pokemonAtacante.getNombre() + " usa " + movimiento.getNombre() + "!");
        mostrarEfectividadTipo(efectividad);
        boolean exito = movimiento.ejecutar(pokemonAtacante, objetivo, efectividad);
        if (exito) {
            System.out.println("\nResultado del ataque:");
            System.out.println(pokemonAtacante.getNombre() + " - PS: " + pokemonAtacante.getPs() + "/" + pokemonAtacante.getPsMaximos());
            System.out.println(objetivo.getNombre() + " - PS: " + objetivo.getPs() + "/" + objetivo.getPsMaximos());
            
            if (objetivo.estaDebilitado()) {
                System.out.println("\n¡" + objetivo.getNombre() + " fue debilitado!");
                if (!equipoDebilitado(defensor)) {
                    cambiarPokemonObligatorio(defensor);
                }
            }
        }
    }

    private void mostrarEfectividadTipo(double efectividad) {
        System.out.printf("[Multiplicador: x%.1f]%n", efectividad);
        if (efectividad <= 0.0) {
            System.out.println("INEFECTIVO");
        } 
        else if (efectividad >= 2.0) {
            System.out.println("SUPEREFECTIVO");
        } 
        else if (efectividad == 0.5) {
            System.out.println("EFECTIVO");
        } 
        else {
            System.out.println("NEUTRAL");
        }   
    }

    private void cambiarPokemon(Entrenador entrenador) {
        List<Pokemon> equipo = entrenador.getEquipoPokemon();
        Pokemon actual = entrenador.getPokemonActivo();
        
        System.out.println("\nPokémon disponibles:");
        int opcion = 1;
        for (Pokemon pokemon : equipo) {
            if (pokemon != actual && !pokemon.estaDebilitado()) {
                System.out.println(opcion + ". " + pokemon.getNombre() + 
                                 " (PS: " + pokemon.getPs() + "/" + pokemon.getPsMaximos() + ")");
                opcion++;
            }
        }
        
        if (opcion == 1) {
            System.out.println("No tienes otros Pokémon disponibles para cambiar.");
            return;
        }
        
        System.out.print("Elige un Pokémon para cambiar: ");
        int indice = scanner.nextInt() - 1;
        
        // Validar selección
        if (indice >= 0 && indice < equipo.size()) {
            Pokemon nuevo = null;
            int contador = 0;
            for (Pokemon pokemon : equipo) {
                if (pokemon != actual && !pokemon.estaDebilitado()) {
                    if (contador == indice) {
                        nuevo = pokemon;
                        break;
                    }
                    contador++;
                }
            }
            
            if (nuevo != null) {
                System.out.println("\n¡" + entrenador.getNombre() + " retira a " + actual.getNombre() + 
                                   " y envía a " + nuevo.getNombre() + "!");
                entrenador.setPokemonActivo(nuevo);
            }
        } else {
            System.out.println("Selección inválida. No se realizó el cambio.");
        }
    }

    private void cambiarPokemonObligatorio(Entrenador entrenador) {
        List<Pokemon> equipo = entrenador.getEquipoPokemon();
        System.out.println("\n¡" + entrenador.getNombre() + " debe enviar un nuevo Pokémon!");
        
        for (Pokemon pokemon : equipo) {
            if (!pokemon.estaDebilitado() && pokemon != entrenador.getPokemonActivo()) {
                System.out.println("¡" + entrenador.getNombre() + " envía a " + pokemon.getNombre() + "!");
                entrenador.setPokemonActivo(pokemon);
                return;
            }
        }
        
        System.out.println("¡No hay Pokémon disponibles para cambiar!");
    }

            private void usarItem(Entrenador entrenador) {
        List<Item> items = entrenador.getMochilaItems();
        
        if (items.isEmpty()) {
            System.out.println("\nNo tienes items en tu mochila.");
            return;
        }
    
        System.out.println("\nItems disponibles (elige 0 para cancelar):");
        System.out.println("0. No usar item");
        
        // Mostrar items únicos (aunque estén repetidos)
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getNombre());
        }
    
        System.out.print("\nElige un item (0 para cancelar): ");
        int indiceItem = scanner.nextInt();
    
        // Opción para cancelar
        if (indiceItem == 0) {
            System.out.println("Decides no usar ningún item.");
            return;
        }
    
        // Validar selección
        if (indiceItem < 1 || indiceItem > items.size()) {
            System.out.println("Índice inválido.");
            return;
        }
    
        Item itemSeleccionado = items.get(indiceItem - 1);
        
        // Seleccionar Pokémon objetivo
        System.out.println("\nSelecciona el Pokémon objetivo:");
        List<Pokemon> equipo = entrenador.getEquipoPokemon();
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon p = equipo.get(i);
            System.out.println((i + 1) + ". " + p.getNombre() + 
                             " (PS: " + p.getPs() + "/" + p.getPsMaximos() + ")" +
                             (p.estaDebilitado() ? " - DEBILITADO" : ""));
        }
        
        System.out.print("Elige un Pokémon (0 para cancelar): ");
        int indicePokemon = scanner.nextInt();
    
        if (indicePokemon == 0) {
            System.out.println("Decides no usar el item.");
            return;
        }
    
        if (indicePokemon < 1 || indicePokemon > equipo.size()) {
            System.out.println("Índice inválido.");
            return;
        }
    
        Pokemon pokemonObjetivo = equipo.get(indicePokemon - 1);
        
        // Aplicar el item
        System.out.println("\nEstado antes:");
        System.out.println(pokemonObjetivo.getNombre() + " - PS: " + 
                          pokemonObjetivo.getPs() + "/" + pokemonObjetivo.getPsMaximos());
    
        // Usar el item
        itemSeleccionado.usar(pokemonObjetivo);
        // Remover el item de la mochila después de usarlo
        entrenador.getMochilaItems().remove(itemSeleccionado);
        
        System.out.println("\nEstado después:");
        System.out.println(pokemonObjetivo.getNombre() + " - PS: " + 
                          pokemonObjetivo.getPs() + "/" + pokemonObjetivo.getPsMaximos());
    }

    public boolean batallaTerminada() {
        return equipoDebilitado(entrenador1) || equipoDebilitado(entrenador2);
    }

    private boolean equipoDebilitado(Entrenador entrenador) {
        for (Pokemon pokemon : entrenador.getEquipoPokemon()) {
            if (!pokemon.estaDebilitado()) {
                return false;
            }
        }
        return true;
    }

    private void mostrarEstadoCombate() {
        System.out.println("\n=== ESTADO DEL COMBATE ===");
        
        System.out.println("\n" + entrenador1.getNombre() + ":");
        Pokemon activo1 = entrenador1.getPokemonActivo();
        System.out.println("- " + activo1.getNombre() + ": " + 
                         (activo1.estaDebilitado() ? "DEBILITADO" : 
                          "PS: " + activo1.getPs() + "/" + activo1.getPsMaximos()));
        
        System.out.println("\n" + entrenador2.getNombre() + ":");
        Pokemon activo2 = entrenador2.getPokemonActivo();
        System.out.println("- " + activo2.getNombre() + ": " + 
                         (activo2.estaDebilitado() ? "DEBILITADO" : 
                          "PS: " + activo2.getPs() + "/" + activo2.getPsMaximos()));
    }

    private Movimiento seleccionarMovimiento(Pokemon pokemon) {
        List<Movimiento> movimientos = pokemon.getMovimientos();
        System.out.println("\nMovimientos de " + pokemon.getNombre() + ":");

        for (int i = 0; i < movimientos.size(); i++) {
            Movimiento movimiento = movimientos.get(i);
            System.out.println((i + 1) + ". " + movimiento.getNombre() + 
                             " (PP: " + movimiento.getPp() + "/" + movimiento.getPpMaximos() + 
                             ") - Tipo: " + movimiento.getTipo());
        }

        System.out.print("Elige un movimiento: ");
        int indice = scanner.nextInt() - 1;

        if (indice < 0 || indice >= movimientos.size() || movimientos.get(indice).getPp() <= 0) {
            return null;
        }
        return movimientos.get(indice);
    }

    private void determinarGanador() {
        Entrenador ganador = equipoDebilitado(entrenador1) ? entrenador2 : entrenador1;
        System.out.println("\n¡" + ganador.getNombre() + " gana la batalla!");
    }
    
 // --- Getters para la fachada ---

    /**
     * Obtiene el primer entrenador en la batalla.
     */
    public Entrenador getEntrenador1() {
        return this.entrenador1;
    }

    /**
     * Obtiene el segundo entrenador en la batalla.
     */
    public Entrenador getEntrenador2() {
        return this.entrenador2;
    }

    /**
     * Indica si la batalla ha terminado (un entrenador no tiene Pokémon disponibles).
     */
    public boolean isBatallaTerminada() {
        return this.batallaTerminada();
    }

    /**
     * Devuelve el entrenador ganador (null si la batalla no ha terminado).
     */
    public Entrenador getGanador() {
        return this.isBatallaTerminada() ? 
               (this.equipoDebilitado(entrenador1) ? entrenador2 : entrenador1) : 
               null;
    }

    /**
     * Obtiene el Pokémon activo de un entrenador.
     */
    public Pokemon getPokemonActivo(Entrenador entrenador) {
        if (!entrenador.equals(entrenador1) && !entrenador.equals(entrenador2)) {
            throw new IllegalArgumentException("El entrenador no está en esta batalla.");
        }
        return entrenador.getPokemonActivo();
    }

    /**
     * Devuelve los movimientos disponibles (con PP > 0) de un Pokémon.
     */
    public List<Movimiento> getMovimientosDisponibles(Pokemon pokemon) {
        return pokemon.getMovimientos().stream()
               .filter(m -> m.getPp() > 0)
               .collect(Collectors.toList());
    }

    /**
     * Obtiene las opciones disponibles para el turno actual de un entrenador.
     * Ejemplo: ["ATACAR", "CAMBIAR_POKEMON", "USAR_ITEM"].
     */
    public List<String> getOpcionesTurno(Entrenador entrenador) {
        List<String> opciones = new ArrayList<>();
        opciones.add("ATACAR");
        
        // Verificar si hay Pokémon para cambiar
        boolean puedeCambiar = entrenador.getEquipoPokemon().stream()
                              .filter(p -> !p.equals(entrenador.getPokemonActivo()))
                              .anyMatch(p -> !p.estaDebilitado());
        if (puedeCambiar) {
            opciones.add("CAMBIAR_POKEMON");
        }
        
        // Verificar si hay ítems disponibles
        if (!entrenador.getMochilaItems().isEmpty()) {
            opciones.add("USAR_ITEM");
        }
        
        return opciones;
    }

    /**
     * Obtiene los Pokémon vivos y no activos de un entrenador (para cambios).
     */
    public List<Pokemon> getPokemonsDisponiblesParaCambio(Entrenador entrenador) {
        return entrenador.getEquipoPokemon().stream()
               .filter(p -> !p.equals(entrenador.getPokemonActivo()) && !p.estaDebilitado())
               .collect(Collectors.toList());
    }
    
}