package Presentacion.Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Button extends JButton {
    private int radio = 30;

    // Colores (arriba, abajo) de cada estado
    private Color normalArriba = new Color(110, 128, 72);
    private Color normalAbajo = new Color(80, 95, 50);
    private Color hoverArriba = new Color(132, 150, 92);
    private Color hoverAbajo = new Color(98, 114, 62);
    private Color presionadoArriba = new Color(66, 78, 40);
    private Color presionadoAbajo = new Color(52, 62, 32);
    private Color colorBorde = new Color(184, 150, 92);
    private Color colorTexto = new Color(252, 249, 242);

    public Button(String texto) {
        super(texto);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setRolloverEnabled(true);
        setForeground(new Color(235, 238, 245));
        setFont(new Font("Arial", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(220, 45));

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

        g2.setPaint(new GradientPaint(0, 0, arriba, 0, h, abajo));
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, radio, radio));

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(1f));
        g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, w - 1, h - 1, radio, radio));

        g2.dispose();
        super.paintComponent(g);   // dibuja el texto encima
    }
}
