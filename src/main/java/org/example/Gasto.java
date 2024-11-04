package org.example;

public class Gasto {
    private double monto;
    private String fecha;
    private String categoria;
    private String comentario;

    // Constructor
    public Gasto(double monto, String fecha, String categoria, String comentario) {
        this.monto = monto;
        this.fecha = fecha;
        this.categoria = categoria;
        this.comentario = comentario;
    }

    // Getters
    public double getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getComentario() {
        return comentario;
    }
}
