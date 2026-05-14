package com.jairs.u11.pedidos;

import java.util.Objects;

public final class Direccion {

    private final String calle;
    private final String ciudad;
    private final String codigoPostal;

    public Direccion(String calle, String ciudad, String codigoPostal) {
        this.calle = validarTexto(calle, "Calle requerida");
        this.ciudad = validarTexto(ciudad, "Ciudad requerida");
        this.codigoPostal = validarTexto(codigoPostal, "Codigo postal requerido");
    }

    private String validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direccion direccion)) {
            return false;
        }
        return Objects.equals(calle, direccion.calle)
                && Objects.equals(ciudad, direccion.ciudad)
                && Objects.equals(codigoPostal, direccion.codigoPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calle, ciudad, codigoPostal);
    }
}
