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


    public void registrarGastoEstudiantil(double monto, String fecha, String categoriaGasto, String comentario) {
        //ver si supera la meta o monto max
        if (gasto.getMonto() + calcularMontoTotal() > limiteGasto) {
            System.out.println("Error: El gasto excede el límite establecido de " + limiteGasto);
            return;
        }

        try (FileWriter csvWriter = new FileWriter(csvGastos, true)) {
            // Asegúrate de que estás escribiendo todos los campos necesarios
            csvWriter.append(monto + ",").append(fecha + ",").append(categoriaGasto + ",").append(comentario + "\n");
            csvWriter.flush(); // Asegúrate de que los datos se escriban en el archivo
            System.out.println("Gasto registrado exitosamente en " + csvGastos);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
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
        List<Gasto> gastos = obtenerGastos(); // Cargar todos los gastos

        // Mapa para almacenar la suma total de cada categoría
        Map<String, Double> sumaPorCategoria = new HashMap<>();
        double montoTotal = 0.0;

        // Recorrer cada gasto, sumarlo al monto total y acumular en su categoría
        for (Gasto gasto : gastos) {
            String categoria = gasto.getCategoria();
            double monto = gasto.getMonto();

            // Sumar al total de la categoría
            sumaPorCategoria.put(categoria, sumaPorCategoria.getOrDefault(categoria, 0.0) + monto);
            // Sumar al monto total
            montoTotal += monto;
        }

        // Mapa para almacenar el porcentaje de cada categoría
        Map<String, Double> porcentajePorCategoria = new HashMap<>();

        // Calcular el porcentaje de cada categoría
        for (String categoria : sumaPorCategoria.keySet()) {
            double sumaCategoria = sumaPorCategoria.get(categoria);
            double porcentaje = (sumaCategoria / montoTotal) * 100;
            porcentajePorCategoria.put(categoria, porcentaje);
        }

        return porcentajePorCategoria;
    }





}
