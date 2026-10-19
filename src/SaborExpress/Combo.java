package SaborExpress;

import java.util.List;

public class Combo extends Producto {
    private final List<Producto> productos;

    public Combo(String nombre, double precio, List<Producto> productos) {
        super(nombre, precio);
        this.productos = List.copyOf(productos);
    }

    public List<Producto> getProductos() { return productos; }
}
