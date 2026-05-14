package com.jairs.u11.pedidos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    default Producto findProductoById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return new Producto(id, "Producto " + id, 100.0);
    }
}
