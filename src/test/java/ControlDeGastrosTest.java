import org.example.ControlDeGastosEstudiantil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ControlDeGastosTest {

    private String nombre;
    private String universidad;
    private String archivoGastosCSV = "gastos.csv"; // Ruta del archivo CSV

    @BeforeEach
    void setUp() {
        nombre = "Rodrigo Olave";
        universidad = "202201234";

        // Limpiar el archivo CSV
        File archivo = new File(archivoGastosCSV);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @Test
    void testComprobacionGastos() {
        String gastoSimulado = "1\n1000\n23/09/2024\nComida\nEmpanadas\n10\n";
        Scanner scanner = new Scanner(gastoSimulado);
            ControlDeGastosEstudiantil.mostrarMenu(scanner);

        try (BufferedReader br = new BufferedReader(new FileReader(archivoGastosCSV))) {
            String linea = br.readLine();
            assertNotNull(linea, "El archivo CSV debería contener una línea.");

            //Se crea una matriz que separa cada coma en una posición distinta en el arreglo
            String[] datos = linea.split(",");
            //Aqui se hace la comprobación de que cada posición coincide con la entrada del gasto ingresado
            assertEquals("1000", datos[0], "El monto debería ser 1000.");
            assertEquals("23/09/2024", datos[1], "La fecha debería ser 23/09/2024.");
            assertEquals("Comida", datos[2], "El tipo de gasto debería ser Comida.");
            assertEquals("Empanadas", datos[3], "El comentario debería ser Empanadas.");

        } catch (IOException e) {
            fail("No se pudo leer el archivo CSV: " + e.getMessage());
        }
    }
}
