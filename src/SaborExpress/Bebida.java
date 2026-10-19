package SaborExpress;

public class Bebida extends Producto {
    private final int mililitros;

    public Bebida(String nombre, double precio, int mililitros) {
        super(nombre, precio);
        if (mililitros <= 0) throw new IllegalArgumentException("Los mililitros deben ser positivos");
        this.mililitros = mililitros;
    }

    public int getMililitros() { return mililitros; }
}
