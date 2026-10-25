package Presentacion.Coordinador;

import Presentacion.FramePrincipal.FramePrincipal;
import Presentacion.Paneles.PanelInicio;

import javax.swing.*;

public class Coordinador {
    FramePrincipal framePrincipal;
    PanelInicio panelInicio;

    public void cambiarPanel(JPanel panel) {
        framePrincipal.setContentPane(panel);
        framePrincipal.revalidate();
        framePrincipal.repaint();
    }

    public void iniciarComponentes(){
        if (framePrincipal == null) {
            framePrincipal = new FramePrincipal(this);
        }
        framePrincipal.setVisible(true);
        mostrarPanelInicio(panelInicio);
    }

    public void mostrarPanelInicio(PanelInicio panelInicio){
        if(panelInicio == null){
            panelInicio = new PanelInicio(this);
        }
        cambiarPanel(panelInicio);
    }

}
