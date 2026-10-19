package SaborExpress;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Menu {
    private final List<Producto> productos = new ArrayList<>();

    public void agregar(Producto producto) { productos.add(producto); }
    public boolean eliminar(String nombre) {
        return productos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
    }
    public List<Producto> listar() { return List.copyOf(productos); }
    public List<Producto> ordenarPorPrecio() {
        return productos.stream().sorted(Comparator.comparingDouble(Producto::getPrecio)).toList();
    }
}
