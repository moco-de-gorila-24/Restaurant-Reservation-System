/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import DAO.CustomerDAO;
import DAO.DiningTableDAO;
import DAO.ReservationDAO;
import DAO.RestaurantDAO;
import DTO.CustomerDTO;
import DTO.DiningTableDTO;
import DTO.ReservationDTO;
import DTO.RestaurantDTO;
import Entidades.DiningTable;
import Interfaces.ICustomerDAO;
import Interfaces.IDiningTableDAO;
import Interfaces.IReservationDAO;
import Interfaces.IRestaurantDAO;
import Mappers.DiningTableMapper;
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
 * Panel de interfaz gráfica para la gestión (CRUD) de reservaciones.
 * Incluye la lógica visual para el feedback de puntos y niveles de clientes Premium.
 * 
 * @author Andres
 */
public class PanelReservacion extends JPanel {

    private static final DateTimeFormatter FECHA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA_FMT  = DateTimeFormatter.ofPattern("HH:mm");

    private final Coordinador coordinador;
    private final ICustomerDAO clienteDAO = new CustomerDAO();
    private final IRestaurantDAO restauranteDAO = new RestaurantDAO();
    private final IDiningTableDAO mesaDAO = new DiningTableDAO();
    private final IReservationDAO reservacionDAO = new ReservationDAO();

    private List<CustomerDTO> clientes = new ArrayList<>();
    private List<RestaurantDTO> restaurantes = new ArrayList<>();
    private List<DiningTableDTO> mesas = new ArrayList<>();
    private List<ReservationDTO> reservaciones = new ArrayList<>();

    private JComboBox<CustomerDTO> cbCliente;
    private JComboBox<RestaurantDTO> cbRestaurante;
    private JComboBox<DiningTableDTO> cbMesa;
    private JTextField txtFecha, txtHora, txtPersonas;

    private JButton btnGuardar, btnActualizar, btnEliminar, btnBuscar;
    private Button btnLimpiar;

    private JLabel lblStatus;
    private Long idActual = null;

    public PanelReservacion(Coordinador coordinador) {
        this.coordinador = coordinador;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(240, 235, 220));
        construirUI();
        
        cargarDatosIniciales();
        cbRestaurante.addActionListener(e -> cargarMesas());
    }

    /**
     * Construye los componentes visuales del panel.
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
     * Construye los componentes visuales del panel.
     */
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

    /**
     * Construye el formulario con los ComboBox y campos de fecha/hora.
     * @return Panel del formulario.
     */
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

        cbCliente.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof CustomerDTO) {
                    CustomerDTO cliente = (CustomerDTO) value;
                    setText(cliente.getName());
                }
                return this;
            }
        });

        cbRestaurante.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof RestaurantDTO) {
                    RestaurantDTO restaurante = (RestaurantDTO) value;
                    setText(restaurante.getName());
                }

                return this;
            }
        });

        cbMesa.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof DiningTableDTO) {
                    DiningTableDTO mesa = (DiningTableDTO) value;
                    setText("Mesa " + mesa.getNumber() + " (capacidad: " + mesa.getCapacity() + ")");
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
    
    /**
     * Construye la barra de botones inferior.
     * @return Panel de botones.
     */
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
    
    /**
     * Helper para crear botones.
     * @return JButton configurado.
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
     * @param msg Mensaje.
     */
    private void mostrarError(String msg) {
        lblStatus.setForeground(new Color(190, 40, 40));
        lblStatus.setText("⚠ " + msg);
    }

    /**
     * Muestra un mensaje de éxito en la barra de estado.
     * @param msg Mensaje.
     */
    private void mostrarExito(String msg) {
        lblStatus.setForeground(new Color(40, 140, 40));
        lblStatus.setText("✔ " + msg);
    }

    /**
     * Formatea los datos de una mesa para mostrarlos en texto.
     * @param m Entidad mesa.
     * @return String formateado.
     */
    private String formatoMesa(DiningTable m) {
        return "Mesa " + m.getNumber() + " (cap " + m.getCapacity() + ")";
    }
    
    /**
     * Delega la acción de guardar según si es creación o actualización.
     * @return true si la operación fue exitosa.
     */
    private boolean guardar() {
        return idActual == null ? crear() : actualizar();
    }

    /**
     * Crea una nueva reservación.
     * Incluye lógica de Feedback Visual: Si el cliente es Premium, al finalizar 
     * la reservación exitosamente, se calcula la diferencia de puntos antes y después 
     * y se muestra un JOptionPane indicando al usuario cuántos puntos ganó/perdió 
     * y si subió/bajó de nivel.
     * @return true si se guardó correctamente.
     */
    private boolean crear() {
        ReservationDTO reservacion = obtenerDatosFormulario();

        if (reservacion == null) {
            return false;
        }
        
        CustomerDTO cliente = clienteDAO.consultarPorId(reservacion.getCustomerId());
        String resultado = reservacionDAO.guardar(reservacion);
        
        
        if (resultado.startsWith("Reservación guardada correctamente")) {
            if (cliente != null && cliente.isPremium()) {
                CustomerDTO clienteDespues = clienteDAO.consultarPorId(reservacion.getCustomerId());              
                if (clienteDespues != null) {
                    int diferenciaPuntos = clienteDespues.getPoints() - cliente.getPoints();
                    String mensaje = "Reservación guardada exitosamente.\n\n";
                    
                    if (diferenciaPuntos > 0) {
                        mensaje += "Ganaste " + diferenciaPuntos + " puntos\n";
                    } else if (diferenciaPuntos < 0) {
                        mensaje += "Perdiste " + Math.abs(diferenciaPuntos) + " puntos (dejaste espacios vacios).\n";
                    }
                    
                    if (clienteDespues.getLevel() > cliente.getLevel()) {
                        mensaje += "Subiste al nivel " + clienteDespues.getLevel() + ".\n";
                    } else if (clienteDespues.getLevel() < cliente.getLevel()) {
                        mensaje += "Bajaste al nivel" + clienteDespues.getLevel() + ".\n";
                    }
                    
                    mensaje += "\nPuntos actuales: " + clienteDespues.getPoints() + 
                               "\nNivel actual: " + clienteDespues.getLevel();
                    
                    JOptionPane.showMessageDialog(this, mensaje, "Reservacion premium realizada con exito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            cargarReservaciones();
            limpiar();
            mostrarExito(resultado);
            return true;
        }

        mostrarError(resultado);
        return false;
    }
    
    /**
     * Actualiza una reservación existente, aplicando la misma lógica
     * visual para clientes Premium que el método crear().
     * @return true si se actualizó correctamente.
     */
    private boolean actualizar() {
        if (idActual == null) {
            mostrarError("Primero selecciona una reservación para actualizar.");
            return false;
        }

        ReservationDTO reservacion = obtenerDatosFormulario();

        if (reservacion == null) {
            return false;
        }
        CustomerDTO cliente = clienteDAO.consultarPorId(reservacion.getCustomerId());
        String resultado = reservacionDAO.actualizar(idActual, reservacion);

        if (resultado.startsWith("Reservación actualizada correctamente")) {
            if (cliente != null && cliente.isPremium()) {
                CustomerDTO clienteDespues = clienteDAO.consultarPorId(reservacion.getCustomerId());              
                if (clienteDespues != null) {
                    int diferenciaPuntos = clienteDespues.getPoints() - cliente.getPoints();
                    String mensaje = "Reservación actualizada con exito.\n\n";
                    
                    if (diferenciaPuntos > 0) {
                        mensaje += "Ganaste " + diferenciaPuntos + " puntos\n";
                    } else if (diferenciaPuntos < 0) {
                        mensaje += "Perdiste " + Math.abs(diferenciaPuntos) + " puntos (dejaste espacios vacios).\n";
                    }
                    
                    if (clienteDespues.getLevel() > cliente.getLevel()) {
                        mensaje += "Subiste al nivel " + clienteDespues.getLevel() + ".\n";
                    } else if (clienteDespues.getLevel() < cliente.getLevel()) {
                        mensaje += "Bajaste al nivel" + clienteDespues.getLevel() + ".\n";
                    }
                    
                    mensaje += "\nPuntos actuales: " + clienteDespues.getPoints() + 
                               "\nNivel actual: " + clienteDespues.getLevel();
                    
                    JOptionPane.showMessageDialog(this, mensaje, "Reservacion premium realizada con exito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            cargarReservaciones();
            limpiar();
            mostrarExito(resultado);
            return true;
        }

        mostrarError(resultado);
        return false;
    }

    /**
     * Valida que el número de personas no exceda la capacidad ni deje espacios vacíos no permitidos.
     * @param mesa DTO de la mesa.
     * @param personas Número de personas.
     * @return true si es válido.
     */
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

    /**
     * Acción del botón Actualizar: carga una reservación seleccionada.
     * @return true si se cargó correctamente.
     */
    private boolean accionActualizar() {
        ReservationDTO sel = mostrarDialogoSeleccion("Seleccionar reservación a actualizar", "Editar");

        if (sel == null) {
            return false;
        }

        idActual = sel.getId();

        for (int i = 0; i < cbCliente.getItemCount(); i++) {
            CustomerDTO cliente = cbCliente.getItemAt(i);

            if (cliente.getId().equals(sel.getCustomerId())) {
                cbCliente.setSelectedIndex(i);
                break;
            }
        }

        boolean mesaEncontrada = false;

        for (RestaurantDTO restaurante : restaurantes) {
            List<DiningTableDTO> mesasRestaurante = mesaDAO.consultarPorRestaurante(restaurante.getId());

            for (DiningTableDTO mesa : mesasRestaurante) {
                if (mesa.getId().equals(sel.getTableId())) {
                    cbRestaurante.setSelectedItem(restaurante);
                    cbMesa.setSelectedItem(mesa);

                    mesaEncontrada = true;
                    break;
                }
            }

            if (mesaEncontrada) {
                break;
            }
        }

        if (!mesaEncontrada) {
            mostrarError("No se encontró la mesa de esta reservación.");
            idActual = null;
            return false;
        }

        txtFecha.setText(sel.getDate().format(FECHA_FMT));
        txtHora.setText(sel.getHour().format(HORA_FMT));
        txtPersonas.setText(String.valueOf(sel.getNumberPeople()));

        mostrarExito("Editando reservación #" + sel.getId() + ". Modifica los datos y presiona Guardar.");

        return true;
    }

    /**
     * Acción del botón Eliminar.
     * @return true si se eliminó correctamente.
     */
    private boolean accionEliminar() {
        ReservationDTO sel = mostrarDialogoSeleccion("Seleccionar reservación a eliminar", "Eliminar");

        if (sel == null) {
            return false;
        }

        int r = JOptionPane.showConfirmDialog(this, "¿Eliminar reservación #" + sel.getId() + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (r != JOptionPane.YES_OPTION) {
            return false;
        }

        String resultado = reservacionDAO.eliminar(sel.getId());

        if (resultado.startsWith("Reservación eliminada correctamente")) {
            cargarReservaciones();

            if (idActual != null && idActual.equals(sel.getId())) {
                limpiar();
            }

            mostrarExito(resultado);
            return true;
        }

        mostrarError(resultado);
        return false;
    }

    /**
     * Acción del botón Buscar.
     * @return true si hay registros y se abre el diálogo.
     */
    private boolean accionBuscar() {
        cargarReservaciones();

        if (reservaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay reservaciones registradas.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        mostrarDialogoBusqueda();
        return true;
    }

     /**
     * Muestra el diálogo modal de selección de reservaciones.
     * @return ReservationDTO seleccionada o null.
     */
    private ReservationDTO mostrarDialogoSeleccion(String titulo, String textoBotonOk) {
        cargarReservaciones();

        if (reservaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay reservaciones registradas.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        DefaultTableModel modelo = crearModeloTabla();
        JTable tabla = new JTable(modelo);

        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowSelectionInterval(0, 0);

        final ReservationDTO[] seleccion = {null};

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
                JOptionPane.showMessageDialog(dlg, "Selecciona un registro.");
            } else {
                Long id = (Long) modelo.getValueAt(fila, 0);
                seleccion[0] = reservacionDAO.consultarPorId(id);
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

    /**
     * Muestra el diálogo de búsqueda con la tabla de todas las reservaciones.
     */
    private void mostrarDialogoBusqueda() {
        cargarReservaciones();

        DefaultTableModel modelo = crearModeloTabla();
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);

        JDialog dlg = new JDialog(SwingUtilities.getWindowAncestor(this), "Reservaciones registradas", Dialog.ModalityType.APPLICATION_MODAL);

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
      * Genera el modelo de la tabla para las reservaciones.
      * @return DefaultTableModel.
      */
    private DefaultTableModel crearModeloTabla() {
         String[] columnas = {"ID", "Cliente", "Mesa", "Capacidad", "Fecha", "Hora", "Personas"};

         DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
             @Override
             public boolean isCellEditable(int fila, int columna) {
                 return false;
             }
         };

         for (ReservationDTO r : reservaciones) {
             modelo.addRow(new Object[]{r.getId(), r.getCustomerName(), r.getTableNumber(), r.getTableCapacity(), r.getDate(), r.getHour(), r.getNumberPeople()});
         }

         return modelo;
    }
    
    /**
     * Limpia los campos del formulario.
     */
    private void limpiar() {
        idActual = null;
        if (cbCliente.getItemCount() > 0) cbCliente.setSelectedIndex(0);
        if (cbRestaurante.getItemCount() > 0) cbRestaurante.setSelectedIndex(0);
        if (cbMesa.getItemCount() > 0) cbMesa.setSelectedIndex(0);
        txtFecha.setText(""); txtHora.setText(""); txtPersonas.setText("");
        lblStatus.setText(" ");
    }
    
    /**
     * Carga los clientes, restaurantes y mesas desde la BD para llenar los ComboBox.
     */
    private void cargarDatosIniciales() {
        try {
            clientes = clienteDAO.consultarTodos();
            cbCliente.removeAllItems();

            for (CustomerDTO cliente : clientes) {
                cbCliente.addItem(cliente);
            }

            restaurantes = restauranteDAO.consultarTodos();
            cbRestaurante.removeAllItems();

            for (RestaurantDTO restaurante : restaurantes) {
                cbRestaurante.addItem(restaurante);
            }

            cargarMesas();
            cargarReservaciones();

        } catch (Exception e) {
            mostrarError("Error al cargar los datos: " + e.getMessage());
        }
    }
    
    /**
     * Carga las mesas del restaurante seleccionado en el ComboBox de mesas.
     */
    private void cargarMesas() {

        cbMesa.removeAllItems();
        RestaurantDTO restaurante = (RestaurantDTO) cbRestaurante.getSelectedItem();

        if (restaurante == null) {
            return;
        }

        mesas = mesaDAO.consultarPorRestaurante(restaurante.getId());

        for (DiningTableDTO mesa : mesas) {
            cbMesa.addItem(mesa);
        }

        if (mesas.isEmpty()) {
            mostrarError("El restaurante no tiene mesas registradas.");
        }
    }
    
    /**
     * Obtiene el cliente seleccionado en el ComboBox.
     * @return CustomerDTO o null.
     */
    private CustomerDTO obtenerClienteSeleccionado() {
        return (CustomerDTO) cbCliente.getSelectedItem();
    }

    /**
     * Obtiene el restaurante seleccionado en el ComboBox.
     * @return RestaurantDTO o null.
     */
    private RestaurantDTO obtenerRestauranteSeleccionado() {
        return (RestaurantDTO) cbRestaurante.getSelectedItem();
    }

    /**
     * Obtiene la mesa seleccionada en el ComboBox.
     * @return DiningTableDTO o null.
     */
    private DiningTableDTO obtenerMesaSeleccionada() {
        return (DiningTableDTO) cbMesa.getSelectedItem();
    }
    
    /**
     * Extrae y valida todos los datos del formulario para construir un ReservationDTO.
     * @return ReservationDTO listo para guardar o null si hay errores de validación.
     */
    private ReservationDTO obtenerDatosFormulario() {
        CustomerDTO cliente = obtenerClienteSeleccionado();
        DiningTableDTO mesa = obtenerMesaSeleccionada();

        if (cliente == null) {
            mostrarError("Selecciona un cliente.");
            return null;
        }

        if (obtenerRestauranteSeleccionado() == null) {
            mostrarError("Selecciona un restaurante.");
            return null;
        }

        if (mesa == null) {
            mostrarError("Selecciona una mesa.");
            return null;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(txtFecha.getText().trim(), FECHA_FMT);
        } catch (DateTimeParseException ex) {
            mostrarError("Fecha inválida. Formato esperado: dd/MM/yyyy.");
            return null;
        }

        LocalTime hora;
        try {
            hora = LocalTime.parse(txtHora.getText().trim(), HORA_FMT);
        } catch (DateTimeParseException ex) {
            mostrarError("Hora inválida. Formato esperado: HH:mm.");
            return null;
        }

        int personas;
        try {
            personas = Integer.parseInt(txtPersonas.getText().trim());
        } catch (NumberFormatException ex) {
            mostrarError("El número de personas debe ser numérico.");
            return null;
        }

        if (!validarPersonas(mesa, personas)) {
            return null;
        }

        return new ReservationDTO(fecha, hora, personas, cliente.getId(), cliente.getName(), mesa.getId(), mesa.getNumber(), mesa.getCapacity());
    }

    /**
     * Valida si las personas caben en la mesa según las reglas básicas.
     * @param mesa DTO de la mesa.
     * @param personas Número de personas.
     * @return true si es válido.
     */
    private boolean validarPersonas(DiningTableDTO mesa, int personas) {
        if (personas <= 0) {
            mostrarError("El número de personas debe ser mayor a 0.");
            return false;
        }

        int capacidad = mesa.getCapacity();

        if (personas > capacidad) {
            mostrarError("El número de personas supera la capacidad de la mesa.");
            return false;
        }

        int minimo = Math.max(1, capacidad - 2);

        if (personas < minimo) {
            mostrarError("La mesa " + mesa.getNumber() + " requiere un mínimo de " + minimo + " personas.");
            return false;
        }

        return true;
    }
    
     
    /**
     * Recarga la lista de reservaciones desde la base de datos.
     */
    private void cargarReservaciones() {
        reservaciones = reservacionDAO.consultarTodos();
    }
}