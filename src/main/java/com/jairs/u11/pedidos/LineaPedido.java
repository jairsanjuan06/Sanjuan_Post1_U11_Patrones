package com.jairs.u11.pedidos;

public final class LineaPedido {

    private final Long productoId;
    private final int cantidad;

    public LineaPedido(Long productoId, int cantidad) {
        if (productoId == null || productoId <= 0) {
            throw new IllegalArgumentException("Producto requerido");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor que cero");
        }
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public int getCantidad() {
        return cantidad;
    }
}
