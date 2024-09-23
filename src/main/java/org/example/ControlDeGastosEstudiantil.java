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
        System.out.print("Ingrese la universidad a la cual pertenece: ");
        String universidad = scanner.nextLine();

        guardarEstudianteEnCSV(nombre, nombre, universidad);
        mostrarMenu(scanner);
    }

    public static void guardarEstudianteEnCSV(String s, String nombre, String universidad) {
        String archivoEstudiantesCSV = "estudiantes.csv";
        try (FileWriter writer = new FileWriter(archivoEstudiantesCSV, true)) {
            writer.append(nombre).append(",").append(universidad).append("\n");
            System.out.println("Datos guardados correctamente en " + archivoEstudiantesCSV);

        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public static void registrarGastoEstudiantil(Scanner scanner) {
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

            escribirGastoEnCSV(monto, fecha, categoriaGasto, comentario);

        } catch (NumberFormatException e) {
            System.out.println("Error: El monto debe ser un número.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    public static void escribirGastoEnCSV(int monto, String fecha, String categoriaGasto, String comentario) throws IOException {
        FileWriter csvWriter = new FileWriter("gastos.csv", true);

        csvWriter.append(monto + ",");csvWriter.append(fecha + ",");csvWriter.append(categoriaGasto + ",");csvWriter.append(comentario + "\n");

        csvWriter.flush();
        csvWriter.close();

        System.out.println("Gasto registrado exitosamente en gastos.csv");

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

            try{
                opcion = Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e) {
                System.out.println("Error, la opción seleccionada es inválida.");
                continue;
            }
            if (opcion < 1 || opcion > 10) {
                System.out.println("Error: La opción seleccionada es inválida. Intente nuevamente.");
                continue;}

            switch (opcion) {
                case 1:
                    registrarGastoEstudiantil(scanner);
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
            }
        }
        System.out.println("Programa finalizado.");
    }
}

