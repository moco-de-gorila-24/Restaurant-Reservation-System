/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restaurant.reservation.system;

import Presentacion.Coordinador.Coordinador;

/**
 * Clase main que inicia la aplicación.
 * 
 * @author Luis Alonso Ortiz Aguirre, Jorge Mayorquin Urbina Urias y Carlos Andrés Duarte Mendez
 */
public class RestaurantReservationSystem {

    /**
     * Punto de entrada del sistema. Crea el coordinador e inicia la GUI.
     * @param args 
     */
    public static void main(String[] args) {
        Coordinador coordinador = new Coordinador();
        coordinador.iniciarComponentes();
    }
}
