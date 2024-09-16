import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlDeGastosGUI {
    private JFrame frame;
    private JTextField nombreField, matriculaField, montoField, fechaField, categoriaField, detalleField;
    private JTextArea historialArea;

    public ControlDeGastosGUI() {
        // Crear la ventana principal
        frame = new JFrame("Control de Gastos Estudiantil");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel de registro de gastos
        JPanel panelRegistro = new JPanel(new GridLayout(6, 2));

        panelRegistro.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panelRegistro.add(nombreField);

        panelRegistro.add(new JLabel("Matrícula:"));
        matriculaField = new JTextField();
        panelRegistro.add(matriculaField);

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

        frame.add(panelRegistro, BorderLayout.NORTH);

        // Panel para el historial de gastos
        historialArea = new JTextArea();
        historialArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historialArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Botón para registrar gasto
        JButton registrarBtn = new JButton("Registrar Gasto");
        frame.add(registrarBtn, BorderLayout.SOUTH);
        registrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarGasto();
            }
        });

        frame.setVisible(true);
    }

    private void registrarGasto() {
        String nombre = nombreField.getText();
        String matricula = matriculaField.getText();
        String monto = montoField.getText();
        String fecha = fechaField.getText();
        String categoria = categoriaField.getText();
        String detalle = detalleField.getText();

        // Validar campos vacíos
        if (nombre.isEmpty() || matricula.isEmpty() || monto.isEmpty() || fecha.isEmpty() || categoria.isEmpty() || detalle.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Registrar gasto en el historial (simulado en este ejemplo)
            historialArea.append("Gasto registrado:\n");
            historialArea.append("Nombre: " + nombre + "\n");
            historialArea.append("Matrícula: " + matricula + "\n");
            historialArea.append("Monto: $" + monto + "\n");
            historialArea.append("Fecha: " + fecha + "\n");
            historialArea.append("Categoría: " + categoria + "\n");
            historialArea.append("Detalle: " + detalle + "\n\n");

            // Limpiar campos
            nombreField.setText("");
            matriculaField.setText("");
            montoField.setText("");
            fechaField.setText("");
            categoriaField.setText("");
            detalleField.setText("");
        }
    }

    public static void main(String[] args) {
        // Crear y mostrar la interfaz gráfica
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ControlDeGastosGUI();
            }
        });
    }
}
