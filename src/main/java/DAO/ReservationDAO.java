/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.JPAUtil;
import DTO.ReservationDTO;
import Entidades.Customer;
import Entidades.DiningTable;
import Entidades.PremiumCustomer;
import Entidades.Reservation;
import Interfaces.IReservationDAO;
import Mappers.ReservationMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

            int personas = reservacion.getNumberPeople();
            int capacidad = table.getCapacity();

            LocalDate hoy = LocalDate.now();
            LocalTime ahora = LocalTime.now();

            if (reservacion.getDate().isBefore(hoy)) {
                return "Error: no se puede agendar una reservación para una fecha pasada.";
            }
            if (reservacion.getDate().isEqual(hoy) && reservacion.getHour().isBefore(ahora)) {
                return "Error: la hora seleccionada para hoy ya ha pasado. Por favor, elige una hora futura.";
            }
            
            if (personas > capacidad) {
                return "El número de personas supera la capacidad de la mesa.";
            }

            int espaciosSobrantes = capacidad - personas;
            int limiteSobrante = 2; 

            //si el cliente es nivel premium 4 o 5, permite hasta 5 de diferencia
            if (customer instanceof PremiumCustomer) {
                PremiumCustomer premium = (PremiumCustomer) customer;
                if (premium.getLevel() >= 4) {
                    limiteSobrante = 5;
                }
            }

            if (espaciosSobrantes > limiteSobrante) {
                return "La mesa no se puede reservar debido a la poca cantidad de personas. " +
                       "Límite de diferencia permitido: " + limiteSobrante;
            }

            if (mesaOcupada(em, reservacion, null)) {
                return "Error: la mesa ya tiene una reservación en esa fecha y hora.";
            }

            if (customer instanceof PremiumCustomer) {
                PremiumCustomer premium = (PremiumCustomer) customer;
                Random random = new Random();

                premium.setPoints(premium.getPoints() + 5);

                if (premium.getLevel() < 5 && random.nextInt(100) < 25) {
                    premium.setLevel(premium.getLevel() + 1);
                }

                //perdida de puntos
                if (espaciosSobrantes > 0) {
                    premium.setPoints(Math.max(0, premium.getPoints() - 5));

                    if (premium.getLevel() > 1 && random.nextInt(100) < 25) {
                        premium.setLevel(premium.getLevel() - 1);
                    }
                }
            }

            Reservation entidad = ReservationMapper.toEntity(reservacion, customer, table);

            tx.begin();
            
            entidad.setEndTime(reservacion.getHour().plusHours(4));
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
            return "Los datos de la reservación no son validos.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Reservation entidad = em.find(Reservation.class, id);
            if (entidad == null) {
                return "No se encontro la reservación.";
            }

            Customer customer = em.find(Customer.class, reservacion.getCustomerId());
            if (customer == null) {
                return "El cliente seleccionado no existe.";
            }

            DiningTable table = em.find(DiningTable.class, reservacion.getTableId());
            if (table == null) {
                return "La mesa seleccionada no existe.";
            }

            int personas = reservacion.getNumberPeople();
            int capacidad = table.getCapacity();

            LocalDate hoy = LocalDate.now();
            LocalTime ahora = LocalTime.now();

            if (reservacion.getDate().isBefore(hoy)) {
                return "Error: no se puede agendar una reservación para una fecha pasada.";
            }
            if (reservacion.getDate().isEqual(hoy) && reservacion.getHour().isBefore(ahora)) {
                return "Error: la hora seleccionada para hoy ya ha pasado. Por favor, elige una hora futura.";
            }
            
            if (personas > capacidad) {
                return "El numero de personas supera la capacidad de la mesa.";
            }

            int espaciosSobrantes = capacidad - personas;
            int limiteSobrante = 2; 

            if (customer instanceof PremiumCustomer) {
                PremiumCustomer premium = (PremiumCustomer) customer;
                if (premium.getLevel() >= 4) {
                    limiteSobrante = 5;
                }
            }

            if (espaciosSobrantes > limiteSobrante) {
                return "La mesa no se puede reservar debido a la poca cantidad de personas. " +
                       "Limite de diferencia permitido por el nivel de cliente: " + limiteSobrante;
            }

            if (mesaOcupada(em, reservacion, id)) {
                return "La mesa ya esta reservada para esta fecha y hora.";
            }

            if (customer instanceof PremiumCustomer) {
                PremiumCustomer premium = (PremiumCustomer) customer;
                Random random = new Random();

                premium.setPoints(premium.getPoints() + 5);

                if (premium.getLevel() < 5 && random.nextInt(100) < 25) {
                    premium.setLevel(premium.getLevel() + 1);
                }
                
                if (espaciosSobrantes > 0) {
                    premium.setPoints(Math.max(0, premium.getPoints() - 5));

                    if (premium.getLevel() > 1 && random.nextInt(100) < 25) {
                        premium.setLevel(premium.getLevel() - 1);
                    }
                }
            }

            tx.begin();
            ReservationMapper.actualizarEntidad(entidad, reservacion, customer, table);
            entidad.setEndTime(reservacion.getHour().plusHours(2));
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
            LocalTime nuevaHoraInicio = reservacion.getHour();
            LocalTime nuevaHoraFin = reservacion.getHour().plusHours(4);

            // La consulta JPQL con la lógica de intersección de rangos
            String jpql = "SELECT COUNT(r) FROM Reservation r " + "WHERE r.diningTable.idTable = :mesaId " + "AND r.date = :fecha " +
                          "AND :nuevaHoraInicio < r.endTime " + "AND :nuevaHoraFin > r.hour";

            if (idExcluir != null) {
                jpql += " AND r.idReservation <> :idExcluir";
            }

            javax.persistence.TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                    .setParameter("mesaId", reservacion.getTableId())
                    .setParameter("fecha", reservacion.getDate())
                    .setParameter("nuevaHoraInicio", nuevaHoraInicio)
                    .setParameter("nuevaHoraFin", nuevaHoraFin);

            if (idExcluir != null) {
                query.setParameter("idExcluir", idExcluir);
            }

            return query.getSingleResult() > 0;
        }
  
}
