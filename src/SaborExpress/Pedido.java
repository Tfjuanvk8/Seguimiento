package SaborExpress;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final String id;
    private final Cliente cliente;
    private final List<DetallePedido> detalles = new ArrayList<>();
    private final List<ObservadorPedido> observadores = new ArrayList<>();
    private EstadoPedido estado = EstadoPedido.CREADO;
    private Repartidor repartidor;

    public Pedido(String id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        detalles.add(new DetallePedido(producto, cantidad));
    }

    public void asignarRepartidor(Repartidor repartidor) { this.repartidor = repartidor; }
    public void agregarObservador(ObservadorPedido observador) { observadores.add(observador); }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        for (ObservadorPedido observador : observadores) observador.actualizar(this);
    }

    public double calcularTotal() {
        return detalles.stream().mapToDouble(DetallePedido::getSubtotal).sum();
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<DetallePedido> getDetalles() { return List.copyOf(detalles); }
    public EstadoPedido getEstado() { return estado; }
    public Repartidor getRepartidor() { return repartidor; }
}

