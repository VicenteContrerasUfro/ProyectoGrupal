package org.example;

public class Estudiante {
    private String nombre;
    private String universidad;

    public Estudiante(String nombre, String universidad) {
        this.nombre = nombre;
        this.universidad = universidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUniversidad() {
        return universidad;
    }

    @Override
    public String toString() {
        return nombre + "," + universidad;
    }
}
