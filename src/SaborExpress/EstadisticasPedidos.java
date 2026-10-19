package SaborExpress;

import java.util.Collection;

public class EstadisticasPedidos {
    public double totalVendido(Collection<Pedido> pedidos) {
        return pedidos.stream().mapToDouble(Pedido::calcularTotal).sum();
    }

    public long contarEntregados(Collection<Pedido> pedidos) {
        return pedidos.stream().filter(p -> p.getEstado() == pedido.EstadoPedido.ENTREGADO).count();
    }
}