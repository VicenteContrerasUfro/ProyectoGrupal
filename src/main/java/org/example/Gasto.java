package org.example;

public class Gasto {
    private int monto;
    private String fecha;
    private String categoria;
    private String comentario;

    public Gasto(int monto, String fecha, String categoria, String comentario) {
        this.monto = monto;
        this.fecha = fecha;
        this.categoria = categoria;
        this.comentario = comentario;
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
        return monto + "," + fecha + "," + categoria + "," + comentario;
    }
}
