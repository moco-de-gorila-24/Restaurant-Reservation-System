/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import DAO.ReservationDAO;
import DTO.ReservationDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para validar la lógica de negocio de las Reservaciones.
 * Prueba las reglas de capacidad, fechas pasadas y la lógica especial para clientes Premium.
 * 
 * @author Andres
 */
public class reservationTest {

    /**
     * Método auxiliar para crear un DTO de reservación base para las pruebas.
     * NOTA IMPORTANTE!! EN LA BD TIENE QUE HABER UN CLIENTE Y UNA MESA
     */
    private ReservationDTO crearReserva(int personas, LocalDate fecha, LocalTime hora) {
        ReservationDTO dto = new ReservationDTO();
        dto.setCustomerId(1L); 
        dto.setTableId(1L);    
        dto.setDate(fecha);
        dto.setHour(hora);
        dto.setNumberPeople(personas);
        return dto;
    }

    /**
     * Prueba 1: Valida que no se pueda reservar en una fecha del pasado.
     */
    @Test
    public void testReservacionFechaPasada() {
        ReservationDAO dao = new ReservationDAO();
      
        //ayer
        ReservationDTO dto = crearReserva(5, LocalDate.now().minusDays(1), LocalTime.of(20, 0));
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.toLowerCase().contains("fecha pasada"), "Debería rechazar reservaciones en el pasado.");
    }

    /**
     * Prueba 2: Valida que no se pueda reservar hoy a una hora que ya pasó.
     */
    @Test
    public void testReservacionHoraPasada() {
        ReservationDAO dao = new ReservationDAO();
        
        //hace1 hora
        ReservationDTO dto = crearReserva(5, LocalDate.now(), LocalTime.now().minusHours(1));
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.toLowerCase().contains("hora seleccionada para hoy ya ha pasado"), "Debería rechazar horas pasadas del día de hoy.");
    }

    /**
     * Prueba 3: Valida que el número de personas no supere la capacidad física de la mesa.
     */
    @Test
    public void testReservacionExcedeCapacidad() {
        ReservationDAO dao = new ReservationDAO();
        
        ReservationDTO dto = crearReserva(67, LocalDate.now().plusDays(1), LocalTime.of(20, 0));
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.toLowerCase().contains("supera la capacidad"), "Debería rechazar si hay más personas que la capacidad.");
    }

    /**
     * Prueba 4: Valida la regla de espacios sobrantes para un cliente normal.
     * Si la mesa es de 10 y reserva para 5 (Sobran 5 espacios), un cliente normal
     * solo permite 2 espacios de diferencia, por lo que debería rechazarse.
     */
    @Test
    public void testReservacionPocaCantidadPersonas() {
        ReservationDAO dao = new ReservationDAO();
        
        ReservationDTO dto = crearReserva(5, LocalDate.now().plusDays(1), LocalTime.of(20, 0));
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.toLowerCase().contains("poca cantidad de personas"), "Debería rechazar por dejar demasiados espacios vacíos.");
    }

    /**
     * Prueba 5: Verifica que se pueda crear una reservación exitosa
     * si los datos cumplen con todos los requisitos.
     */
    @Test
    public void testReservacionExitosa() {
        ReservationDAO dao = new ReservationDAO();
        ReservationDTO dto = crearReserva(10, LocalDate.now().plusDays(2), LocalTime.of(18, 0));
        
        String resultado = dao.guardar(dto);
        
        assertTrue(resultado.toLowerCase().contains("guardada correctamente"), "La reservación válida debería guardarse exitosamente.");
    }

    
}
