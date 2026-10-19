package SaborExpress;

public class Repartidor {
    private final String id;
    private final String nombre;
    private boolean disponible = true;

    public Repartidor(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
