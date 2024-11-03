package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorGastos {
    private static final String csvGastos = "gastos.csv";

    public void registrarGasto(Gasto gasto) {
        try (FileWriter csvWriter = new FileWriter(csvGastos, true)) {
            csvWriter.append(gasto.toString()).append("\n");
            System.out.println("Gasto registrado exitosamente en " + csvGastos);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    public void imprimirGastos() {
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
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

    // opcion 3
    public int calcularMontoTotal() {
        int montoTotal = 0;
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                int monto = Integer.parseInt(datos[0].trim());
                montoTotal += monto;
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al procesar los gastos: " + e.getMessage());
        }
        return montoTotal;
    }

    // opcion 4
    public void buscarGastosPorCategoria(String categoria) {
        boolean encontrado = false;
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            System.out.println("Gastos en la categoría: " + categoria);
            System.out.println("----------------------------------------");
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[2].trim().equalsIgnoreCase(categoria)) {
                    System.out.println("Monto: " + datos[0] + ", Fecha: " + datos[1] + ", Comentario: " + datos[3]);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontraron gastos en la categoría especificada.");
            }
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
