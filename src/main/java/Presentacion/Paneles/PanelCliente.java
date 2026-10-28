package Presentacion.Paneles;

import DAO.CustomerDAO;
import DTO.CustomerDTO;
import Interfaces.ICustomerDAO;
import Presentacion.Componentes.Button;
import Presentacion.Coordinador.Coordinador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Panel con funcionalidades CRUD de cliente
 */
public class PanelCliente extends JPanel {

    private static final Pattern TELEFONO_PATTERN = Pattern.compile("\\d{10}");
    private static final Pattern EMAIL_PATTERN    = Pattern.compile("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    private final Coordinador coordinador;

    private JTextField txtNombre, txtEmail, txtTelefono, txtPoints, txtLevel;
    private JCheckBox chkPremium;
    private DefaultListModel<String> modeloTelefonos;
    private JList<String> listaTelefonos;

    private JButton btnGuardar, btnActualizar, btnEliminar, btnBuscar, btnAgregarTel, btnQuitarTel;
    private Button btnLimpiar;

    private JLabel lblStatus;
    private Long idActual = null;
    
    private final ICustomerDAO clienteDAO = new CustomerDAO();

    private static class FilaCliente {
        Long id;
        String nombre, email;
        List<String> telefonos = new ArrayList<>();
        boolean premium;
        int points, level;
    }

    private final List<FilaCliente> filas = new ArrayList<>();

    public PanelCliente(Coordinador coordinador) {
        this.coordinador = coordinador;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 235, 220));
        construirUI();
        cargarClientes();
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

        JLabel titulo = new JLabel("Gestión de clientes");
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
                BorderFactory.createTitledBorder("Datos del Cliente"),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        int y = 0;

        gbc.gridy = y;
        gbc.gridx = 0; form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; txtNombre = new JTextField(15); form.add(txtNombre, gbc);

        gbc.gridx = 2; form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3; txtEmail = new JTextField(20); form.add(txtEmail, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(new JSeparator(), gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        y++;

        gbc.gridy = y;
        JPanel panelTel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelTel.setOpaque(false);
        panelTel.add(new JLabel("Tel:"));
        txtTelefono = new JTextField(10);
        panelTel.add(txtTelefono);
        btnAgregarTel = boton("Agregar teléfono", new Color(80, 130, 70), 140, 30);
        panelTel.add(btnAgregarTel);

        gbc.gridx = 0; gbc.gridwidth = 2;
        form.add(panelTel, gbc);

        JPanel panelListaTel = new JPanel(new BorderLayout(5, 5));
        panelListaTel.setOpaque(false);

        modeloTelefonos = new DefaultListModel<>();
        listaTelefonos = new JList<>(modeloTelefonos);
        JScrollPane scrollTel = new JScrollPane(listaTelefonos);
        scrollTel.setPreferredSize(new Dimension(250, 60));
        panelListaTel.add(scrollTel, BorderLayout.CENTER);

        btnQuitarTel = boton("Quitar teléfono", new Color(180, 90, 60), 180, 25);
        panelListaTel.add(btnQuitarTel, BorderLayout.SOUTH);

        gbc.gridx = 2; gbc.gridwidth = 2;
        form.add(panelListaTel, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; gbc.gridwidth = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        form.add(new JSeparator(), gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        y++;

        gbc.gridy = y;
        gbc.gridx = 0; gbc.gridwidth = 2;
        chkPremium = new JCheckBox("Cliente Premium");
        chkPremium.setOpaque(false);
        chkPremium.setFont(new Font("Arial", Font.BOLD, 13));
        form.add(chkPremium, gbc);
        y++;

        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridx = 0; form.add(new JLabel("Puntos:"), gbc);
        gbc.gridx = 1; txtPoints = new JTextField(10); form.add(txtPoints, gbc);

        gbc.gridx = 2; form.add(new JLabel("Nivel:"), gbc);
        gbc.gridx = 3; txtLevel = new JTextField(10); form.add(txtLevel, gbc);
        y++;

        txtPoints.setEnabled(false);
        txtLevel.setEnabled(false);
        chkPremium.addActionListener(e -> {
            txtPoints.setEnabled(chkPremium.isSelected());
            txtLevel.setEnabled(chkPremium.isSelected());
        });

        btnAgregarTel.addActionListener(e -> agregarTelefono());
        btnQuitarTel.addActionListener(e -> quitarTelefono());

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

    private boolean agregarTelefono() {
        String tel = txtTelefono.getText().trim();
        if (!TELEFONO_PATTERN.matcher(tel).matches()) {
            mostrarError("Teléfono inválido. Debe tener 10 dígitos sin guiones (ej. 6444010101).");
            return false;
        }
        modeloTelefonos.addElement(tel);
        txtTelefono.setText("");
        mostrarExito("Teléfono agregado.");
        return true;
    }

    private boolean quitarTelefono() {
        int idx = listaTelefonos.getSelectedIndex();
        if (idx < 0) {
            mostrarError("Selecciona un teléfono para quitar.");
            return false;
        }
        modeloTelefonos.remove(idx);
        return true;
    }

    private List<String> extraerTelefonos() {
        List<String> tel = new ArrayList<>();
        for (int i = 0; i < modeloTelefonos.size(); i++) tel.add(modeloTelefonos.get(i));
        return tel;
    }

    private boolean guardar() {
        return idActual == null ? crear() : actualizar();
    }

    private boolean crear() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        
        if (nombre.isEmpty() || nombre.length() > 50) {
            mostrarError("El nombre es obligatorio y no debe superar 50 caracteres.");
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            mostrarError("Email inválido. Formato esperado: usuario@dominio.com");
            return false;
        }

        int puntos = 0;
        int nivel = 1;

        if (chkPremium.isSelected()) {
            try {
                puntos = Integer.parseInt(txtPoints.getText().trim());
                nivel = Integer.parseInt(txtLevel.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarError("Los puntos y el nivel deben ser numéricos.");
                return false;
            }

            if (puntos < 0) {
                mostrarError("Los puntos no pueden ser negativos.");
                return false;
            }

            if (nivel < 1 || nivel > 5) {
                mostrarError("El nivel debe estar entre 1 y 5.");
                return false;
            }
        }

        CustomerDTO dto = new CustomerDTO();
        dto.setName(nombre);
        dto.setEmail(email);
        dto.setTelephone(extraerTelefonos());
        dto.setPremium(chkPremium.isSelected());
        dto.setPoints(puntos);
        dto.setLevel(nivel);

        String resultado = clienteDAO.guardar(dto);

        cargarClientes();

        if (esError(resultado)) {
            mostrarError(resultado);
            return false;
        }

        limpiar();
        mostrarExito(resultado);
        return true;
    }

    private boolean actualizar() {
        if (idActual == null) {
            mostrarError("Primero selecciona un cliente para actualizar.");
            return false;
        }

        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || nombre.length() > 50) {
            mostrarError("El nombre es obligatorio y no debe superar 50 caracteres.");
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            mostrarError("Email inválido.");
            return false;
        }

        int puntos = 0;
        int nivel = 1;

        if (chkPremium.isSelected()) {
            try {
                puntos = Integer.parseInt(txtPoints.getText().trim());
                nivel = Integer.parseInt(txtLevel.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarError("Los puntos y el nivel deben ser numéricos.");
                return false;
            }

            if (puntos < 0 || nivel < 1 || nivel > 5) {
                mostrarError("Puntos inválidos o nivel fuera del rango 1–5.");
                return false;
            }
        }

        CustomerDTO dto = new CustomerDTO();
        dto.setName(nombre);
        dto.setEmail(email);
        dto.setTelephone(extraerTelefonos());
        dto.setPremium(chkPremium.isSelected());
        dto.setPoints(puntos);
        dto.setLevel(nivel);

        String resultado = clienteDAO.actualizar(idActual, dto);

        cargarClientes();

        if (esError(resultado)) {
            mostrarError(resultado);
            return false;
        }

        limpiar();
        mostrarExito(resultado);
        return true;
    }

    private boolean accionActualizar() {
        FilaCliente sel = mostrarDialogoSeleccion("Seleccionar cliente a actualizar", "Editar");
        if (sel == null) return false;

        idActual = sel.id;
        txtNombre.setText(sel.nombre);
        txtEmail.setText(sel.email);
        modeloTelefonos.clear();
        for (String t : sel.telefonos) modeloTelefonos.addElement(t);
        chkPremium.setSelected(sel.premium);
        txtPoints.setEnabled(sel.premium);
        txtLevel.setEnabled(sel.premium);
        txtPoints.setText(sel.premium ? String.valueOf(sel.points) : "");
        txtLevel.setText(sel.premium ? String.valueOf(sel.level) : "");
        mostrarExito("Editando cliente #" + sel.id + ". Modifica y presiona Guardar.");
        return true;
    }

    private boolean accionEliminar() {
        FilaCliente sel = mostrarDialogoSeleccion("Seleccionar cliente a eliminar", "Eliminar");
        if (sel == null) return false;

        int r = JOptionPane.showConfirmDialog(this,
                "¿Eliminar cliente #" + sel.id + " (" + sel.nombre + ")?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) return false;

        String resultado = clienteDAO.eliminar(sel.id);
        cargarClientes();

        if (esError(resultado)) {
            mostrarError(resultado);
            return false;
        }

        if (idActual != null && idActual.equals(sel.id)) {
            limpiar();
        }

        mostrarExito(resultado);
        return true;
    }

    private boolean accionBuscar() {
        cargarClientes();
        
        if (filas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros disponibles.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        mostrarDialogoBusqueda();
        return true;
    }

    private FilaCliente mostrarDialogoSeleccion(String titulo, String textoBotonOk) {
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

        final FilaCliente[] seleccion = {null};

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
                "Clientes registrados", Dialog.ModalityType.APPLICATION_MODAL);
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
        String[] cols = {"ID", "Nombre", "Email", "Teléfonos", "Premium", "Puntos", "Nivel"};
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (FilaCliente c : filas) {
            m.addRow(new Object[]{
                    c.id, c.nombre, c.email,
                    String.join(", ", c.telefonos),
                    c.premium ? "Sí" : "No",
                    c.premium ? c.points : "-",
                    c.premium ? c.level  : "-"
            });
        }
        return m;
    }

    private void limpiar() {
        idActual = null;
        txtNombre.setText(""); txtEmail.setText(""); txtTelefono.setText("");
        modeloTelefonos.clear();
        chkPremium.setSelected(false);
        txtPoints.setEnabled(false); txtLevel.setEnabled(false);
        txtPoints.setText(""); txtLevel.setText("");
        lblStatus.setText(" ");
    }

    private FilaCliente buscarPorId(Long id) {
        for (FilaCliente c : filas) if (c.id.equals(id)) return c;
        return null;
    }
    
    private void cargarClientes() {
        try {
            List<CustomerDTO> clientes = clienteDAO.consultarTodos();

            filas.clear();

            if (clientes != null) {
                for (CustomerDTO dto : clientes) {
                    FilaCliente fila = new FilaCliente();

                    fila.id = dto.getId();
                    fila.nombre = dto.getName();
                    fila.email = dto.getEmail();

                    if (dto.getTelephone() != null) {
                        fila.telefonos = new ArrayList<>(dto.getTelephone());
                    }

                    fila.premium = dto.isPremium();
                    fila.points = dto.getPoints();
                    fila.level = dto.getLevel();

                    filas.add(fila);
                }
            }
        } catch (Exception ex) {
            mostrarError("No se pudieron cargar los clientes: " + ex.getMessage());
        }
    }
    
    private boolean esError(String mensaje) {
        if (mensaje == null) {
            return true;
        }

        String texto = mensaje.toLowerCase();
        return texto.contains("error") || texto.contains("no se pudo") || texto.contains("inválido") || texto.contains("invalido") || texto.contains("ya existe") || texto.contains("no encontrado");
    }
}