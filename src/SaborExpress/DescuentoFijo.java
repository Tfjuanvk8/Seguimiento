package SaborExpress;

public class DescuentoFijo implements EstrategiaDescuento {
    private final double descuento;

    public DescuentoFijo(double descuento) {
        if (descuento < 0) throw new IllegalArgumentException("Descuento inválido");
        this.descuento = descuento;
    }

    @Override
    public double aplicar(double precio) { return Math.max(0, precio - descuento); }
}