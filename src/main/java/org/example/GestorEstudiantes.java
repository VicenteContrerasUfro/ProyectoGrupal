package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class GestorEstudiantes {
    private static final String csvEstudiantes = "estudiantes.csv";

    public void guardarEstudianteEnCSV(Estudiante estudiante) {
        try (FileWriter writer = new FileWriter(csvEstudiantes, true)) {
            writer.append(estudiante.toString()).append("\n");
            System.out.println("Datos del estudiante guardados correctamente en " + csvEstudiantes);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos del estudiante: " + e.getMessage());
        }
    }
}
