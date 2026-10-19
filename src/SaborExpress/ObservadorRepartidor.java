package SaborExpress;

public class ObservadorRepartidor implements ObservadorPedido {
    @Override
    public void actualizar(Pedido pedido) {
        System.out.println("Repartidor: pedido " + pedido.getId() + " -> " + pedido.getEstado());
    }
}