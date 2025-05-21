package domain;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import presentation.VentanaBatalla;

public class Batalla {
    private final Entrenador entrenador1;
    private final Entrenador entrenador2;
    private final VentanaBatalla ventanaBatalla;
    private boolean turnoJugador1;

    public Batalla(Entrenador entrenador1, Entrenador entrenador2, VentanaBatalla ventanaBatalla) {
        this.entrenador1 = entrenador1;
        this.entrenador2 = entrenador2;
        this.ventanaBatalla = ventanaBatalla;
        // Determinar aleatoriamente quién comienza primero
        this.turnoJugador1 = new Random().nextBoolean();
    }

    public void iniciarBatalla() {
        ventanaBatalla.mostrarMensaje("¡Comienza la batalla entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre() + "!");
        ventanaBatalla.mostrarMensaje("El turno inicial es para: " + (turnoJugador1 ? entrenador1.getNombre() : entrenador2.getNombre()));
        enviarPrimerPokemon(entrenador1);
        enviarPrimerPokemon(entrenador2);
        ventanaBatalla.cargarPokemonesIniciales();
        ventanaBatalla.actualizarVistaJugador();
    }
    
    public boolean isBatallaTerminada() {
        return this.batallaTerminada();
    }
    

    private void enviarPrimerPokemon(Entrenador entrenador) {
        if (entrenador.getEquipoPokemon().isEmpty()) {
            throw new IllegalStateException("El entrenador " + entrenador.getNombre() + " no tiene Pokémon en su equipo");
        }
        
        Pokemon activo = entrenador.getEquipoPokemon().get(0);
        entrenador.setPokemonActivo(activo);
        ventanaBatalla.mostrarMensaje("¡" + entrenador.getNombre() + " envía a " + activo.getNombre() + "!");
    }

    public void ejecutarTurno(int opcion) {
        Entrenador atacante = turnoJugador1 ? entrenador1 : entrenador2;
        Entrenador defensor = turnoJugador1 ? entrenador2 : entrenador1;

        switch (opcion) {
            case 1 -> atacar(atacante, defensor);
            case 2 -> usarItem(atacante);
            case 3 -> cambiarPokemon(atacante);
            default -> ventanaBatalla.mostrarMensaje("Opción inválida. Pierdes tu turno.");
        }

        if (!batallaTerminada()) {
            cambiarTurno();
        }
    }

    private void atacar(Entrenador atacante, Entrenador defensor) {
        Pokemon pokemonAtacante = atacante.getPokemonActivo();
        Pokemon objetivo = defensor.getPokemonActivo();

        if (!validarPokemonActivo(pokemonAtacante, objetivo)) {
            return;
        }

        Movimiento movimiento = obtenerMovimiento(pokemonAtacante);
        if (movimiento == null) {
            return;
        }

        ejecutarMovimiento(movimiento, pokemonAtacante, objetivo);
        verificarEstadoBatalla(defensor);
    }

    private boolean validarPokemonActivo(Pokemon atacante, Pokemon objetivo) {
        if (atacante == null || atacante.estaDebilitado()) {
            ventanaBatalla.mostrarMensaje("No tienes un Pokémon activo para atacar.");
            return false;
        }
        if (objetivo == null || objetivo.estaDebilitado()) {
            ventanaBatalla.mostrarMensaje("El oponente no tiene un Pokémon activo.");
            return false;
        }
        return true;
    }

    private Movimiento obtenerMovimiento(Pokemon pokemon) {
        List<Movimiento> movimientos = pokemon.getMovimientos().stream()
                .filter(m -> m.getPp() > 0)
                .collect(Collectors.toList());

        if (movimientos.isEmpty()) {
            ventanaBatalla.mostrarMensaje("No hay movimientos disponibles.");
            return null;
        }
        
        // Devolver el primer movimiento disponible (la selección se hará en la interfaz)
        return movimientos.get(0);
    }

    private void ejecutarMovimiento(Movimiento movimiento, Pokemon atacante, Pokemon objetivo) {
        ventanaBatalla.mostrarMensaje("¡" + atacante.getNombre() + " usa " + movimiento.getNombre() + "!");
        double efectividad = calcularEfectividad(movimiento, objetivo);
        String mensajeEfectividad = obtenerMensajeEfectividad(efectividad);
        ventanaBatalla.agregarMensaje(mensajeEfectividad); // Usamos agregarMensaje en lugar de mostrarMensaje
        int psAntes = objetivo.getPs();
        boolean exito = movimiento.ejecutar(atacante, objetivo, efectividad);
        if (exito) {
            int danio = psAntes - objetivo.getPs();
            ventanaBatalla.agregarMensaje("¡El ataque hizo " + danio + " de daño!");
            ventanaBatalla.actualizarVidaPokemon1(entrenador1.getPokemonActivo().getPs());
            ventanaBatalla.actualizarVidaPokemon2(entrenador2.getPokemonActivo().getPs());
        } else {
            ventanaBatalla.agregarMensaje("¡El ataque falló!");
        }
    }
    
    private String obtenerMensajeEfectividad(double efectividad) {
        String mensaje = String.format("[Multiplicador: x%.1f]%n", efectividad);
        if (efectividad <= 0.0) {
            mensaje += "INEFECTIVO";
        } else if (efectividad >= 2.0) {
            mensaje += "SUPEREFECTIVO";
        } else if (efectividad == 0.5) {
            mensaje += "EFECTIVO";
        } else {
            mensaje += "NEUTRAL";
        }
        return mensaje;
    }

    private double calcularEfectividad(Movimiento movimiento, Pokemon objetivo) {
        String tipoAtaque = movimiento.getTipo().toLowerCase().replace("é", "e");
        String tipoDefensa = objetivo.getTipoPrincipal().toLowerCase().replace("é", "e");
        double efectividad = Efectividad.calcular(tipoAtaque, tipoDefensa);

        if (objetivo.getTipoSecundario() != null && !objetivo.getTipoSecundario().isEmpty()) {
            String tipoSecundario = objetivo.getTipoSecundario().toLowerCase().replace("é", "e");
            efectividad *= Efectividad.calcular(tipoAtaque, tipoSecundario);
        }
        return efectividad;
    }

    private void verificarEstadoBatalla(Entrenador defensor) {
        Pokemon objetivo = defensor.getPokemonActivo();
        if (objetivo.estaDebilitado()) {
            ventanaBatalla.mostrarMensaje("¡" + objetivo.getNombre() + " fue debilitado!");
            if (!equipoDebilitado(defensor)) {
                ventanaBatalla.mostrarVentanaCambioObligatorio(defensor);
            }
        }
    }

    private void usarItem(Entrenador entrenador) {
        if (entrenador.getMochilaItems().isEmpty()) {
            ventanaBatalla.mostrarMensaje("No tienes items en tu mochila.");
            return;
        }
        ventanaBatalla.mostrarVentanaItem();
    }
    
    public Pokemon getPokemonActivo(Entrenador entrenador) {
        if (!entrenador.equals(entrenador1) && !entrenador.equals(entrenador2)) {
            throw new IllegalArgumentException("El entrenador no está en esta batalla.");
        }
        return entrenador.getPokemonActivo();
    }

    private void cambiarPokemon(Entrenador entrenador) {
        if (getPokemonsDisponiblesParaCambio(entrenador).isEmpty()) {
            ventanaBatalla.mostrarMensaje("No tienes otros Pokémon disponibles para cambiar.");
            return;
        }
        ventanaBatalla.mostrarVentanaCambioPokemon();
    }

    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        ventanaBatalla.actualizarVistaJugador();
    }

    public boolean batallaTerminada() {
        boolean terminada = equipoDebilitado(entrenador1) || equipoDebilitado(entrenador2);
        if (terminada) {
            determinarGanador();
        }
        return terminada;
    }

    private boolean equipoDebilitado(Entrenador entrenador) {
        return entrenador.getEquipoPokemon().stream()
                .allMatch(Pokemon::estaDebilitado);
    }

    private void determinarGanador() {
        Entrenador ganador = equipoDebilitado(entrenador1) ? entrenador2 : entrenador1;
        ventanaBatalla.mostrarMensaje("\n¡" + ganador.getNombre() + " gana la batalla!");
    }
    
    public Entrenador getGanador() {
        return this.isBatallaTerminada() ?
               (this.equipoDebilitado(entrenador1) ? entrenador2 : entrenador1) :
               null;
    }

    // Métodos de acceso para la interfaz
    public List<Movimiento> getMovimientosDisponibles(Pokemon pokemon) {
        return pokemon.getMovimientos().stream()
                .filter(m -> m.getPp() > 0)
                .collect(Collectors.toList());
    }

    public List<Pokemon> getPokemonsDisponiblesParaCambio(Entrenador entrenador) {
        return entrenador.getEquipoPokemon().stream()
                .filter(p -> !p.equals(entrenador.getPokemonActivo()))
                .filter(p -> !p.estaDebilitado())
                .collect(Collectors.toList());
    }

    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    public boolean isTurnoJugador1() {
        return turnoJugador1;
    }
}