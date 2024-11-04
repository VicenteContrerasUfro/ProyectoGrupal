package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static org.example.ControlDeGastosEstudiantil.mostrarPorcentajePorCategoria;

public class ControlDeGastosGUI {
    private JFrame frame;
    private JTextField nombreField, montoField, fechaField, categoriaField, detalleField;
    private JTextArea historialArea;
    private GestorGastos gestorGastos;
    private JLabel metaGastoLabel;
    private double metaGasto;

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

        panelRegistro.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panelRegistro.add(nombreField);

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
        String nombre = nombreField.getText();
        String montoStr = montoField.getText();
        String fecha = fechaField.getText();
        String categoria = categoriaField.getText();
        String detalle = detalleField.getText();

        // Validar campos vacíos
        if (nombre.isEmpty() || montoStr.isEmpty() || fecha.isEmpty() || categoria.isEmpty() || detalle.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);

            // Crear y registrar el gasto si está dentro del límite
            Gasto gasto = new Gasto(monto, fecha, categoria, detalle);
            gestorGastos.registrarGasto(gasto);

            // Registrar gasto en el historial
            historialArea.append("Gasto registrado:\n");
            historialArea.append("Nombre: " + nombre + "\n");
            historialArea.append("Monto: $" + montoStr + "\n");
            historialArea.append("Fecha: " + fecha + "\n");
            historialArea.append("Categoría: " + categoria + "\n");
            historialArea.append("Detalle: " + detalle + "\n\n");

            // Limpiar campos
            nombreField.setText("");
            montoField.setText("");
            fechaField.setText("");
            categoriaField.setText("");
            detalleField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error: El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limpiarTodosLosGastos() {
        gestorGastos.eliminarDatosCSV();
        historialArea.setText(""); // Limpiar el área de historial en la interfaz
    }

    private void establecerMeta() {
        String input = JOptionPane.showInputDialog(frame, "Ingrese el límite máximo de gasto:");
        if (input != null) {
            try {
                metaGasto = Double.parseDouble(input);
                gestorGastos.establecerLimiteGasto(metaGasto);
                gestorGastos.guardarMetaGasto(metaGasto); // Guardar la nueva meta
                metaGastoLabel.setText("Meta Establecida: $" + metaGasto); // Actualizar el JLabel
                JOptionPane.showMessageDialog(frame, "Meta establecida en: " + metaGasto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // Crear gestorGastos y pasar el archivo CSV
        GestorGastos gestorGastos = new GestorGastos();

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
}
