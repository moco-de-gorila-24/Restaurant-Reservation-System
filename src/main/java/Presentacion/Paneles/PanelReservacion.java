package Presentacion.Paneles;

import Entidades.DiningTable;
import Presentacion.Componentes.Button;
import Presentacion.Coordinador.Coordinador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel con funcionalidades CRUD de reservación
 */
public class PanelReservacion extends JPanel {

    private static final DateTimeFormatter FECHA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FMT  = DateTimeFormatter.ofPattern("HH:mm");

    private final Coordinador coordinador;

    private JComboBox<String> cbCliente;
    private JComboBox<String> cbRestaurante;
    private JComboBox<DiningTable> cbMesa;
    private JTextField txtFecha, txtHora, txtPersonas;

    private JButton btnGuardar, btnActualizar, btnEliminar, btnBuscar;
    private Button btnLimpiar;

    private JLabel lblStatus;
    private Long idActual = null;

    private static class FilaReservacion {
        Long id;
        String cliente, restaurante;
        DiningTable mesa;
        LocalDate fecha;
        LocalTime hora;
        int personas;
    }

    private final List<FilaReservacion> filas = new ArrayList<>();

    public PanelReservacion(Coordinador coordinador) {
        this.coordinador = coordinador;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 235, 220));
        construirUI();
    }

    private void construirUI() {
        add(construirHeader(), BorderLayout.NORTH);
        add(construirFormulario(), BorderLayout.CENTER);

        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);
        sur.add(construirBarraBotones(), BorderLayout.CENTER);

        lblStatus = new JLabel(" ", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        sur.add(lblStatus, BorderLayout.SOUTH);

        add(sur, BorderLayout.SOUTH);
    }

    private JPanel construirHeader() {
        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);

        Button btnVolver = new Button("Volver al inicio");
        btnVolver.setPreferredSize(new Dimension(180, 38));
        btnVolver.addActionListener(e -> coordinador.mostrarPanelInicio());

        JLabel titulo = new JLabel("Gestión de reservaciones");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel placeholder = new JPanel();
        placeholder.setOpaque(false);
        placeholder.setPreferredSize(new Dimension(180, 38));

        norte.add(btnVolver, BorderLayout.WEST);
        norte.add(titulo, BorderLayout.CENTER);
        norte.add(placeholder, BorderLayout.EAST);
        return norte;
    }

    private JPanel construirFormulario() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(252, 249, 242));
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Datos de la Reservación"),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        int y = 0;

        cbCliente = new JComboBox<>();
        cbRestaurante = new JComboBox<>();
        cbMesa = new JComboBox<>();
        txtFecha = new JTextField(15);
        txtHora = new JTextField(15);
        txtPersonas = new JTextField(15);

        cbCliente.addItem("Cliente de ejemplo");
        cbRestaurante.addItem("Restaurante de ejemplo");
        cbMesa.addItem(new DiningTable(1, 4));

        cbMesa.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DiningTable) {
                    DiningTable m = (DiningTable) value;
                    setText("Mesa " + m.getNumber() + " (cap " + m.getCapacity() + ")");
                }
                return this;
            }
        });

        gbc.gridy = y;
        gbc.gridx = 0; form.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; form.add(cbCliente, gbc);

        gbc.gridx = 2; form.add(new JLabel("Restaurante:"), gbc);
        gbc.gridx = 3; form.add(cbRestaurante, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; form.add(new JLabel("Mesa:"), gbc);
        gbc.gridx = 1; form.add(cbMesa, gbc);

        gbc.gridx = 2; form.add(new JLabel("N° de personas:"), gbc);
        gbc.gridx = 3; form.add(txtPersonas, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; form.add(new JLabel("Fecha (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; form.add(txtFecha, gbc);

        gbc.gridx = 2; form.add(new JLabel("Hora (HH:mm):"), gbc);
        gbc.gridx = 3; form.add(txtHora, gbc);
        y++;

        GridBagConstraints gbcWrapper = new GridBagConstraints();
        gbcWrapper.gridx = 0; gbcWrapper.gridy = 0;
        gbcWrapper.weightx = 0; gbcWrapper.weighty = 0;
        gbcWrapper.anchor = GridBagConstraints.CENTER;
        wrapper.add(form, gbcWrapper);

        btnGuardar = boton("Guardar", new Color(80, 130, 70), 250, 45);
        btnGuardar.addActionListener(e -> guardar());
        JPanel panelGuardar = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        panelGuardar.setOpaque(false);
        panelGuardar.add(btnGuardar);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.add(wrapper, BorderLayout.CENTER);
        contenedor.add(panelGuardar, BorderLayout.SOUTH);
        return contenedor;
    }

    private JPanel construirBarraBotones() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);
        barra.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnLimpiar = new Button("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(110, 32));
        btnLimpiar.addActionListener(e -> limpiar());
        barra.add(btnLimpiar, BorderLayout.WEST);

        JPanel grupo = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        grupo.setOpaque(false);

        btnActualizar = boton("Actualizar", new Color(70, 110, 160), 200, 40);
        btnEliminar = boton("Eliminar", new Color(180, 60, 60), 200, 40);
        btnBuscar = boton("Buscar", new Color(120, 80, 160), 200, 40);

        grupo.add(btnActualizar);
        grupo.add(btnEliminar);
        grupo.add(btnBuscar);
        barra.add(grupo, BorderLayout.CENTER);

        JPanel placeholder = new JPanel();
        placeholder.setOpaque(false);
        placeholder.setPreferredSize(new Dimension(110, 32));
        barra.add(placeholder, BorderLayout.EAST);

        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnBuscar.addActionListener(e -> accionBuscar());

        return barra;
    }

    private JButton boton(String texto, Color color, int w, int h) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(w, h));
        return b;
    }

    private void mostrarError(String msg) {
        lblStatus.setForeground(new Color(190, 40, 40));
        lblStatus.setText("⚠ " + msg);
    }

    private void mostrarExito(String msg) {
        lblStatus.setForeground(new Color(40, 140, 40));
        lblStatus.setText("✔ " + msg);
    }

    private String formatoMesa(DiningTable m) {
        return "Mesa " + m.getNumber() + " (cap " + m.getCapacity() + ")";
    }

    private boolean guardar() {
        return idActual == null ? crear() : actualizar();
    }

    private boolean crear() {
        if (cbCliente.getSelectedItem() == null) {
            mostrarError("Selecciona un cliente.");
            return false;
        }
        if (cbRestaurante.getSelectedItem() == null) {
            mostrarError("Selecciona un restaurante.");
            return false;
        }

        DiningTable mesa = (DiningTable) cbMesa.getSelectedItem();
        if (mesa == null) {
            mostrarError("Selecciona una mesa.");
            return false;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(txtFecha.getText().trim(), FECHA_FMT);
        }
        catch (DateTimeParseException ex) {
            mostrarError("Fecha inválida. Formato esperado: dd/MM/yyyy.");
            return false;
        }

        LocalTime hora;
        try {
            hora = LocalTime.parse(txtHora.getText().trim(), HORA_FMT);
        }
        catch (DateTimeParseException ex) {
            mostrarError("Hora inválida. Formato esperado: HH:mm (24 h).");
            return false;
        }

        int personas;
        try {
            personas = Integer.parseInt(txtPersonas.getText().trim());
        }
        catch (NumberFormatException ex) {
            mostrarError("El número de personas debe ser numérico.");
            return false;
        }

        if (!validarPersonas(mesa, personas)) return false;

        FilaReservacion fila = new FilaReservacion();
        fila.id = (long) (filas.size() + 1);
        fila.cliente = (String) cbCliente.getSelectedItem();
        fila.restaurante = (String) cbRestaurante.getSelectedItem();
        fila.mesa = mesa;
        fila.fecha = fecha;
        fila.hora = hora;
        fila.personas = personas;

        filas.add(fila);
        limpiar();
        mostrarExito("Reservación creada con ID " + fila.id + ".");
        return true;
    }

    private boolean actualizar() {
        FilaReservacion fila = buscarPorId(idActual);
        if (fila == null) {
            mostrarError("Reservación no encontrada.");
            return false;
        }

        if (cbCliente.getSelectedItem() == null || cbRestaurante.getSelectedItem() == null) {
            mostrarError("Selecciona cliente y restaurante.");
            return false;
        }

        DiningTable mesa = (DiningTable) cbMesa.getSelectedItem();
        if (mesa == null) {
            mostrarError("Selecciona una mesa.");
            return false;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(txtFecha.getText().trim(), FECHA_FMT);
        }
        catch (DateTimeParseException ex) {
            mostrarError("Fecha inválida (dd/MM/yyyy).");
            return false;
        }

        LocalTime hora;
        try {
            hora = LocalTime.parse(txtHora.getText().trim(), HORA_FMT);
        }
        catch (DateTimeParseException ex) {
            mostrarError("Hora inválida (HH:mm).");
            return false;
        }

        int personas;
        try {
            personas = Integer.parseInt(txtPersonas.getText().trim());
        }
        catch (NumberFormatException ex) {
            mostrarError("Personas debe ser numérico.");
            return false;
        }

        if (!validarPersonas(mesa, personas)) return false;

        fila.cliente = (String) cbCliente.getSelectedItem();
        fila.restaurante = (String) cbRestaurante.getSelectedItem();
        fila.mesa = mesa;
        fila.fecha = fecha;
        fila.hora = hora;
        fila.personas = personas;

        limpiar();
        mostrarExito("Reservación actualizada.");
        return true;
    }

    private boolean validarPersonas(DiningTable mesa, int personas) {
        if (personas <= 0) {
            mostrarError("El número de personas debe ser mayor a 0.");
            return false;
        }
        int cap = mesa.getCapacity();
        if (personas > cap) {
            mostrarError("La reservación es para " + personas + " personas y la capacidad de la mesa es " + cap + ". No se puede asignar.");
            return false;
        }
        int minPersonas = Math.max(1, cap - 2);
        if (personas < minPersonas) {
            mostrarError("La mesa " + mesa.getNumber() + " (cap. " + cap + ") no puede reservarse con solo " + personas + " persona(s). Mínimo: " + minPersonas + ".");
            return false;
        }
        return true;
    }

    private boolean accionActualizar() {
        FilaReservacion sel = mostrarDialogoSeleccion("Seleccionar reservación a actualizar", "Editar");
        if (sel == null) return false;

        idActual = sel.id;
        cbCliente.setSelectedItem(sel.cliente);
        cbRestaurante.setSelectedItem(sel.restaurante);
        if (sel.mesa != null) {
            boolean encontrada = false;
            for (int i = 0; i < cbMesa.getItemCount(); i++) {
                if (cbMesa.getItemAt(i) == sel.mesa) {
                    cbMesa.setSelectedIndex(i);
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                cbMesa.addItem(sel.mesa);
                cbMesa.setSelectedItem(sel.mesa);
            }
        }
        txtFecha.setText(sel.fecha.format(FECHA_FMT));
        txtHora.setText(sel.hora.format(HORA_FMT));
        txtPersonas.setText(String.valueOf(sel.personas));
        mostrarExito("Editando reservación #" + sel.id + ". Modifica y presiona Guardar.");
        return true;
    }

    private boolean accionEliminar() {
        FilaReservacion sel = mostrarDialogoSeleccion("Seleccionar reservación a eliminar", "Eliminar");
        if (sel == null) return false;

        int r = JOptionPane.showConfirmDialog(this,
                "¿Eliminar reservación #" + sel.id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return false;

        filas.remove(sel);
        if (idActual != null && idActual.equals(sel.id)) limpiar();
        mostrarExito("Reservación eliminada.");
        return true;
    }

    private boolean accionBuscar() {
        if (filas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros disponibles.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        mostrarDialogoBusqueda();
        return true;
    }

    private FilaReservacion mostrarDialogoSeleccion(String titulo, String textoBotonOk) {
        if (filas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros disponibles.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        DefaultTableModel modelo = crearModeloTabla();
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowSelectionInterval(0, 0);

        final FilaReservacion[] seleccion = {null};

        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), titulo,
                Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setSize(820, 420);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(10, 10));
        dlg.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dlg.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton ok = new JButton(textoBotonOk);
        JButton cancelar = new JButton("Cancelar");
        ok.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(dlg, "Selecciona un registro.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                Long id = (Long) modelo.getValueAt(fila, 0);
                seleccion[0] = buscarPorId(id);
                dlg.dispose();
            }
        });
        cancelar.addActionListener(e -> dlg.dispose());
        sur.add(ok);
        sur.add(cancelar);
        dlg.add(sur, BorderLayout.SOUTH);

        dlg.setVisible(true);
        return seleccion[0];
    }

    private void mostrarDialogoBusqueda() {
        DefaultTableModel modelo = crearModeloTabla();
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);

        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this),
                "Reservaciones registradas", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setSize(820, 450);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(10, 10));
        dlg.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dlg.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dlg.dispose());
        JPanel sur = new JPanel();
        sur.add(cerrar);
        dlg.add(sur, BorderLayout.SOUTH);

        dlg.setVisible(true);
    }

    private DefaultTableModel crearModeloTabla() {
        String[] cols = {"ID", "Cliente", "Restaurante", "Mesa", "Fecha", "Hora", "Personas"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (FilaReservacion r : filas) {
            m.addRow(new Object[]{
                    r.id,
                    r.cliente == null ? "-" : r.cliente,
                    r.restaurante == null ? "-" : r.restaurante,
                    r.mesa == null ? "-" : formatoMesa(r.mesa),
                    r.fecha, r.hora, r.personas
            });
        }
        return m;
    }

    private void limpiar() {
        idActual = null;
        if (cbCliente.getItemCount() > 0) cbCliente.setSelectedIndex(0);
        if (cbRestaurante.getItemCount() > 0) cbRestaurante.setSelectedIndex(0);
        if (cbMesa.getItemCount() > 0) cbMesa.setSelectedIndex(0);
        txtFecha.setText(""); txtHora.setText(""); txtPersonas.setText("");
        lblStatus.setText(" ");
    }

    private FilaReservacion buscarPorId(Long id) {
        for (FilaReservacion r : filas) if (r.id.equals(id)) return r;
        return null;
    }
}