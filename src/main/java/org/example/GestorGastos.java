package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorGastos {
    private static String csvGastos = "gastos.csv";
    private double limiteGasto;


    public void registrarGasto(Gasto gasto) {
        double montoTotal = calcularMontoTotal();
        if (gasto.getMonto() + montoTotal > limiteGasto) {
            System.out.println("Error: El gasto de " + gasto.getMonto() + " excede el límite establecido de " + limiteGasto + ". Total actual: " + montoTotal);
            return;
        }

        try (FileWriter csvWriter = new FileWriter(csvGastos, true)) {
            csvWriter.append(gasto.getMonto() + ",")
                    .append(gasto.getFecha() + ",")
                    .append(gasto.getCategoria() + ",")
                    .append(gasto.getComentario() + "\n");
            csvWriter.flush();
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
    public double calcularMontoTotal() {
        double total = 0.0;
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                total += Double.parseDouble(datos[0]);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: El monto debe ser un número válido.");
            e.printStackTrace();
        }
        return total;
    }


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
    public void establecerLimiteGasto(double limite) {
        this.limiteGasto = limite;
        System.out.println("Límite de gasto establecido en: " + limite);
    }

    public void eliminarDatosCSV() {
        try (FileWriter writer = new FileWriter(csvGastos, false)) {
            writer.write("");
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

    public List<Gasto> cargarGastosDesdeCSV() {
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

    public List<Gasto> obtenerGastos() {
        List<Gasto> gastos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvGastos))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");

                // Parsear datos del CSV y crear un objeto Gasto
                double monto = Double.parseDouble(datos[0]);
                String fecha = datos[1];
                String categoria = datos[2];
                String comentario = datos[3];

                Gasto gasto = new Gasto(monto, fecha, categoria, comentario);
                gastos.add(gasto);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el archivo de gastos: " + e.getMessage());
        }

        return gastos;
    }
    public void guardarMetaGasto(double metaGasto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("metaGasto.txt"))) {
            writer.write(Double.toString(metaGasto));
        } catch (IOException e) {
            System.out.println("No se pudo guardar la meta de gasto: " + e.getMessage());
        }
    }


    public double cargarMetaGasto() {
        double meta = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader("metaGasto.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                meta = Double.parseDouble(line);
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar la meta de gasto: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Formato de número inválido en la meta de gasto.");
        }
        return meta;
    }

}
