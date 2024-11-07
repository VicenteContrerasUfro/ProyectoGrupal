package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControlDeGastosGUI {
    private JFrame frame;
    private JTextField tituloField, montoField, fechaField, categoriaField, detalleField;
    private JTextArea historialArea;
    private GestorGastos gestorGastos;
    private JLabel metaGastoLabel;
    private JLabel totalGastadoLabel;
    private int metaGasto;

    public ControlDeGastosGUI(GestorGastos gestorGastos) {
        this.gestorGastos = gestorGastos;
        metaGasto = gestorGastos.cargarMetaGasto();

        // Configuración de la ventana principal
        frame = new JFrame("Control de Gastos Estudiantil");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel de registro de gastos
        JPanel panelRegistro = new JPanel(new GridLayout(6, 2));

        panelRegistro.add(new JLabel("Titulo:"));
        tituloField = new JTextField();
        panelRegistro.add(tituloField);

        panelRegistro.add(new JLabel("Monto:"));
        montoField = new JTextField();
        panelRegistro.add(montoField);

        panelRegistro.add(new JLabel("Fecha (DD/MM/AAAA):"));
        fechaField = new JTextField();
        panelRegistro.add(fechaField);

        panelRegistro.add(new JLabel("Categoría:"));
        categoriaField = new JTextField();
        panelRegistro.add(categoriaField);

        panelRegistro.add(new JLabel("Detalle:"));
        detalleField = new JTextField();
        panelRegistro.add(detalleField);

        // Agregar el JLabel para mostrar la meta de gasto
        metaGastoLabel = new JLabel("Meta Establecida: $" + metaGasto);
        panelRegistro.add(metaGastoLabel); // Agregar la meta al panel de registro

        frame.add(panelRegistro, BorderLayout.NORTH);

        // Panel para el monto total gastado
        JPanel panelMontoTotal = new JPanel(new GridLayout(1, 2));
        panelMontoTotal.add(new JLabel("Monto Total Gastado:"));
        totalGastadoLabel = new JLabel("$0.0");
        panelMontoTotal.add(totalGastadoLabel);
        frame.add(panelMontoTotal, BorderLayout.SOUTH);

        // Panel para el historial de gastos
        historialArea = new JTextArea();
        historialArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historialArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Gastos")); // Borde para el historial
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones de acciones
        JPanel panelBotones = new JPanel(new GridLayout(1, 5));

        // Botón para registrar gasto
        JButton registrarBtn = new JButton("Registrar Gasto");
        registrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarGasto();
            }
        });
        panelBotones.add(registrarBtn);

        JButton abrirCSVBtn = new JButton("Abrir Archivo CSV");
        abrirCSVBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivoCSV();
            }
        });
        panelBotones.add(abrirCSVBtn);

        // Botón para limpiar todos los gastos
        JButton limpiarBtn = new JButton("Limpiar Todos los Gastos");
        limpiarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTodosLosGastos();
            }
        });
        panelBotones.add(limpiarBtn);

        // Botón para establecer meta de gastos
        JButton establecerMetaBtn = new JButton("Establecer Meta de Gastos");
        establecerMetaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                establecerMeta();
            }
        });
        panelBotones.add(establecerMetaBtn);

        JButton verPorcentajeBtn = new JButton("Ver % por Categoría");
        verPorcentajeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPorcentajePorCategoria(gestorGastos);
            }
        });
        panelBotones.add(verPorcentajeBtn);


        // Botón para mostrar total gastado
        JButton verTotalGastadoBtn = new JButton("Total Gastado");
        verTotalGastadoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTotalGastado();
            }
        });
        panelBotones.add(verTotalGastadoBtn);

        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void registrarGasto() {
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
            int montoTotalActual = (int) gestorGastos.calcularMontoTotal();

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
            historialArea.append("Gasto registrado:\n");
            historialArea.append("Nombre: " + titulo + "\n");
            historialArea.append("Monto: $" + montoStr + "\n");
            historialArea.append("Fecha: " + fecha + "\n");
            historialArea.append("Categoría: " + categoria + "\n");
            historialArea.append("Detalle: " + detalle + "\n\n");

            // Actualizar el monto total gastado
            totalGastadoLabel.setText("$" + gestorGastos.calcularMontoTotal());

            // Limpiar campos
            tituloField.setText("");
            montoField.setText("");
            fechaField.setText("");
            categoriaField.setText("");
            detalleField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error: El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void limpiarTodosLosGastos() {
        GestorCSV.eliminarDatosCSV("gastos.csv");
        historialArea.setText(""); // Limpiar el área de historial en la interfaz
    }

    private void establecerMeta() {
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

    public static void main(String[] args) {
        // Crear gestorGastos y pasar el archivo CSV
        GestorGastos gestorGastos = new GestorGastos("gastos.csv");

        // Crear y mostrar la interfaz gráfica
        SwingUtilities.invokeLater(() -> new ControlDeGastosGUI(gestorGastos));
    }

    private void abrirArchivoCSV() {
        try {
            File archivoCSV = new File("gastos.csv");
            if (archivoCSV.exists()) {
                Desktop.getDesktop().open(archivoCSV);
            } else {
                JOptionPane.showMessageDialog(frame, "El archivo CSV no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "No se pudo abrir el archivo CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTotalGastado() {
        double totalGastado = gestorGastos.calcularMontoTotal();
        JOptionPane.showMessageDialog(frame, "Total Gastado: $" + totalGastado, "Total Gastado", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarPorcentajePorCategoria(GestorGastos gestorGastos) {
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
