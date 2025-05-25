package domain;

    /**
	 * Clase abstracta que representa un objeto en el juego.
	 * Los objetos pueden ser utilizados por los Pokémon para obtener efectos específicos.
	 */
public abstract class Item {
    protected String nombre;
    
    /**
     * Constructor de la clase Item.
     * @param nombre
     */
    public Item(String nombre) {
        this.nombre = nombre;
    }
    
    /**
	 * Método abstracto que define el comportamiento de uso del objeto.
	 * @param pokemon El Pokémon que usará el objeto.
	 */
    public abstract void usar(Pokemon pokemon);
    
    /**
     * Método que devuelve el nombre del objeto.
     * @return
     */
    public String getNombre() {
        return nombre;
    }
}