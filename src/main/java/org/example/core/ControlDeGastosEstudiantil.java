package org.example.core;

import org.example.model.Gasto;
import org.example.service.GestorGastos;

import java.util.Map;
import java.util.Scanner;

public class ControlDeGastosEstudiantil {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorGastos gestorGastos = new GestorGastos();

        System.out.println("Bienvenido al Sistema de Control de Gastos Estudiantil");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        mostrarMenu(scanner, gestorGastos);
    }

    public static void mostrarMenu(Scanner scanner, GestorGastos gestorGastos) {
        while (true) {
            System.out.println("\nMenú principal:");
            System.out.println("1) Registrar gastos");
            System.out.println("2) Visualizar historial de gastos");
            System.out.println("3) Monto total gastado");
            System.out.println("4) Búsqueda por categoría de gasto");
            System.out.println("5) Búsqueda por fecha");
            System.out.println("6) Establecer meta");
            System.out.println("7) Calcular promedio de cada tipo de gastos");
            System.out.println("8) Eliminar datos de CSV");
            System.out.println("9) Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: La opción seleccionada no es válida.");
                continue;
            }

            switch (opcion) {
                case 1 -> registrarGastoEstudiantil(scanner, gestorGastos);
                case 2 -> gestorGastos.imprimirGastos();
                case 3 -> System.out.println("Monto total gastado: " + gestorGastos.calcularMontoTotal());
                case 4 -> {
                    System.out.print("Ingrese la categoría de gasto: ");
                    gestorGastos.buscarGastosPorCategoria(scanner.nextLine());
                }
                case 5 -> {
                    System.out.print("Ingrese la fecha de gasto (DD/MM/AAAA): ");
                    gestorGastos.buscarGastosPorFecha(scanner.nextLine());
                }
                case 6 -> establecerMeta(scanner, gestorGastos);
                case 7 -> mostrarPorcentajePorCategoria(gestorGastos);
                case 8 -> gestorGastos.eliminarDatosCSV();
                case 9 -> {
                    System.out.println("Finalizando programa...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    public static void registrarGastoEstudiantil(Scanner scanner, GestorGastos gestorGastos) {
        double monto = 0.0;
        String fecha, categoriaGasto, comentario;
        try {
            System.out.println("Ingrese el monto del gasto: ");
            String montoInput = scanner.nextLine().trim(); // Eliminar espacios en blanco
            System.out.println("Monto ingresado (sin espacios): " + montoInput);

            // Reemplazar coma por punto si es necesario
            if (montoInput.contains(",")) {
                montoInput = montoInput.replace(",", ".");
                System.out.println("Monto después de reemplazar comas por puntos: " + montoInput);
            }

            monto = Double.parseDouble(montoInput);

            System.out.println("Ingrese la fecha del gasto (DD/MM/AAAA): ");
            fecha = scanner.nextLine();
            if (!validarFecha(fecha)) {
                System.out.println("Error: La fecha ingresada no es válida. Por favor, intente nuevamente con el formato DD/MM/AAAA.");
                return;
            }

            System.out.println("Ingrese la categoría de gasto: ");
            categoriaGasto = scanner.nextLine();

            System.out.println("Si desea, ingrese algún comentario extra: ");
            comentario = scanner.nextLine();

            Gasto gasto = new Gasto(monto, fecha, categoriaGasto, comentario);

            gestorGastos.registrarGasto(gasto);

        } catch (NumberFormatException e) {
            System.out.println("Error: El monto debe ser un número válido.");
            e.printStackTrace(); // Esto ayudará a ver el error completo en la consola
        }
    }




    public static void establecerMeta(Scanner scanner, GestorGastos gestorGastos) {
        int limite = 0;

        while (true) {
            System.out.print("Ingrese el límite de gasto: ");
            try {
                limite = Integer.parseInt(scanner.nextLine());
                if (limite <= 0) {
                    System.out.println("Error: El límite debe ser un número positivo.");
                    continue;
                }
                System.out.println("El limite ha sido definido en: "+ limite);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: El límite debe ser un número.");
            }
        }

        gestorGastos.establecerLimiteGasto(limite);
    }

    public static void mostrarPorcentajePorCategoria(GestorGastos gestorGastos) {
        Map<String, Double> porcentajes = gestorGastos.calcularPorcentajePorCategoria();
        System.out.println("Porcentaje de gastos por categoría:");
        if (porcentajes.isEmpty()) {
            System.out.println("No hay gastos registrados.");
        } else {
            for (Map.Entry<String, Double> entry : porcentajes.entrySet()) {
                System.out.printf("%s: %.2f%%\n", entry.getKey(), entry.getValue());
            }
        }
    }


    public static boolean validarFecha(String fecha) {
        String[] partes = fecha.split("/");
        if (partes.length != 3) return false;

        try {
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);

            if (mes < 1 || mes > 12 || dia < 1 || dia > 31) return false;
            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) return false;
            if (mes == 2 && (dia > 29 || (dia == 29 && anio % 4 != 0))) return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
