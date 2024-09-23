import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.example.GestorGastos;

import static org.example.ControlDeGastosEstudiantil.registrarGastoEstudiantil;
import static org.junit.jupiter.api.Assertions.*;

class ControlDeGastosTest {
    private String archivoGastosCSV = "gastos.csv"; // Ruta del archivo CSV

    @BeforeEach
    void setUp() {
        File archivo = new File(archivoGastosCSV);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @Test
    public void testRegistrarGastoEstudiantil_validInput() {
        GestorGastos gestorGastos = new GestorGastos();
        Scanner scanner = new Scanner("1000\n01/01/2022\nComida\nEmpanadas\n");
        int montoEsperado = 1000;
        String fechaEsperada = "01/01/2022";
        String categoriaGastoEsperada = "Comida";
        String comentarioEsperado = "Empanadas";

        registrarGastoEstudiantil(scanner, gestorGastos);
        String[] lineasGasto = leerLineasArchivo("gastos.csv");
        String ultimaLinea = lineasGasto[lineasGasto.length - 1];
        String[] valoresGasto = ultimaLinea.split(",");
        assertEquals(String.valueOf(montoEsperado), valoresGasto[0]);
        assertEquals(fechaEsperada, valoresGasto[1]);
        assertEquals(categoriaGastoEsperada, valoresGasto[2]);
        assertEquals(comentarioEsperado, valoresGasto[3]);
    }
    //Se crea metodo para leerLineasArchivo ya que el metodo de GestorGastos tiene otra funcionalidad y no devuelve
    //un String[]
    private String[] leerLineasArchivo(String archivoGastosCSV) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivoGastosCSV))) {
            List<String> lineas = new ArrayList<>();
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineas.add(linea);
            }
            return lineas.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return new String[0];
    }
}
