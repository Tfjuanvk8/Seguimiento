package SaborExpress;

public class Cliente {
    private final int id;
    private final String documento;
    private final String nombre;
    private final String telefono;
    private final String direccion;

    public Cliente(int id, String documento, String nombre, String telefono, String direccion) {
        if (id <= 0) throw new IllegalArgumentException("El id debe ser positivo.");
        this.id = id;
        this.documento = requerido(documento, "documento");
        this.nombre = requerido(nombre, "nombre");
        this.telefono = requerido(telefono, "teléfono");
        this.direccion = requerido(direccion, "dirección");
    }

    private static String requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        return valor.trim();
    }

    public int getId() { return id; }
    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }

    @Override public String toString() { return nombre + " (" + documento + ")"; }
}
