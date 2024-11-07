package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorCSV {
    public static void eliminarDatosCSV(String csvGastos) {
        try (FileWriter writer = new FileWriter(csvGastos, false)) {
            writer.write("");
            System.out.println("Datos eliminados correctamente del archivo de gastos.");
        } catch (IOException e) {
            System.out.println("Error al intentar borrar los datos: " + e.getMessage());
        }
    }

    public static List<Gasto> cargarGastosDesdeCSV(String csvGastos) {
        List<Gasto> gastos = new ArrayList<>();
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    String titulo = datos[0];
                    int monto = Integer.parseInt(datos[1]);
                    String fecha = datos[2];
                    String categoria = datos[3];
                    String comentario = datos[4];
                    gastos.add(new Gasto(titulo, monto, fecha, categoria, comentario));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer los gastos: " + e.getMessage());
        }
        return gastos;
    }
}
