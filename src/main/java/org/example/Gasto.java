package org.example;

public class Gasto {
    private String titulo;
    private int monto;
    private String fecha;
    private String categoria;
    private String comentario;

    public Gasto(String titulo, int monto, String fecha, String categoria, String comentario) {
        this.titulo = titulo;
        this.monto = monto;
        this.fecha = fecha;
        this.categoria = categoria;
        this.comentario = comentario;
    }

    // Métodos getters
    public String getTitulo() {
        return titulo;
    }

    public int getMonto() {
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

    @Override
    public String toString() {
        return "Titulo: " + getTitulo() + "Monto: " + getMonto() + ", Fecha: " + getFecha() + ", Categoría: " + getCategoria() + ", Detalle: " + getComentario();
    }
}
