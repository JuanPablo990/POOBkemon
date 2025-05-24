package domain;

public class POOBkemonException extends Exception {
    public static final String POKEMON_DEBILITADO = "El Pokémon está debilitado y no puede realizar acciones";
    public static final String MOVIMIENTO_NO_DISPONIBLE = "No hay movimientos disponibles con PP restante";
    public static final String EQUIPO_DEBILITADO = "Todos los Pokémon del equipo están debilitados";
    public static final String POKEMON_NO_ENCONTRADO = "Pokémon no encontrado en el equipo";
    public static final String MOVIMIENTO_NO_COMPATIBLE = "El movimiento no es compatible con este Pokémon";
    public static final String LIMITE_MOVIMIENTOS = "Un Pokémon no puede tener más de 4 movimientos";
    public static final String ITEM_NO_DISPONIBLE = "No hay items disponibles en la mochila";
    public static final String BATALLA_NO_INICIADA = "No hay una batalla en curso";
    public static final String ENTRENADOR_NO_VALIDO = "El entrenador no es válido para esta acción";
    public static final String POKEMON_NO_ACTIVO = "No hay un Pokémon activo para realizar la acción";
    public static final String OPCION_INVALIDA = "Opción inválida seleccionada";
    public static final String TURNO_INCORRECTO = "No es el turno de este jugador";
    public static final String DATOS_INVALIDOS = "Datos inválidos proporcionados";

    public POOBkemonException(String message) {
        super(message);
    }

    public POOBkemonException(String message, Throwable cause) {
        super(message, cause);
    }
}