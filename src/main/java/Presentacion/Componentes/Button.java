package Presentacion.Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Button extends JButton {
    private int radio = 30;

    // Colores (arriba, abajo) de cada estado
    private Color normalArriba = new Color(52, 60, 82);
    private Color normalAbajo = new Color(30, 35, 52);
    private Color hoverArriba = new Color(70, 80, 108);
    private Color hoverAbajo = new Color(42, 49, 72);
    private Color presionadoArriba = new Color(24, 28, 42);
    private Color presionadoAbajo = new Color(18, 21, 32);
    private Color colorBorde = new Color(90, 102, 135);

    public Button(String texto) {
        super(texto);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setRolloverEnabled(true);
        setForeground(new Color(235, 238, 245));
        setFont(new Font("Arial", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color arriba, abajo;
        if (getModel().isPressed()) {
            arriba = presionadoArriba;
            abajo = presionadoAbajo;
        } else if (getModel().isRollover()) {
            arriba = hoverArriba;
            abajo = hoverAbajo;
        } else {
            arriba = normalArriba;
            abajo = normalAbajo;
        }

        int w = getWidth();
        int h = getHeight();

        // Fondo con degradado vertical
        g2.setPaint(new GradientPaint(0, 0, arriba, 0, h, abajo));
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, radio, radio));

        // Borde fino
        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(1f));
        g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, w - 1, h - 1, radio, radio));

        g2.dispose();
        super.paintComponent(g);   // dibuja el texto encima
    }
}
