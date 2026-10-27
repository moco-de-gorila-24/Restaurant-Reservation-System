package Presentacion.Paneles;

import Entidades.Address;
import Entidades.DiningTable;
import Presentacion.Componentes.Button;
import Presentacion.Coordinador.Coordinador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Panel con funcionalidades CRUD de restaurante
 */
public class PanelRestaurante extends JPanel {

    private static final Pattern TELEFONO_PATTERN = Pattern.compile("\\d{10}");

    private final Coordinador coordinador;

    private JTextField txtNombre, txtTelefono, txtTipoCocina;
    private JTextField txtCalle, txtEstado, txtCP, txtCiudad, txtNumero;
    private JTextField txtCapMesa;

    //Mesas del restaurante
    private final List<DiningTable> mesasEnEdicion = new ArrayList<>();
    private DefaultListModel<String> modeloListaMesas;
    private JList<String> listaMesas;

    //Botones
    private JButton btnGuardar, btnActualizar, btnEliminar, btnBuscar, btnAgregarMesa, btnQuitarMesa;
    private Button btnLimpiar;

    private JLabel lblStatus;
    private Long idActual = null;

    private static class FilaRestaurante {
        Long id;
        String nombre, telefono, tipoCocina;
        Address address;
        List<DiningTable> mesas = new ArrayList<>();
    }

    private final List<FilaRestaurante> filas = new ArrayList<>();

    public PanelRestaurante(Coordinador coordinador) {
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

        JLabel titulo = new JLabel("Gestión de restaurantes");
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
                BorderFactory.createTitledBorder("Datos del Restaurante"),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Espaciado entre filas y columnas
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        int y = 0;

        gbc.gridy = y;
        gbc.gridx = 0; form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; txtNombre = new JTextField(15); form.add(txtNombre, gbc);

        gbc.gridx = 2; form.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 3; txtTelefono = new JTextField(12); form.add(txtTelefono, gbc);

        gbc.gridx = 4; form.add(new JLabel("Tipo de cocina:"), gbc);
        gbc.gridx = 5; txtTipoCocina = new JTextField(15); form.add(txtTipoCocina, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; form.add(new JLabel("Calle:"), gbc);
        gbc.gridx = 1; txtCalle = new JTextField(20); form.add(txtCalle, gbc);

        gbc.gridx = 2; form.add(new JLabel("Número:"), gbc);
        gbc.gridx = 3; txtNumero = new JTextField(5); form.add(txtNumero, gbc);

        gbc.gridx = 4; form.add(new JLabel("CP:"), gbc);
        gbc.gridx = 5; txtCP = new JTextField(8); form.add(txtCP, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; form.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; txtEstado = new JTextField(15); form.add(txtEstado, gbc);

        gbc.gridx = 2; form.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 3; txtCiudad = new JTextField(15); form.add(txtCiudad, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; gbc.gridwidth = 6; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(new JSeparator(), gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        y++;

        gbc.gridy = y;

        //Panel izquierdo
        JPanel panelMesa = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelMesa.setOpaque(false);
        panelMesa.add(new JLabel("Capacidad: "));
        txtCapMesa = new JTextField(4);
        panelMesa.add(txtCapMesa);
        btnAgregarMesa = boton("+", new Color(80, 130, 70), 100, 30);
        panelMesa.add(btnAgregarMesa);

        gbc.gridx = 0; gbc.gridwidth = 2;
        form.add(panelMesa, gbc);

        // Panel Derecho: Lista de mesas y botón de quitar
        JPanel panelListaMesas = new JPanel(new BorderLayout(5, 5));
        panelListaMesas.setOpaque(false);

        modeloListaMesas = new DefaultListModel<>();
        listaMesas = new JList<>(modeloListaMesas);
        JScrollPane scrollMesas = new JScrollPane(listaMesas);
        scrollMesas.setPreferredSize(new Dimension(250, 60)); // Tamaño horizontal
        panelListaMesas.add(scrollMesas, BorderLayout.CENTER);

        btnQuitarMesa = boton("Quitar mesa seleccionada", new Color(180, 90, 60), 180, 25);
        panelListaMesas.add(btnQuitarMesa, BorderLayout.SOUTH);

        gbc.gridx = 2; gbc.gridwidth = 4;
        form.add(panelListaMesas, gbc);

        //Listeners de mesas
        btnAgregarMesa.addActionListener(e -> agregarMesa());
        btnQuitarMesa .addActionListener(e -> quitarMesa());

        GridBagConstraints gbcWrapper = new GridBagConstraints();
        gbcWrapper.gridx = 0; gbcWrapper.gridy = 0;
        gbcWrapper.weightx = 0; gbcWrapper.weighty = 0;
        gbcWrapper.anchor = GridBagConstraints.CENTER;
        wrapper.add(form, gbcWrapper);

        //boton guardar
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

        // Grupo RUD
        JPanel grupo = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        grupo.setOpaque(false);

        btnActualizar = boton("Actualizar", new Color(70, 110, 160), 200, 40);
        btnEliminar = boton("Eliminar", new Color(180, 60, 60),  200, 40);
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

    //mesas
    private boolean agregarMesa() {
        try {
            int cap = Integer.parseInt(txtCapMesa.getText().trim());
            if (cap <= 0) { mostrarError("La capacidad debe ser mayor a 0."); return false; }
            DiningTable mesa = new DiningTable(mesasEnEdicion.size() + 1, cap);
            mesasEnEdicion.add(mesa);
            renumerarMesas();
            txtCapMesa.setText("");
            mostrarExito("Mesa agregada.");
            return true;
        } catch (NumberFormatException ex) {
            mostrarError("La capacidad debe ser numérica.");
            return false;
        }
    }

    private boolean quitarMesa() {
        int idx = listaMesas.getSelectedIndex();
        if (idx < 0) { mostrarError("Selecciona una mesa para quitar."); return false; }
        mesasEnEdicion.remove(idx);
        renumerarMesas();
        return true;
    }

    private void renumerarMesas() {
        for (int i = 0; i < mesasEnEdicion.size(); i++) {
            mesasEnEdicion.get(i).setNumber(i + 1);
        }
        modeloListaMesas.clear();
        for (DiningTable m : mesasEnEdicion) {
            modeloListaMesas.addElement(formatoMesa(m));
        }
    }

    private void cargarMesasEnLista(List<DiningTable> mesas) {
        mesasEnEdicion.clear();
        modeloListaMesas.clear();
        for (DiningTable m : mesas) {
            mesasEnEdicion.add(m);
            modeloListaMesas.addElement(formatoMesa(m));
        }
    }

    //CRUD
    private boolean guardar() {
        return idActual == null ? crear() : actualizar();
    }
    private boolean crear() {
        if(txtNombre.getText().trim().isEmpty()){
            mostrarError("El nombre no puede estar vacío.");
            return false;
        }
        if(txtTipoCocina.getText().trim().isEmpty()) {
            mostrarError("El tipo de cocina no puede estar vacío.");
            return false;
        }
        if(txtCalle.getText().trim().isEmpty()){
            mostrarError("La calle no puede estar vacía.");
            return false;
        }
        if(txtCP.getText().trim().isEmpty()){
            mostrarError("El CP no puede estar vacío.");
            return false;
        }

        String tel = txtTelefono.getText().trim();
        if(!tel.isEmpty() && !TELEFONO_PATTERN.matcher(tel).matches()) {
            mostrarError("El telefono debe tener 10 dígitos sin guiones (ej. 6444010101).");
            return false;
        }
        int cp, numDir;
        try {
            cp = Integer.parseInt(txtCP.getText().trim()); }
        catch (
                NumberFormatException ex) {
                mostrarError("El CP debe ser numérico.");
                return false;
        }
        try {
            numDir = Integer.parseInt(txtNumero.getText().trim());
        }
        catch (NumberFormatException ex) {
            mostrarError("El número de dirección debe ser numérico.");
            return false;
        }

        Address dir = new Address(txtCalle.getText().trim(), txtEstado.getText().trim(), cp, txtCiudad.getText().trim(), numDir);

        FilaRestaurante fila = new FilaRestaurante();
        fila.id = (long) (filas.size() + 1);
        fila.nombre = txtNombre.getText().trim();
        fila.telefono = tel;
        fila.tipoCocina = txtTipoCocina.getText().trim();
        fila.address = dir;
        fila.mesas = new ArrayList<>(mesasEnEdicion);
        filas.add(fila);
        limpiar();
        mostrarExito("Restaurante creado con ID " + fila.id + ".");
        return true;
    }

    private boolean actualizar() {
        FilaRestaurante fila = buscarPorId(idActual);
        if(fila == null) {
            mostrarError("No se encontró el restaurante a actualizar.");
            return false;
        }

        if(txtNombre.getText().trim().isEmpty()){
            mostrarError("El nombre no puede estar vacío.");
            return false;
        }

        if(txtTipoCocina.getText().trim().isEmpty()){
            mostrarError("El tipo de cocina no puede estar vacío.");
            return false;
        }

        if(txtCalle.getText().trim().isEmpty()){
            mostrarError("La calle no puede estar vacía.");
            return false;
        }

        String tel = txtTelefono.getText().trim();
        if (!tel.isEmpty() && !TELEFONO_PATTERN.matcher(tel).matches()) {
            mostrarError("El teléfono debe tener 10 dígitos sin guiones.");
            return false;
        }
        int cp, numDir;
        try {
            cp = Integer.parseInt(txtCP.getText().trim());
        }
        catch (NumberFormatException ex) {
            mostrarError("El CP debe ser numérico.");
            return false;
        }
        try {
            numDir = Integer.parseInt(txtNumero.getText().trim());
        }
        catch (NumberFormatException ex) {
            mostrarError("El número de dirección debe ser numérico.");
            return false;
        }

        fila.nombre = txtNombre.getText().trim();
        fila.telefono = tel;
        fila.tipoCocina = txtTipoCocina.getText().trim();
        fila.address.setStreet(txtCalle.getText().trim());
        fila.address.setState(txtEstado.getText().trim());
        fila.address.setPostCode(cp);
        fila.address.setCity(txtCiudad.getText().trim());
        fila.address.setNumber(numDir);
        fila.mesas = new ArrayList<>(mesasEnEdicion);
        limpiar();
        mostrarExito("Restaurante actualizado.");
        return true;
    }

    //acciones con diálogo

    private boolean accionActualizar() {
        FilaRestaurante sel = mostrarDialogoSeleccion("Seleccionar restaurante a actualizar", "Editar");
        if (sel == null) return false;
        idActual = sel.id;
        txtNombre.setText(sel.nombre);
        txtTelefono.setText(sel.telefono == null ? "" : sel.telefono);
        txtTipoCocina.setText(sel.tipoCocina);
        if (sel.address != null) {
            txtCalle.setText(sel.address.getStreet() == null ? "" : sel.address.getStreet());
            txtEstado.setText(sel.address.getState() == null ? "" : sel.address.getState());
            txtCP.setText(String.valueOf(sel.address.getPostCode()));
            txtCiudad.setText(sel.address.getCity() == null ? "" : sel.address.getCity());
            txtNumero.setText(String.valueOf(sel.address.getNumber()));
        }
        cargarMesasEnLista(sel.mesas);
        mostrarExito("Editando restaurante #" + sel.id + ". Modifica y presiona Guardar.");
        return true;
    }

    private boolean accionEliminar() {
        FilaRestaurante sel = mostrarDialogoSeleccion("Seleccionar restaurante a eliminar", "Eliminar");
        if (sel == null) return false;

        int r = JOptionPane.showConfirmDialog(this,
                "¿Eliminar restaurante #" + sel.id + " (" + sel.nombre + ")?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return false;

        filas.remove(sel);
        if (idActual != null && idActual.equals(sel.id)) limpiar();
        mostrarExito("Restaurante eliminado.");
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

    //diálogos modales
    private FilaRestaurante mostrarDialogoSeleccion(String titulo, String textoBotonOk) {
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

        final FilaRestaurante[] seleccion = {null};

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
                "Restaurantes registrados", Dialog.ModalityType.APPLICATION_MODAL);
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
        String[] cols = {"ID", "Nombre", "Teléfono", "Tipo cocina", "Calle", "Ciudad", "Mesas"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (FilaRestaurante f : filas) {
            m.addRow(new Object[]{
                    f.id, f.nombre,
                    f.telefono == null ? "" : f.telefono,
                    f.tipoCocina,
                    f.address == null ? "" : f.address.getStreet(),
                    f.address == null ? "" : f.address.getCity(),
                    f.mesas.size() + " mesa(s)"
            });
        }
        return m;
    }

    //limpiar
    private void limpiar() {
        idActual = null;
        txtNombre.setText(""); txtTelefono.setText(""); txtTipoCocina.setText("");
        txtCalle.setText(""); txtEstado.setText(""); txtCP.setText("");
        txtCiudad.setText(""); txtNumero.setText("");
        txtCapMesa.setText("");
        mesasEnEdicion.clear();
        modeloListaMesas.clear();
        lblStatus.setText(" ");
    }

    private FilaRestaurante buscarPorId(Long id) {
        for (FilaRestaurante r : filas) if (r.id.equals(id)) return r;
        return null;
    }
}