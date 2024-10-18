package com.example.telopasode1;

import java.util.ArrayList;
import java.util.List;

public class Anuncio {
    private String categoria;
    private String titulo;
    private String descripcion;
    private Double precio;
    private String fechaActualizacion;
    private ArrayList<String> imagenes;

    public Anuncio(String categoria, String titulo, String descripcion, Double precio, String fechaActualizacion, ArrayList<String> imagenes) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechaActualizacion = fechaActualizacion;
        this.imagenes = (imagenes != null) ? imagenes : new ArrayList<>();
    }

    // Getters y setters...
    public String getCategoria() {
        return categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }
}

