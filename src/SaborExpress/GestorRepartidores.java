package SaborExpress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorRepartidores {
    private final List<Repartidor> repartidores = new ArrayList<>();

    public void agregar(Repartidor repartidor) { repartidores.add(repartidor); }
    public List<Repartidor> listar() { return List.copyOf(repartidores); }
    public Optional<Repartidor> buscarDisponible() {
        return repartidores.stream().filter(Repartidor::isDisponible).findFirst();
    }
}
