package SaborExpress;

public class DescuentoPorcentaje implements EstrategiaDescuento {
    private final double porcentaje;

    public DescuentoPorcentaje(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) throw new IllegalArgumentException("Porcentaje inválido");
        this.porcentaje = porcentaje;
    }

    @Override
    public double aplicar(double precio) { return precio * (1 - porcentaje / 100.0); }
}

