package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class GestorGastos {
    private static final String archivo_EstudiantesCSV = "estudiantes.csv";
    private static final String archivo_GastosCSV = "gastos.csv";

    public void guardarEstudianteEnCSV(String nombre, String universidad) {
        try (FileWriter writer = new FileWriter(archivo_EstudiantesCSV, true)) {
            writer.append(nombre).append(",").append(universidad).append("\n");
            System.out.println("Datos guardados correctamente en " + archivo_EstudiantesCSV);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public static void imprimirGastosCSV() {
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(archivo_GastosCSV))) {
            String linea;
            System.out.println("Historial de gastos:");
            System.out.println("----------------------------------------");
            while ((linea = lectorGastos.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void registrarGastoEstudiantil(int monto, String fecha, String categoriaGasto, String comentario) {
        try (FileWriter csvWriter = new FileWriter(archivo_GastosCSV, true)) {
            csvWriter.append(monto + ",").append(fecha + ",").append(categoriaGasto + ",").append(comentario + "\n");
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Gasto registrado exitosamente en " + archivo_GastosCSV);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }
}