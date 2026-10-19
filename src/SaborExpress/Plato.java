package SaborExpress;

public class Plato extends Producto {
    private final String categoria;

    public Plato(String nombre, double precio, String categoria) {
        super(nombre, precio);
        this.categoria = categoria;
    }

    public String getCategoria() { return categoria; }
}
