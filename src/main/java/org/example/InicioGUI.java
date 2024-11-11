package org.example;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioGUI {
    public static JFrame frame;
    public static JTextField tituloField, montoField, fechaField, categoriaField, detalleField;
    public static JTextArea historialArea;
    public static GestorGastos gestorGastos;
    public static JLabel metaGastoLabel;
    public static JLabel totalGastadoLabel;
    public static int metaGasto;

    public InicioGUI(GestorGastos gestorGastos) {
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
        totalGastadoLabel = new JLabel("$0");
        panelMontoTotal.add(totalGastadoLabel);
        frame.add(panelMontoTotal, BorderLayout.SOUTH);

        // Panel para el historial de gastos
        historialArea = new JTextArea();
        historialArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historialArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Gastos")); // Borde para el historial
        frame.add(scrollPane, BorderLayout.CENTER);

        // Recuperar historial guardado
        ControlDeGastosGUI.imprimirHistorial();

        // Panel para los botones de acciones
        JPanel panelBotones = new JPanel(new GridLayout(1, 5));

        // Botón para registrar gasto
        JButton registrarBtn = new JButton("Registrar Gasto");
        registrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlDeGastosGUI.registrarGasto();
            }
        });
        panelBotones.add(registrarBtn);

        JButton abrirCSVBtn = new JButton("Abrir Archivo CSV");
        abrirCSVBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestorCSV.abrirArchivoCSV();
            }
        });
        panelBotones.add(abrirCSVBtn);

        // Botón para limpiar todos los gastos
        JButton limpiarBtn = new JButton("Limpiar Todos los Gastos");
        limpiarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlDeGastosGUI.limpiarTodosLosGastos();
            }
        });
        panelBotones.add(limpiarBtn);

        // Botón para establecer meta de gastos
        JButton establecerMetaBtn = new JButton("Establecer Meta de Gastos");
        establecerMetaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlDeGastosGUI.establecerMeta();
            }
        });
        panelBotones.add(establecerMetaBtn);

        JButton verPorcentajeBtn = new JButton("Ver % por Categoría");
        verPorcentajeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlDeGastosGUI.mostrarPorcentajePorCategoria(gestorGastos);
            }
        });
        panelBotones.add(verPorcentajeBtn);


        // Botón para mostrar total gastado
        JButton verTotalGastadoBtn = new JButton("Total Gastado");
        verTotalGastadoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlDeGastosGUI.mostrarTotalGastado();
            }
        });
        panelBotones.add(verTotalGastadoBtn);

        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
