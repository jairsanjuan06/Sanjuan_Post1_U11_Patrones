package com.jairs.u11.pedidos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionService.class);

    public void notificarPedido(DatosCliente cliente, boolean urgente) {
        LOGGER.info("Enviando confirmacion de pedido a {}. Urgente: {}",
                cliente.getEmail(), urgente);
    }
}
