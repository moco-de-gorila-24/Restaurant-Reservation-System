package Presentacion.Paneles;

import Presentacion.Componentes.Button;
import Presentacion.Coordinador.Coordinador;

import javax.swing.*;
import java.awt.*;

/**
 * Menu principal del sistema que navega a los modulos del restaurante, cliente y reservaciones
 */
public class PanelInicio extends JPanel {

    private final Coordinador coordinador;
    private Image imagen;
    private Button buttonReservacion, buttonCliente, buttonRestaurante;

    public PanelInicio(Coordinador coordinador) {
        this.coordinador = coordinador;
        imagen = new ImageIcon("src/main/resources/img/fondo.jpg").getImage();
        setLayout(new BorderLayout());

        JPanel panelNorte = new JPanel();
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelNorte.setOpaque(false);
        panelCentro.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelTitulo = new JLabel("Seleccione la operación que desee realizar");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        buttonRestaurante = new Button("Restaurante");
        buttonCliente     = new Button("Cliente");
        buttonReservacion = new Button("Reservación");

        //buttonRestaurante.addActionListener(e -> coordinador.mostrarPanelRestaurante());
        //buttonCliente    .addActionListener(e -> coordinador.mostrarPanelCliente());
        //buttonReservacion.addActionListener(e -> coordinador.mostrarPanelReservacion());

        gbc.gridx = 0; gbc.gridy = 0;
        panelCentro.add(buttonRestaurante, gbc);
        gbc.gridy = 1;
        panelCentro.add(buttonCliente, gbc);
        gbc.gridy = 2;
        panelCentro.add(buttonReservacion, gbc);

        panelNorte.add(labelTitulo);
        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        g2.dispose();
    }
}
