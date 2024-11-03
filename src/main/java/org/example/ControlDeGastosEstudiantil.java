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

            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1 -> registrarGasto(scanner, gestorGastos);
                case 2 -> gestorGastos.imprimirGastos();
                case 3 -> System.out.println("Monto total gastado: " + gestorGastos.calcularMontoTotal());
                case 4 -> {
                    System.out.print("Ingrese la categoría de gasto: ");
                    gestorGastos.buscarGastosPorCategoria(scanner.nextLine());
                }
                case 5 -> System.out.println("Funcionalidad de búsqueda por fecha (por implementar)");
                case 6 -> System.out.println("Funcionalidad de establecer meta (por implementar)");
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
        System.out.print("Ingrese el monto del gasto: ");
        int monto = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese la fecha del gasto (DD/MM/AAAA): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese la categoría de gasto: ");
        String categoria = scanner.nextLine();
        System.out.print("Ingrese algún comentario extra: ");
        String comentario = scanner.nextLine();

        Gasto gasto = new Gasto(monto, fecha, categoria, comentario);
        gestorGastos.registrarGasto(gasto);
    }
}
