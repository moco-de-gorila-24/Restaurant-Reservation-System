package Presentacion.Coordinador;

import Presentacion.FramePrincipal.FramePrincipal;
import Presentacion.Paneles.PanelCliente;
import Presentacion.Paneles.PanelInicio;
import Presentacion.Paneles.PanelReservacion;
import Presentacion.Paneles.PanelRestaurante;

import javax.swing.*;

public class Coordinador {

    private FramePrincipal framePrincipal;

    public void iniciarComponentes() {
        if (framePrincipal == null) {
            framePrincipal = new FramePrincipal(this);
        }
        framePrincipal.setVisible(true);
        mostrarPanelInicio();
    }

    public void cambiarPanel(JPanel panel) {
        framePrincipal.setContentPane(panel);
        framePrincipal.revalidate();
        framePrincipal.repaint();
    }

    public void mostrarPanelInicio() {
        cambiarPanel(new PanelInicio(this));
    }

    //public void mostrarPanelRestaurante() {
    //    cambiarPanel(new PanelRestaurante(this));
    //}

    //public void mostrarPanelCliente() {
    //    cambiarPanel(new PanelCliente(this));
    //}

    //public void mostrarPanelReservacion() {
    //    cambiarPanel(new PanelReservacion(this));
    //}
}
