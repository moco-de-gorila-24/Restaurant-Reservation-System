package Presentacion.FramePrincipal;

import Presentacion.Coordinador.Coordinador;

import javax.swing.*;

public class FramePrincipal extends JFrame {
    private final Coordinador coordinador;

    public FramePrincipal(Coordinador coordinador){
        this.coordinador = coordinador;
        iniciarComponentes();
    }

    public void iniciarComponentes(){
        setSize(1080,720);
        setTitle("Pantalla de Eleccion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
