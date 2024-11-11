package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.InicioGUI.frame;

public class GestorCSV {
    static void abrirArchivoCSV() {
        try {
            File archivoCSV = new File("gastos.csv");
            if (archivoCSV.exists()) {
                Desktop.getDesktop().open(archivoCSV);
            } else {
                JOptionPane.showMessageDialog(frame, "El archivo CSV no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "No se pudo abrir el archivo CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
                if (datos.length == 5) {
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

    public static void guardarGastoCSV(String csvGastos, Gasto gasto) {

    }
}
