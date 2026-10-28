/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.JPAUtil;
import DTO.ReservationDTO;
import Entidades.Customer;
import Entidades.DiningTable;
import Entidades.Reservation;
import Interfaces.IReservationDAO;
import Mappers.ReservationMapper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author ACER
 */
public class ReservationDAO implements IReservationDAO {

    @Override
    public String guardar(ReservationDTO reservacion) {
        if (!datosValidos(reservacion)) {
            return "Error: verifica la fecha, hora, cliente, mesa y número de personas.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Customer customer = em.find(Customer.class, reservacion.getCustomerId());

            if (customer == null) {
                return "El cliente seleccionado no existe.";
            }

            DiningTable table = em.find(DiningTable.class, reservacion.getTableId());

            if (table == null) {
                return "La mesa seleccionada no existe.";
            }

            if (reservacion.getNumberPeople() > table.getCapacity()) {
                return "El número de personas supera la capacidad de la mesa.";
            }

            if (mesaOcupada(em, reservacion, null)) {
                return "Error: la mesa ya tiene una reservación en esa fecha y hora.";
            }

            Reservation entidad = ReservationMapper.toEntity(reservacion, customer, table);

            tx.begin();
            em.persist(entidad);
            tx.commit();

            return "Reservación guardada correctamente ID: " + entidad.getId();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            return "Error al guardar la reservación: " + e.getMessage();

        } finally {
            em.close();
        }
    }

    @Override
    public String actualizar(Long id, ReservationDTO reservacion) {
        if (id == null || id <= 0 || !datosValidos(reservacion)) {
            return "Los datos de la reservación no son válidos.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Reservation entidad = em.find(Reservation.class, id);

            if (entidad == null) {
                return "No se encontró la reservación.";
            }

            Customer customer = em.find(Customer.class, reservacion.getCustomerId());

            if (customer == null) {
                return "El cliente seleccionado no existe.";
            }

            DiningTable table = em.find(DiningTable.class, reservacion.getTableId());

            if (table == null) {
                return "La mesa seleccionada no existe.";
            }

            if (reservacion.getNumberPeople() > table.getCapacity()) {
                return "El número de personas supera la capacidad de la mesa.";
            }

            if (mesaOcupada(em, reservacion, id)) {
                return "Error: la mesa ya tiene una reservación en esa fecha y hora.";
            }

            tx.begin();

            ReservationMapper.actualizarEntidad(entidad, reservacion, customer, table);

            tx.commit();

            return "Reservación actualizada correctamente.";

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            return "Error al actualizar la reservación: " + e.getMessage();

        } finally {
            em.close();
        }
    }

    @Override
    public String eliminar(Long id) {
        if (id == null || id <= 0) {
            return "Error: el ID de la reservación no es válido.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Reservation entidad = em.find(Reservation.class, id);

            if (entidad == null) {
                return "No se encontró la reservación.";
            }

            tx.begin();
            em.remove(entidad);
            tx.commit();

            return "Reservación eliminada correctamente.";

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            return "Error al eliminar la reservación: " + e.getMessage();

        } finally {
            em.close();
        }
    }

    @Override
    public ReservationDTO consultarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        EntityManager em = JPAUtil.crearEntityManager();

        try {
            Reservation entidad = em.find(Reservation.class, id);
            return ReservationMapper.toDTO(entidad);
        } catch (Exception e) {
            System.err.println("Error al consultar la reservación: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<ReservationDTO> consultarTodos() {
        EntityManager em = JPAUtil.crearEntityManager();

        try {
            List<Reservation> entidades = em.createQuery("SELECT r FROM Reservation r ORDER BY r.date, r.hour", Reservation.class).getResultList();
            List<ReservationDTO> resultado = new ArrayList<>();

            for (Reservation entidad : entidades) {
                resultado.add(ReservationMapper.toDTO(entidad));
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("Error al consultar reservaciones: " + e.getMessage());
            return new ArrayList<>();

        } finally {
            em.close();
        }
    }

    @Override
    public List<ReservationDTO> consultarPorCliente(Long customerId) {
        if (customerId == null || customerId <= 0) {
            return new ArrayList<>();
        }

        EntityManager em = JPAUtil.crearEntityManager();

        try {
            List<Reservation> entidades = em.createQuery("SELECT r FROM Reservation r WHERE r.customer.idCustomer = :clienteId ORDER BY r.date, r.hour",
                    Reservation.class)
                    .setParameter("clienteId", customerId)
                    .getResultList();

            List<ReservationDTO> resultado = new ArrayList<>();

            for (Reservation entidad : entidades) {
                resultado.add(ReservationMapper.toDTO(entidad));
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("Error al consultar las reservaciones del cliente: " + e.getMessage());
            return new ArrayList<>();

        } finally {
            em.close();
        }
    }

    private boolean datosValidos(ReservationDTO reservacion) {
        return reservacion != null
                && reservacion.getDate() != null
                && reservacion.getHour() != null
                && reservacion.getNumberPeople() > 0
                && reservacion.getCustomerId() != null
                && reservacion.getCustomerId() > 0
                && reservacion.getTableId() != null
                && reservacion.getTableId() > 0;
    }

    private boolean mesaOcupada(EntityManager em, ReservationDTO reservacion, Long idExcluir) {

        String jpql = "SELECT COUNT(r) FROM Reservation r WHERE r.diningTable.idTable = :mesaId AND r.date = :fecha AND r.hour = :hora";

        if (idExcluir != null) {
            jpql += " AND r.idReservation <> :idExcluir";
        }

        javax.persistence.TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                .setParameter("mesaId", reservacion.getTableId())
                .setParameter("fecha", reservacion.getDate())
                .setParameter("hora", reservacion.getHour());

        if (idExcluir != null) {
            query.setParameter("idExcluir", idExcluir);
        }

        return query.getSingleResult() > 0;
    }
}
