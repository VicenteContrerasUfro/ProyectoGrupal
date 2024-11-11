package org.example;

import javax.swing.*;

import java.util.HashMap;
import java.util.Map;

import static org.example.InicioGUI.*;

public class ControlDeGastosGUI {
    public static void main(String[] args) {
        // Crear gestorGastos y pasar el archivo CSV
        GestorGastos gestorGastos = new GestorGastos("gastos.csv");

        // Crear y mostrar la interfaz gráfica
        SwingUtilities.invokeLater(() -> new InicioGUI(gestorGastos));
    }

    static void registrarGasto() {
        String titulo = tituloField.getText();
        String montoStr = montoField.getText();
        String fecha = fechaField.getText();
        String categoria = categoriaField.getText();
        String detalle = detalleField.getText();

        // Validar campos vacíos
        if (titulo.isEmpty() || montoStr.isEmpty() || fecha.isEmpty() || categoria.isEmpty() || detalle.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int monto = Integer.parseInt(montoStr);
            int montoTotalActual = InicioGUI.gestorGastos.calcularMontoTotal();

            // Verificar si el gasto excede el límite establecido
            if (monto + montoTotalActual > metaGasto) {
                JOptionPane.showMessageDialog(frame, "Error: El gasto que se intenta ingresar supera el límite máximo establecido. Límite: $" + metaGasto, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //validar fecha
            if (!GestorGastos.validarFecha(fecha)) {
                JOptionPane.showMessageDialog(frame, "Error: La fecha introducida no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear y registrar el gasto
            Gasto gasto = new Gasto(titulo, monto, fecha, categoria, detalle);
            gestorGastos.registrarGasto(gasto);

            // Registrar gasto en el historial
            imprimirGasto(gasto);

            // Actualizar el monto total gastado
            totalGastadoLabel.setText("$" + gestorGastos.calcularMontoTotal());

            // Limpiar campos
            tituloField.setText("");
            montoField.setText("");
            fechaField.setText("");
            categoriaField.setText("");
            detalleField.setText("");

            JOptionPane.showMessageDialog(frame, "Gasto registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error: El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void imprimirHistorial() {
        for (Gasto gasto : gestorGastos.obtenerGastos()) {
            imprimirGasto(gasto);
        }
    }

    public static void imprimirGasto(Gasto gasto) {
        historialArea.append("Titulo: " + gasto.getTitulo() + "\n");
        historialArea.append("Monto: $" + gasto.getMonto() + "\n");
        historialArea.append("Fecha: " + gasto.getFecha() + "\n");
        historialArea.append("Categoría: " + gasto.getCategoria() + "\n");
        historialArea.append("Detalle: " + gasto.getComentario() + "\n\n");
    }

    static void limpiarTodosLosGastos() {
        GestorCSV.eliminarDatosCSV("gastos.csv");
        historialArea.setText(""); // Limpiar el área de historial en la interfaz
    }

    static void establecerMeta() {
        String input = JOptionPane.showInputDialog(frame, "Ingrese el límite máximo de gasto:");
        if (input != null) {
            try {
                metaGasto = Integer.parseInt(input);
                gestorGastos.establecerLimiteGasto((int) metaGasto);
                gestorGastos.guardarMetaGasto(metaGasto); // Guardar la nueva meta
                metaGastoLabel.setText("Meta Establecida: $" + metaGasto); // Actualizar el JLabel
                JOptionPane.showMessageDialog(frame, "Meta establecida en: $" + metaGasto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static void mostrarTotalGastado() {
        int totalGastado = GestorGastos.calcularMontoTotal();
        JOptionPane.showMessageDialog(frame, "Total Gastado: $" + totalGastado, "Total Gastado", JOptionPane.INFORMATION_MESSAGE);
    }

    static void mostrarPorcentajePorCategoria(GestorGastos gestorGastos) {
        Map<String, Double> gastosPorCategoria = new HashMap<>();
        double totalGastado = 0.0;

        // Calcular el total gastado y los gastos por categoría
        for (Gasto gasto : gestorGastos.obtenerGastos()) {
            totalGastado += gasto.getMonto();
            gastosPorCategoria.put(gasto.getCategoria(), gastosPorCategoria.getOrDefault(gasto.getCategoria(), 0.0) + gasto.getMonto());
        }

        // Crear el mensaje con el porcentaje por categoría
        StringBuilder mensaje = new StringBuilder("Porcentaje de Gasto por Categoría:\n");
        for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
            double porcentaje = (entry.getValue() / totalGastado) * 100;
            mensaje.append(entry.getKey()).append(": ").append(String.format("%.2f", porcentaje)).append("%\n");
        }

        // Mostrar el mensaje en un cuadro de diálogo
        JOptionPane.showMessageDialog(frame, mensaje.toString(), "Porcentaje de Gasto por Categoría", JOptionPane.INFORMATION_MESSAGE);
    }
}
