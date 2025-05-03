package domain;

public abstract class Item {
    protected String nombre;
    
    public Item(String nombre) {
        this.nombre = nombre;
    }
    
    public abstract void usar(Pokemon pokemon);
    
    public String getNombre() {
        return nombre;
    }
}