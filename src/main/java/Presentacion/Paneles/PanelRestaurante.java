/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import DAO.AddressDAO;
import DAO.DiningTableDAO;
import DAO.RestaurantDAO;
import DTO.AddressDTO;
import DTO.DiningTableDTO;
import DTO.RestaurantDTO;
import Presentacion.Componentes.Button;
import Presentacion.Coordinador.Coordinador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Panel de interfaz gráfica para la gestión (CRUD) de restaurantes.
 * Incluye la creación de su dirección (1:1) y sus mesas asociadas (1:N).
 * 
 * @author Andres
 */
public class PanelRestaurante extends JPanel {

    private static final Pattern TELEFONO_PATTERN = Pattern.compile("\\d{10}");

    private final Coordinador coordinador;

    private JTextField txtNombre, txtTelefono, txtTipoCocina;
    private JTextField txtCalle, txtEstado, txtCP, txtCiudad, txtNumero;
    private JTextField txtCapMesa;

    //Mesas del restaurante
    private final List<DiningTableDTO> mesasEnEdicion = new ArrayList<>();
    private DefaultListModel<String> modeloListaMesas;
    private JList<String> listaMesas;

    //Botones
    private JButton btnGuardar, btnActualizar, btnEliminar, btnBuscar, btnAgregarMesa, btnQuitarMesa;
    private Button btnLimpiar;
    
    private final RestaurantDAO restaurantDAO = new RestaurantDAO();
    private final AddressDAO addressDAO = new AddressDAO();
    private final DiningTableDAO diningTableDAO = new DiningTableDAO();

    private JLabel lblStatus;
    private Long idActual = null;

    /**
     * Constructor del panel de restaurantes.
     * Configura el diseño base y construye los componentes de la interfaz.
     * 
     * @param coordinador Coordinador principal para la navegación.
     */
    public PanelRestaurante(Coordinador coordinador) {
        this.coordinador = coordinador;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 235, 220));
        construirUI();
    }
    
    /**
     * Construye y organiza los paneles principales (Header, Formulario y Botones).
     */
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

    /**
     * Construye el panel superior (Header) con el botón de volver y el título.
     * 
     * @return Panel del encabezado configurado.
     */
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

    /**
     * Construye el formulario que contiene los datos del restaurante, 
     * la dirección y la gestión de mesas.
     * 
     * @return Panel del formulario configurado y centrado.
     */
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
    /**
     * Construye la barra inferior de botones de acción (Actualizar, Eliminar, Buscar y Limpiar).
     * 
     * @return Panel con los botones distribuidos.
     */
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

    /**
     * Método auxiliar para instanciar y estilizar botones planos.
     * 
     * @param texto Texto a mostrar en el botón.
     * @param color Color de fondo.
     * @param w Ancho preferido.
     * @param h Alto preferido.
     * @return El JButton configurado.
     */
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

    /**
     * Muestra un mensaje de error en la barra de estado.
     * @param msg Mensaje de error.
     */
    private void mostrarError(String msg) {
        lblStatus.setForeground(new Color(190, 40, 40));
        lblStatus.setText(msg);
    }

    /**
     * Muestra un mensaje de éxito en la barra de estado.
     * @param msg Mensaje de confirmación.
     */
    private void mostrarExito(String msg) {
        lblStatus.setForeground(new Color(40, 140, 40));
        lblStatus.setText(msg);
    }

    /**
     * Formatea los datos de una mesa (DTO) en texto para ser mostrados en la lista.
     * @param m DTO de la mesa.
     * @return Cadena de texto formateada.
     */
    private String formatoMesa(DiningTableDTO m) {
        return "Mesa " + m.getNumber() + " (cap " + m.getCapacity() + ")";
    }

    /**
     * Agrega una nueva mesa a la lista de edición local (en memoria).
     * Valida que la capacidad ingresada sea numérica y mayor a cero.
     * 
     * @return true si la mesa fue agregada correctamente.
     */
    private boolean agregarMesa() {
        try {
            int cap = Integer.parseInt(txtCapMesa.getText().trim());
            if (cap <= 0) { mostrarError("La capacidad debe ser mayor a 0."); return false; }
            DiningTableDTO mesa = new DiningTableDTO(mesasEnEdicion.size() + 1, cap);
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

    /**
     * Quita la mesa actualmente seleccionada en la lista de edición.
     * @return true si se pudo quitar.
     */
    private boolean quitarMesa() {
        int idx = listaMesas.getSelectedIndex();
        if (idx < 0) { mostrarError("Selecciona una mesa para quitar."); return false; }
        mesasEnEdicion.remove(idx);
        renumerarMesas();
        return true;
    }

    /**
     * Renumera consecutivamente (1, 2, 3...) las mesas en la lista para 
     * evitar saltos en la numeración visual.
     */
    private void renumerarMesas() {
        for (int i = 0; i < mesasEnEdicion.size(); i++) {
            mesasEnEdicion.get(i).setNumber(i + 1);
        }
        modeloListaMesas.clear();
        for (DiningTableDTO m : mesasEnEdicion) {
            modeloListaMesas.addElement(formatoMesa(m));
        }
    }

    /**
     * Carga una lista de mesas desde la base de datos hacia el componente visual.
     * @param mesas Lista de DTOs de mesas.
     */
    private void cargarMesasEnLista(List<DiningTableDTO> mesas) {
        mesasEnEdicion.clear();
        modeloListaMesas.clear();
        for (DiningTableDTO m : mesas) {
            mesasEnEdicion.add(m);
            modeloListaMesas.addElement(formatoMesa(m));
        }
    }

    /**
     * Delega la acción de guardar. Si no hay un ID seleccionado, crea uno nuevo; 
     * de lo contrario, actualiza el existente.
     * 
     * @return true si la operación fue exitosa.
     */
    private boolean guardar() {
        return idActual == null ? crear() : actualizar();
    }
    
    /**
     * Crea un nuevo restaurante en la base de datos.
     * Sigue el orden de persistencia: 1. Dirección, 2. Restaurante, 3. Mesas.
     * 
     * @return true si todo se guardó correctamente.
     */
    private boolean crear() {
        if (!validarFormulario()) {
            return false;
        }

        AddressDTO address = obtenerDireccionFormulario();

        Long addressId = addressDAO.agregar(address);

        if (addressId == null) {
            mostrarError("No se pudo guardar la dirección.");
            return false;
        }

        RestaurantDTO restaurante = new RestaurantDTO(txtNombre.getText().trim(), txtTelefono.getText().trim(), txtTipoCocina.getText().trim(), addressId);

        Long restaurantId = restaurantDAO.guardar(restaurante);

        if (restaurantId == null) {
            mostrarError("No se pudo guardar el restaurante.");
            return false;
        }

        List<DiningTableDTO> mesasNuevas = new ArrayList<>();

        for (int i = 0; i < mesasEnEdicion.size(); i++) {
            DiningTableDTO mesa = new DiningTableDTO(i + 1, mesasEnEdicion.get(i).getCapacity(), restaurantId);
            mesasNuevas.add(mesa);
        }

        if (!mesasNuevas.isEmpty()) {
            String resultado = diningTableDAO.guardar(mesasNuevas);

            if (!resultado.startsWith("Se guardaron")) {
                mostrarError("El restaurante se guardó, pero ocurrió un problema al guardar las mesas: " + resultado);
                return false;
            }
        }

        limpiar();
        mostrarExito("Restaurante registrado correctamente. ID: " + restaurantId);
        return true;
    }
    
    
    /**
     * Acción ejecutada por el botón "Actualizar". Despliega el diálogo de selección 
     * y carga los datos del restaurante elegido en el formulario.
     * 
     * @return true si el restaurante fue seleccionado y cargado.
     */
    private boolean accionActualizar() {
        Long id = mostrarDialogoSeleccion("Seleccionar restaurante a actualizar", "Editar");

        if (id == null) {
            return false;
        }

        cargarRestaurante(id);
        return true;
    }

    /**
     * Acción ejecutada por el botón "Eliminar". Despliega el diálogo de selección, 
     * solicita confirmación y elimina el restaurante de la BD (junto con sus mesas y dirección por cascada).
     * 
     * @return true si el restaurante fue eliminado con éxito.
     */
    private boolean accionEliminar() {
        Long id = mostrarDialogoSeleccion("Seleccionar restaurante a eliminar", "Eliminar");

        if (id == null) {
            return false;
        }

        RestaurantDTO restaurante = restaurantDAO.consultarPorId(id);

        if (restaurante == null) {
            mostrarError("El restaurante ya no existe.");
            return false;
        }

        int respuesta = JOptionPane.showConfirmDialog(this, "¿Deseas eliminar el restaurante " + restaurante.getName() + " (ID: " + id + ")?\nTambién se eliminarán sus mesas y dirección.", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (respuesta != JOptionPane.YES_OPTION) {
            return false;
        }

        String resultado = restaurantDAO.eliminar(id);

        if (resultado.startsWith("Restaurante eliminado correctamente")) {
            if (idActual != null && idActual.equals(id)) {
                limpiar();
            }

            mostrarExito(resultado);
            return true;
        }

        mostrarError(resultado);
        return false;
    }

    /**
     * Acción ejecutada por el botón "Buscar". Abre un diálogo mostrando
     * la tabla con todos los restaurantes registrados.
     * 
     * @return true si hay registros disponibles y se abre el diálogo.
     */
    private boolean accionBuscar() {
        List<RestaurantDTO> restaurantes = restaurantDAO.consultarTodos();

        if (restaurantes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay restaurantes registrados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        mostrarDialogoBusqueda();
        return true;
    }

    /**
     * Muestra un diálogo modal con una tabla que contiene a todos los restaurantes.
     * Permite seleccionar uno y retorna su ID.
     * 
     * @param titulo Título de la ventana.
     * @param textoBotonOk Texto del botón de confirmación.
     * @return El ID del restaurante seleccionado, o null si se cancela.
     */
    private Long mostrarDialogoSeleccion(String titulo, String textoBotonOk) {
        DefaultTableModel modelo = crearModeloTabla();

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay restaurantes registrados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowSelectionInterval(0, 0);

        final Long[] seleccion = {null};

        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), titulo, Dialog.ModalityType.APPLICATION_MODAL);

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
                JOptionPane.showMessageDialog(dlg, "Selecciona un restaurante.");
                return;
            }

            seleccion[0] = (Long) tabla.getValueAt(fila, 0);
            dlg.dispose();
        });

        cancelar.addActionListener(e -> dlg.dispose());

        sur.add(ok);
        sur.add(cancelar);

        dlg.add(sur, BorderLayout.SOUTH);
        dlg.setVisible(true);

        return seleccion[0];
    }

    /**
     * Muestra un diálogo modal meramente informativo con la tabla de todos los restaurantes.
     */
    private void mostrarDialogoBusqueda() {
        DefaultTableModel modelo = crearModeloTabla();

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);

        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Restaurantes registrados", Dialog.ModalityType.APPLICATION_MODAL);

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

    /**
     * Genera el modelo de datos para las tablas, cruzando la información de 
     * restaurantes, direcciones y cantidad de mesas desde la base de datos.
     * 
     * @return DefaultTableModel listo para mostrarse en pantalla.
     */
    private DefaultTableModel crearModeloTabla() {
        String[] columnas = {"ID", "Nombre", "Teléfono", "Tipo de cocina", "Calle", "Ciudad", "Cantidad de mesas"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        List<RestaurantDTO> restaurantes = restaurantDAO.consultarTodos();

        for (RestaurantDTO restaurante : restaurantes) {
            AddressDTO direccion = addressDAO.consultarPorId(restaurante.getAddressId());

            List<DiningTableDTO> mesas = diningTableDAO.consultarPorRestaurante(restaurante.getId());

            modelo.addRow(new Object[]{
                restaurante.getId(),
                restaurante.getName(),
                restaurante.getTelephone() == null ? "" : restaurante.getTelephone(),
                restaurante.getKitchenType(),
                direccion == null ? "" : direccion.getStreet(),
                direccion == null ? "" : direccion.getCity(),
                mesas.size()
            });
        }

        return modelo;
    }

    /**
     * Limpia todos los campos de texto del formulario, vacía la lista de mesas
     * y reinicia el estado de edición (idActual = null).
     */
    private void limpiar() {
        idActual = null;

        txtNombre.setText("");
        txtTelefono.setText("");
        txtTipoCocina.setText("");
        txtCalle.setText("");
        txtEstado.setText("");
        txtCP.setText("");
        txtCiudad.setText("");
        txtNumero.setText("");
        txtCapMesa.setText("");

        mesasEnEdicion.clear();
        modeloListaMesas.clear();

        lblStatus.setText(" ");
    }
    
    /**
     * Actualiza un restaurante existente en la base de datos.
     * Actualiza la dirección, el restaurante y sincroniza las mesas.
     * 
     * @return true si la actualización fue exitosa.
     */
    private boolean actualizar() {
        if (idActual == null) {
            mostrarError("Selecciona un restaurante para actualizar.");
            return false;
        }

        if (!validarFormulario()) {
            return false;
        }

        RestaurantDTO restauranteActual = restaurantDAO.consultarPorId(idActual);

        if (restauranteActual == null) {
            mostrarError("El restaurante ya no existe en la base de datos.");
            return false;
        }

        AddressDTO direccion = obtenerDireccionFormulario();
        String resultadoDireccion = addressDAO.actualizar(restauranteActual.getAddressId(), direccion);

        if (!resultadoDireccion.startsWith("Dirección actualizada correctamente")) {
            mostrarError(resultadoDireccion);
            return false;
        }

        RestaurantDTO restaurante = new RestaurantDTO(txtNombre.getText().trim(), txtTelefono.getText().trim(), txtTipoCocina.getText().trim(), restauranteActual.getAddressId());
        String resultadoRestaurante = restaurantDAO.actualizar(idActual, restaurante);

        if (!resultadoRestaurante.startsWith("Restaurante actualizado correctamente")) {
            mostrarError(resultadoRestaurante);
            return false;
        }

        if (!sincronizarMesas(idActual)) {
            return false;
        }

        Long idGuardado = idActual;

        limpiar();
        mostrarExito("Restaurante #" + idGuardado + " actualizado correctamente.");

        return true;
    }
    
    /**
     * Sincroniza las mesas del formulario con las de la base de datos.
     * Elimina las mesas que ya no están en la lista, actualiza las modificadas 
     * y guarda las nuevas.
     * 
     * @param restaurantId ID del restaurante al que pertenecen las mesas.
     * @return true si la sincronización fue exitosa.
     */
    private boolean sincronizarMesas(Long restaurantId) {
        List<DiningTableDTO> mesasBD = diningTableDAO.consultarPorRestaurante(restaurantId);

        for (DiningTableDTO mesaBD : mesasBD) {
            boolean existe = false;

            for (DiningTableDTO mesaFormulario : mesasEnEdicion) {
                if (mesaFormulario.getId() != null && mesaFormulario.getId().equals(mesaBD.getId())) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                String resultado = diningTableDAO.eliminar(mesaBD.getId());

                if (!resultado.startsWith("Mesa eliminada correctamente")) {
                    mostrarError(resultado);
                    return false;
                }
            }
        }

        List<DiningTableDTO> mesasNuevas = new ArrayList<>();

        for (int i = 0; i < mesasEnEdicion.size(); i++) {
            DiningTableDTO mesa = mesasEnEdicion.get(i);
            mesa.setNumber(i + 1);
            mesa.setRestaurantId(restaurantId);

            if (mesa.getId() == null) {
                mesasNuevas.add(mesa);
            } else {
                String resultado = diningTableDAO.actualizar(mesa.getId(), mesa);

                if (!resultado.startsWith("Mesa actualizada correctamente")) {
                    mostrarError(resultado);
                    return false;
                }
            }
        }

        if (!mesasNuevas.isEmpty()) {
            String resultado = diningTableDAO.guardar(mesasNuevas);

            if (!resultado.startsWith("Se guardaron")) {
                mostrarError(resultado);
                return false;
            }
        }

        return true;
    }

    /**
     * Carga los datos de un restaurante (junto con su dirección y mesas)
     * desde la base de datos hacia los campos del formulario para su edición.
     * 
     * @param id ID del restaurante a cargar.
     */
    private void cargarRestaurante(Long id) {
        RestaurantDTO restaurante = restaurantDAO.consultarPorId(id);

        if (restaurante == null) {
            mostrarError("No se encontró el restaurante.");
            return;
        }

        AddressDTO direccion = addressDAO.consultarPorId(restaurante.getAddressId());

        if (direccion == null) {
            mostrarError("No se encontró la dirección del restaurante.");
            return;
        }

        List<DiningTableDTO> mesas = diningTableDAO.consultarPorRestaurante(id);

        idActual = restaurante.getId();

        txtNombre.setText(restaurante.getName());
        txtTelefono.setText(restaurante.getTelephone() == null ? "" : restaurante.getTelephone());
        txtTipoCocina.setText(restaurante.getKitchenType());

        txtCalle.setText(direccion.getStreet());
        txtEstado.setText(direccion.getState());
        txtCP.setText(String.valueOf(direccion.getPostCode()));
        txtCiudad.setText(direccion.getCity());
        txtNumero.setText(String.valueOf(direccion.getNumber()));

        cargarMesasEnLista(mesas);

        mostrarExito("Editando restaurante #" + idActual + ". Modifica los datos y presiona Guardar.");
    }
    
    /**
     * Valida que todos los campos del formulario cumplan con las reglas 
     * de longitud y formato definidas por el sistema.
     * 
     * @return true si todos los datos son válidos.
     */
    private boolean validarFormulario() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre no puede estar vacío.");
            return false;
        }

        if (txtNombre.getText().trim().length() > 30) {
            mostrarError("El nombre no puede superar los 30 caracteres.");
            return false;
        }

        if (txtTipoCocina.getText().trim().isEmpty()) {
            mostrarError("El tipo de cocina no puede estar vacío.");
            return false;
        }

        if (txtTipoCocina.getText().trim().length() > 100) {
            mostrarError("El tipo de cocina no puede superar los 100 caracteres.");
            return false;
        }

        String telefono = txtTelefono.getText().trim();

        if (!telefono.isEmpty() && !TELEFONO_PATTERN.matcher(telefono).matches()) {
            mostrarError("El teléfono debe contener exactamente 10 dígitos.");
            return false;
        }

        if (txtCalle.getText().trim().isEmpty()) {
            mostrarError("La calle no puede estar vacía.");
            return false;
        }

        if (txtCalle.getText().trim().length() > 50) {
            mostrarError("La calle no puede superar los 50 caracteres.");
            return false;
        }

        if (txtEstado.getText().trim().isEmpty()) {
            mostrarError("El estado no puede estar vacío.");
            return false;
        }

        if (txtCiudad.getText().trim().isEmpty()) {
            mostrarError("La ciudad no puede estar vacía.");
            return false;
        }

        try {
            int cp = Integer.parseInt(txtCP.getText().trim());

            if (cp < 0 || cp > 99999) {
                mostrarError("El código postal debe tener máximo 5 dígitos.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El código postal debe ser numérico.");
            return false;
        }

        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());

            if (numero <= 0) {
                mostrarError("El número de dirección debe ser mayor a cero.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El número de dirección debe ser numérico.");
            return false;
        }

        return true;
    }
    
    /**
     * Extrae y construye un objeto AddressDTO a partir de los campos de texto
     * de la dirección en el formulario.
     * 
     * @return Un AddressDTO con los datos ingresados.
     */
    private AddressDTO obtenerDireccionFormulario() {
        return new AddressDTO(txtCalle.getText().trim(), txtEstado.getText().trim(), Integer.parseInt(txtCP.getText().trim()), txtCiudad.getText().trim(), Integer.parseInt(txtNumero.getText().trim()));
    }
}