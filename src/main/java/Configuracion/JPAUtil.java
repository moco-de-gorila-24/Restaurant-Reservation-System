/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ACER
 */
public class JPAUtil {

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("ConnectionPU");

    /**
     * Crea un EntityManager para realizar operaciones con JPA.
     *
     * @return EntityManager disponible para las operaciones.
     */
    public static EntityManager crearEntityManager() {
        return FACTORY.createEntityManager();
    }

    /**
     * Indica si la fábrica de EntityManager continúa abierta.
     *
     * @return true si la fábrica está abierta, false en caso contrario.
     */
    public static boolean estaAbierta() {
        return FACTORY.isOpen();
    }

    /**
     * Cierra la fábrica de conexiones de JPA.
     *
     * @return true si se cerró o ya estaba cerrada.
     */
    public static boolean cerrar() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
        return !FACTORY.isOpen();
    }
}
