package domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final String LOG_FILE = "poobkemon.log";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private Log() {
    }
    
    public static void registrarEvento(String evento) {
        String timestamp = DATE_FORMAT.format(new Date());
        String logEntry = "[" + timestamp + "] " + evento;
        System.out.println(logEntry);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de log: " + e.getMessage());
        }
    }
    
    public static void logBatallaIniciada(Entrenador entrenador1, Entrenador entrenador2) {
        registrarEvento("Batalla iniciada entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre());
    }
    
    public static void logBatallaTerminada(Entrenador ganador) {
        registrarEvento("Batalla terminada. Ganador: " + ganador.getNombre());
    }
    
    public static void logMovimiento(Pokemon atacante, Pokemon objetivo, Movimiento movimiento, double efectividad) {
        String efectividadStr = "";
        if (efectividad >= 2.0) {
            efectividadStr = " (Superefectivo)";
        } else if (efectividad <= 0.0) {
            efectividadStr = " (Inefectivo)";
        } else if (efectividad == 0.5) {
            efectividadStr = " (Poco efectivo)";
        }
        
        registrarEvento(atacante.getNombre() + " usa " + movimiento.getNombre() + 
                       " contra " + objetivo.getNombre() + efectividadStr);
    }
    
    public static void logCambioPokemon(Entrenador entrenador, Pokemon pokemon) {
        registrarEvento(entrenador.getNombre() + " cambia a " + pokemon.getNombre());
    }
    
    public static void logUsoItem(Entrenador entrenador, String item, Pokemon pokemon) {
        registrarEvento(entrenador.getNombre() + " usa " + item + " en " + pokemon.getNombre());
    }
    
    public static void logPokemonDebilitado(Pokemon pokemon) {
        registrarEvento(pokemon.getNombre() + " ha sido debilitado");
    }
    
    public static void logError(String error) {
        registrarEvento("ERROR: " + error);
    }
}