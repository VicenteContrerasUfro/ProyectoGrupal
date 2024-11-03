package org.example;

import java.util.Scanner;

public class ControlDeGastosEstudiantil {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorEstudiantes gestorEstudiantes = new GestorEstudiantes();
        GestorGastos gestorGastos = new GestorGastos();

        System.out.println("Bienvenido al Sistema de Control de Gastos Estudiantil");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la universidad a la cual pertenece: ");
        String universidad = scanner.nextLine();

        Estudiante estudiante = new Estudiante(nombre, universidad);
        gestorEstudiantes.guardarEstudianteEnCSV(estudiante);

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
            System.out.println("7) Calcular promedio de gastos");
            System.out.println("8) Eliminar datos de CSV");
            System.out.println("9) Visualizar porcentaje de tipos de gastos en el monto total");
            System.out.println("10) Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: La opción seleccionada no es válida.");
                continue;
            }

            switch (opcion) {
                case 1 -> registrarGasto(scanner, gestorGastos);
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
                case 7 -> System.out.println("Funcionalidad de calcular promedio de gastos (por implementar)");
                case 8 -> System.out.println("Funcionalidad de eliminar datos de CSV (por implementar)");
                case 9 -> System.out.println("Funcionalidad de visualizar porcentaje de tipos de gastos (por implementar)");
                case 10 -> {
                    System.out.println("Finalizando programa...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    public static void registrarGasto(Scanner scanner, GestorGastos gestorGastos) {
        int monto = 0;
        String fecha, categoria, comentario;

        while (true) {
            System.out.print("Ingrese el monto del gasto: ");
            try {
                monto = Integer.parseInt(scanner.nextLine());
                if (monto <= 0) {
                    System.out.println("Error: El monto debe ser un número positivo.");
                    continue;
                }
                break; // monto invalido
            } catch (NumberFormatException e) {
                System.out.println("Error: El monto debe ser un número.");
            }
        }

        while (true) {
            System.out.print("Ingrese la fecha del gasto (DD/MM/AAAA): ");
            fecha = scanner.nextLine();
            if (validarFecha(fecha)) {
                break; // fecha en correcto formato
            } else {
                System.out.println("Error: La fecha ingresada no tiene un formato válido. Intente de nuevo.");
            }
        }

        while (true) {
            System.out.print("Ingrese la categoría de gasto: ");
            categoria = scanner.nextLine();
            if (!categoria.isEmpty()) {
                break; // acaba el bucle si es que no esta vacía la categoria
            } else {
                System.out.println("Error: La categoría no puede estar vacía.");
            }
        }

        System.out.print("Ingrese algún comentario extra: ");
        comentario = scanner.nextLine();

        Gasto gasto = new Gasto(monto, fecha, categoria, comentario);
        gestorGastos.registrarGasto(gasto);
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
                break; // fin del bucle si no supera el maximo
            } catch (NumberFormatException e) {
                System.out.println("Error: El límite debe ser un número.");
            }
        }

        gestorGastos.establecerLimiteGasto(limite);
    }

    public static boolean validarFecha(String fecha) {
        // Verifica que la fecha esté en formato DD/MM/AAAA
        String[] partes = fecha.split("/");
        if (partes.length != 3) return false;

        try {
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int anio = Integer.parseInt(partes[2]);

            // Validaciones
            if (mes < 1 || mes > 12 || dia < 1 || dia > 31) return false;

            // Validar días en meses
            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) return false;
            if (mes == 2) {
                if (dia > 29 || (dia == 29 && anio % 4 != 0)) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
