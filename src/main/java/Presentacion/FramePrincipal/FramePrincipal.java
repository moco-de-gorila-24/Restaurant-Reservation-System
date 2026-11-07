/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.FramePrincipal;

import Presentacion.Coordinador.Coordinador;
import javax.swing.*;

/**
 * Ventana principal de la aplicación.
 * Configura el tamaño, título y propiedades básicas de la ventana.
 * 
 * @author luis
 */
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
        setResizable(false);
    }
}
