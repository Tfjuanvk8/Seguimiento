package SaborExpress;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GestorPedidos {
    private final List<Pedido> pedidos = new ArrayList<>();

    public void agregar(Pedido pedido) { pedidos.add(pedido); }
    public List<Pedido> listar() { return List.copyOf(pedidos); }
    public Optional<Pedido> buscarPorId(String id) {
        return pedidos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
    public List<Pedido> ordenarPorTotal() {
        return pedidos.stream().sorted(Comparator.comparingDouble(Pedido::calcularTotal)).toList();
    }
}