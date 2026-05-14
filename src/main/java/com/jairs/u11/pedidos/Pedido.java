package com.jairs.u11.pedidos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clienteNombre;
    private String clienteEmail;
    private double total;

    protected Pedido() {
    }

    public Pedido(DatosCliente cliente, double total) {
        this.clienteNombre = cliente.getNombre();
        this.clienteEmail = cliente.getEmail();
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public double getTotal() {
        return total;
    }
}
