package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorGastos {
    private static String csvGastos;
    private int limiteGasto;

    public GestorGastos(String csvGastosPath) {
        this.csvGastos = csvGastosPath;
    }

    public void registrarGasto(Gasto gasto) {
        double montoTotal = calcularMontoTotal();
        if (gasto.getMonto() + montoTotal > limiteGasto) {
            System.out.println("Error: El gasto de " + gasto.getMonto() + " excede el límite establecido de " + limiteGasto + ". Total actual: " + montoTotal);
            return;
        }
        if (!validarFecha(gasto.getFecha())) {
            System.out.println("Error: Introduzca una fecha válida.");
            return;
        }

        try (FileWriter csvWriter = new FileWriter(csvGastos, true)) {
            csvWriter.append(gasto.getTitulo() + ",")
                     .append(gasto.getMonto() + ",")
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

    public int calcularMontoTotal() {
        int total = 0;
        try (BufferedReader lectorGastos = new BufferedReader(new FileReader(csvGastos))) {
            String linea;
            while ((linea = lectorGastos.readLine()) != null) {
                String[] datos = linea.split(",");
                total += Integer.parseInt(datos[1]);
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
                if (datos[3].trim().equalsIgnoreCase(categoria)) {
                    System.out.println("Titulo: " + datos[0] + "Monto: " + datos[1] + ", Fecha: " + datos[2] + ", Comentario: " + datos[4]);
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
                if (datos[2].equals(fecha)) {
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
        this.limiteGasto = limite; }

    public int getLimiteGasto() {
        return limiteGasto; }

    public Map<String, Double> calcularPorcentajePorCategoria() {
        List<Gasto> gastos = GestorCSV.cargarGastosDesdeCSV("gastos.csv");
        Map<String, Double> totalPorCategoria = new HashMap<>();
        int totalGastos = 0;

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

    public List<Gasto> obtenerGastos() {
        List<Gasto> gastos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvGastos))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");

                // Parsear datos del CSV y crear un objeto Gasto
                String titulo = datos[0];
                int monto = Integer.parseInt(datos[1]);
                String fecha = datos[2];
                String categoria = datos[3];
                String comentario = datos[4];

                Gasto gasto = new Gasto(titulo, monto, fecha, categoria, comentario);
                gastos.add(gasto);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el archivo de gastos: " + e.getMessage());
        }
        return gastos;
    }

    public void guardarMetaGasto(int metaGasto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("metaGasto.txt"))) {
            writer.write(Double.toString(metaGasto));
        } catch (IOException e) {
            System.out.println("No se pudo guardar la meta de gasto: " + e.getMessage());
        }
    }

    public int cargarMetaGasto() {
        int meta = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("metaGasto.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                meta = Integer.parseInt(line);
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar la meta de gasto: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Formato de número inválido en la meta de gasto.");
        }
        return meta;
    }

    public static boolean validarFecha(String fecha) {
        String patron = "\\d{2}/\\d{2}/\\d{4}";
        if (fecha.matches(patron)) {
            String dia = (String.valueOf(fecha.charAt(0))+String.valueOf(fecha.charAt(1)));
            String mes = (String.valueOf(fecha.charAt(3))+String.valueOf(fecha.charAt(4)));
            String anio = (String.valueOf(fecha.charAt(6))+String.valueOf(fecha.charAt(7))+String.valueOf(fecha.charAt(8))+String.valueOf(fecha.charAt(9)));

            if (Integer.parseInt(dia) > 31) {
                return false; //comprobar que el dia no supere el 31
            }
            else if (Integer.parseInt(mes) > 12) {
                return false; //comprobar que el mes no supere el 12
            }
            else if (Integer.parseInt(dia) == 0 || Integer.parseInt(mes) == 0) {
                return false; //comprobar que el dia y mes no sea 00
            }
            else if (!esBisiesto(Integer.parseInt(anio)) && Integer.parseInt(mes) == 2 && Integer.parseInt(dia) > 28) {
                return false; //comprobar que en febrero no supere el dia 28
            }
            else if (esBisiesto(Integer.parseInt(anio)) && Integer.parseInt(mes) == 2 && Integer.parseInt(dia) > 29) {
                return false; //comprobar que en año bisiesto, en febrero no se supere el dia 29
            }
            else { return true; }
        }
        else { return false; }
    }

    public static boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
    }


}
