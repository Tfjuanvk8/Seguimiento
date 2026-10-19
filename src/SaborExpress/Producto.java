package SaborExpress;

public abstract class Producto {
    private final String nombre;
    private final double precio;

    public Producto(String nombre, double precio) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    @Override
    public String toString() { return nombre + " ($" + precio + ")"; }
}
