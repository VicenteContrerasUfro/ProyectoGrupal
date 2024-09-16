package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ControlDeGastosEstudiantil {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Sistema de Control de Gastos Estudiantil");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su matrícula: ");
        String matricula = scanner.nextLine();

        guardarEstudianteEnCSV(nombre, nombre, matricula);
        mostrarMenu(scanner);
    }

    public static void guardarEstudianteEnCSV(String s, String nombre, String matricula) {
        String archivoCSV = "estudiantes.csv";
        try (FileWriter writer = new FileWriter(archivoCSV, true)) {
            writer.append(nombre).append(",").append(matricula).append("\n");
            System.out.println("Datos guardados correctamente en " + archivoCSV);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }


    public static void mostrarMenu(Scanner scanner) {
        int opcion = -1;

        while (opcion != 10) {
            System.out.println("\nMenú principal:");
            System.out.println("1) Registrar gastos");
            System.out.println("2) Visualizar historial de compras");
            System.out.println("3) Monto total gastado");
            System.out.println("4) Búsqueda por categoría de gasto");
            System.out.println("5) Búsqueda por fecha");
            System.out.println("6) Establecer meta");
            System.out.println("7) Calcular promedio de gastos");
            System.out.println("8) Eliminar datos de CSV");
            System.out.println("9) Visualizar porcentaje de tipos de gastos en el monto total");
            System.out.println("10) Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Opción 1 seleccionada: Registrar gastos.");

                    break;
                case 2:
                    System.out.println("Opción 2 seleccionada: Visualizar historial de compras.");

                    break;
                case 3:
                    System.out.println("Opción 3 seleccionada: Monto total gastado.");

                    break;
                case 4:
                    System.out.println("Opción 4 seleccionada: Búsqueda por categoría de gasto.");

                    break;
                case 5:
                    System.out.println("Opción 5 seleccionada: Búsqueda por fecha.");

                    break;
                case 6:
                    System.out.println("Opción 6 seleccionada: Establecer meta.");

                    break;
                case 7:
                    System.out.println("Opción 7 seleccionada: Calcular promedio de gastos.");

                    break;
                case 8:
                    System.out.println("Opción 8 seleccionada: Eliminar datos de CSV.");

                    break;
                case 9:
                    System.out.println("Opción 9 seleccionada: Visualizar porcentaje de tipos de gastos.");

                    break;
                case 10:
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        System.out.println("Programa finalizado.");
    }
}