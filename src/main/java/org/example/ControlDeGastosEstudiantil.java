package org.example;

import java.util.Scanner;

public class ControlDeGastosEstudiantil {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorGastos gestorGastos = new GestorGastos();

        System.out.println("Bienvenido al Sistema de Control de Gastos Estudiantil");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la universidad a la cual pertenece: ");
        String universidad = scanner.nextLine();

        gestorGastos.guardarEstudianteEnCSV(nombre, universidad);
        mostrarMenu(scanner, gestorGastos);
    }

    public static void mostrarMenu(Scanner scanner, GestorGastos gestorGastos) {
        int opcion = -1;
        boolean mostrarMenu = true;

        while (mostrarMenu) {
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

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error, la opción seleccionada es inválida.");
                continue;
            }
            if (opcion < 1 || opcion > 10) {
                System.out.println("Error: La opción seleccionada es inválida. Intente nuevamente.");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarGastoEstudiantil(scanner, gestorGastos);
                    break;
                case 2:
                    gestorGastos.imprimirGastosCSV();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    mostrarMenu=false;
                    break;
            }
        }
        System.out.println("Finalizando programa...");
    }

    public static void registrarGastoEstudiantil(Scanner scanner, GestorGastos gestorGastos) {
        int monto = 0;
        String fecha, categoriaGasto, comentario;
        try {
            System.out.println("Ingrese el monto del gasto: ");
            monto = Integer.parseInt(scanner.nextLine());

            System.out.println("Ingrese la fecha del gasto (DD/MM/AAAA): ");
            fecha = scanner.nextLine();

            System.out.println("Ingrese el tipo o categoría de gasto: ");
            categoriaGasto = scanner.nextLine();

            System.out.println("Si desea, ingrese algún comentario extra: ");
            comentario = scanner.nextLine();

            gestorGastos.registrarGastoEstudiantil(monto, fecha, categoriaGasto, comentario);

        } catch (NumberFormatException e) {
            System.out.println("Error: El monto debe ser un número.");
        }
    }
}