package com.jairs.u11.pedidos;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository repo;
    private final NotificacionService notificacionService;

    public PedidoService(PedidoRepository repo, NotificacionService notificacionService) {
        this.repo = repo;
        this.notificacionService = notificacionService;
    }

    public String procesarPedido(DatosCliente cliente, List<LineaPedido> lineas,
            String metodoPago, boolean esUrgente, CodigoDescuento descuento) {
        validarMetodoPago(metodoPago);
        double total = calcularTotal(lineas);
        double totalConDescuento = aplicarDescuento(total, descuento);
        notificacionService.notificarPedido(cliente, esUrgente);
        return persistirPedido(cliente, totalConDescuento);
    }

    private void validarMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.isBlank()) {
            throw new IllegalArgumentException("Metodo de pago requerido");
        }
    }

    private double calcularTotal(List<LineaPedido> lineas) {
        validarLineas(lineas);
        return lineas.stream()
                .mapToDouble(this::calcularSubtotal)
                .sum();
    }

    private void validarLineas(List<LineaPedido> lineas) {
        if (lineas == null || lineas.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos una linea");
        }
    }

    private double calcularSubtotal(LineaPedido linea) {
        Producto producto = buscarProducto(linea);
        return producto.getPrecio() * linea.getCantidad();
    }

    private Producto buscarProducto(LineaPedido linea) {
        Producto producto = repo.findProductoById(linea.getProductoId());
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + linea.getProductoId());
        }
        return producto;
    }

    private double aplicarDescuento(double total, CodigoDescuento descuento) {
        return descuento == null ? total : total * (1 - descuento.getPorcentaje());
    }

    private String persistirPedido(DatosCliente cliente, double total) {
        Pedido pedido = new Pedido(cliente, total);
        return "OK_" + repo.save(pedido).getId();
    }
}
