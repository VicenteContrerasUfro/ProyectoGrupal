package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorGastos {
    private static String csvGastos = "gastos.csv";
    private int limiteGasto;


    public void registrarGasto(Gasto gasto) {
        // Verificar si el nuevo gasto supera la meta o el monto máximo
        double montoTotal = calcularMontoTotal(); // Obtener el total actual de gastos
        if (gasto.getMonto() + montoTotal > limiteGasto) {
            System.out.println("Error: El gasto de " + gasto.getMonto() + " excede el límite establecido de " + limiteGasto + ". Total actual: " + montoTotal);
            return; // Salir si el gasto excede el límite
        }

        // Intentar registrar el gasto en el archivo CSV
        try (FileWriter csvWriter = new FileWriter(csvGastos, true)) {
            csvWriter.append(gasto.getMonto() + ",")
                    .append(gasto.getFecha() + ",")
                    .append(gasto.getCategoria() + ",")
                    .append(gasto.getComentario() + "\n");
            csvWriter.flush(); // Asegúrate de que se escriban los datos
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
        int total = 0;
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                total += Integer.parseInt(datos[0]);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return total;
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
    public void buscarGastosPorFecha(String fecha) {
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            boolean encontrado = false;
            System.out.println("Gastos registrados para la fecha " + fecha + ":");
            System.out.println("----------------------------------------");
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[1].equals(fecha)) {
                    System.out.println(linea);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontraron gastos para la fecha " + fecha);
            }
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    public void establecerLimiteGasto(int limite) {
        this.limiteGasto = limite;
        System.out.println("Límite de gasto establecido en: " + limite);
    }


    private List<Gasto> obtenerGastos() {
        List<Gasto> gastos = new ArrayList<>();

        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;

            while ((linea = lectorGastos.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 4) { // Asegúrate de que haya suficientes campos
                    double monto = Double.parseDouble(campos[0].trim()); // Cambia a double
                    String fecha = campos[1].trim();
                    String categoria = campos[2].trim();
                    String comentario = campos[3].trim();

                    // Crear un nuevo objeto Gasto
                    gastos.add(new Gasto(monto, fecha, categoria, comentario));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer o parsear los datos de gastos: " + e.getMessage());
        }

        return gastos;
    }



    public void eliminarDatosCSV() {
        try (FileWriter writer = new FileWriter(csvGastos, false)) {
            writer.write(""); // Borrar contenido del archivo
            System.out.println("Datos eliminados correctamente del archivo de gastos.");
        } catch (IOException e) {
            System.out.println("Error al intentar borrar los datos: " + e.getMessage());
        }
    }

    public Map<String, Double> calcularPorcentajePorCategoria() {
        List<Gasto> gastos = cargarGastosDesdeCSV();
        Map<String, Double> totalPorCategoria = new HashMap<>();
        double totalGastos = 0.0;

        for (Gasto gasto : gastos) {
            totalGastos += gasto.getMonto();
            totalPorCategoria.put(gasto.getCategoria(), totalPorCategoria.getOrDefault(gasto.getCategoria(), 0.0) + gasto.getMonto());
        }

        Map<String, Double> porcentajePorCategoria = new HashMap<>();
        for (Map.Entry<String, Double> entry : totalPorCategoria.entrySet()) {
            String categoria = entry.getKey();
            double totalCategoria = entry.getValue();
            double porcentaje = (totalCategoria / totalGastos) * 100;
            porcentajePorCategoria.put(categoria, porcentaje);
        }

        return porcentajePorCategoria;
    }

    private List<Gasto> cargarGastosDesdeCSV() {
        List<Gasto> gastos = new ArrayList<>();
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    double monto = Double.parseDouble(datos[0]);
                    String fecha = datos[1];
                    String categoria = datos[2];
                    String comentario = datos[3];
                    gastos.add(new Gasto(monto, fecha, categoria, comentario));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer los gastos: " + e.getMessage());
        }
        return gastos;
    }







}
