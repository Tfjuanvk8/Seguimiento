package SaborExpress;

public class DetallePedido {
    private final Producto producto;
    private final int cantidad;

    public DetallePedido(Producto producto, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser positiva");
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getSubtotal() { return producto.getPrecio() * cantidad; }
}
