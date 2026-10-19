package SaborExpress;

public class ObservadorCliente implements ObservadorPedido {
    @Override
    public void actualizar(Pedido pedido) {
        System.out.println("Aviso al cliente " + pedido.getCliente().getNombre()
                + ": pedido " + pedido.getId() + " -> " + pedido.getEstado());
    }
}
