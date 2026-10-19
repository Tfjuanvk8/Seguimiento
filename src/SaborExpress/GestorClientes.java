package SaborExpress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorClientes {
    private final List<Cliente> clientes = new ArrayList<>();
    public void agregar(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> listar() {
        return List.copyOf(clientes);
    }

    public Optional<Cliente> buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }
}
