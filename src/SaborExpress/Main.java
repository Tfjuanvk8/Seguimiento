package SaborExpress;

public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("C001", "Ana Pérez", "3001234567", "Calle 10");
        Plato plato = new Plato("Hamburguesa", 18000, "Principal");
        Bebida bebida = new Bebida("Limonada", 5000, 350);

        Pedido pedido = new Pedido("P001", cliente);
        pedido.agregarProducto(plato, 2);
        pedido.agregarProducto(bebida, 2);
        pedido.agregarObservador(new ObservadorCliente());
        pedido.agregarObservador(new ObservadorCocina());

        System.out.println("Total: $" + pedido.calcularTotal());
        pedido.cambiarEstado(EstadoPedido.EN_PREPARACION);
        pedido.cambiarEstado(EstadoPedido.LISTO);
    }
}
