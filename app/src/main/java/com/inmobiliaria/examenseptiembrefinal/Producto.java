package com.inmobiliaria.examenseptiembrefinal;

import android.net.Uri;

public class Producto implements Comparable<Producto> {
    private int codigo;
    private String descripcion;
    private double precio;
    private String imagenUri;

    public Producto(int codigo, String descripcion, double precio, String imagenUri) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUri = imagenUri;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }

    @Override
    public int compareTo(Producto otroProducto) {
        return this.descripcion.toLowerCase().compareTo(otroProducto.getDescripcion().toLowerCase());
    }
}
