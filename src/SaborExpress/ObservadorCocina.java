package SaborExpress;

public class ObservadorCocina implements ObservadorPedido {
    @Override
    public void actualizar(Pedido pedido) {
        System.out.println("Cocina: pedido " + pedido.getId() + " -> " + pedido.getEstado());
    }
}
