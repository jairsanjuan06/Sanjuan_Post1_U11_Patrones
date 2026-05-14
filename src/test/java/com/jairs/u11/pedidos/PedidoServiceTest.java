package com.jairs.u11.pedidos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @Test
    void procesaPedidoValido() {
        String resultado = pedidoService.procesarPedido(
                clienteValido(),
                List.of(new LineaPedido(1L, 1), new LineaPedido(2L, 2)),
                "TARJETA",
                false,
                CodigoDescuento.VIP10);

        assertThat(resultado).startsWith("OK_");
    }

    @Test
    void rechazaClienteInvalido() {
        assertThatThrownBy(() -> new DatosCliente(
                "",
                "correo-invalido",
                "3001112233",
                new Direccion("Calle 1", "Bogota", "110111")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nombre requerido");
    }

    @Test
    void rechazaPedidoSinLineas() {
        assertThatThrownBy(() -> pedidoService.procesarPedido(
                clienteValido(),
                List.of(),
                "TARJETA",
                false,
                null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El pedido debe tener al menos una linea");
    }

    private DatosCliente clienteValido() {
        return new DatosCliente(
                "Ana Perez",
                "ana@example.com",
                "3001112233",
                new Direccion("Calle 1", "Bogota", "110111"));
    }
}
