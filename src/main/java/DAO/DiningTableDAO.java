/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.JPAUtil;
import DTO.DiningTableDTO;
import Entidades.DiningTable;
import Entidades.Restaurant;
import Interfaces.IDiningTableDAO;
import Mappers.DiningTableMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author ACER
 */
public class DiningTableDAO implements IDiningTableDAO {

    @Override
    public String guardar(List<DiningTableDTO> mesas) {
        System.out.println("entró a guardar mesas, cantidad=" + (mesas == null ? "null" : mesas.size()));
        if (mesas == null || mesas.isEmpty()) {
            return "Error: debes indicar al menos una mesa.";
        }

        Long restaurantId = mesas.get(0) == null ? null : mesas.get(0).getRestaurantId();
        Set<Integer> numeros = new HashSet<>();

        for (DiningTableDTO d : mesas) {
            if (d == null || !datosValidos(d)) {
                return "Número, capacidad y restaurante son obligatorios.";
            }
            if (!d.getRestaurantId().equals(restaurantId)) {
                return "Error: todas las mesas deben pertenecer al mismo restaurante.";
            }
            if (!numeros.add(d.getNumber())) {
                return "Error: el número de mesa " + d.getNumber() + " está repetido en la lista.";
            }
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Restaurant restaurant = em.find(Restaurant.class, restaurantId);
            if (restaurant == null) {
                return "El restaurante seleccionado no existe.";
            }

            List<Integer> repetido = em.createQuery(
                            "SELECT m.number FROM DiningTable m "
                                    + "WHERE m.restaurant.idRestaurant = :restauranteId AND m.number IN :numeros",
                            Integer.class)
                    .setParameter("restauranteId", restaurantId)
                    .setParameter("numeros", numeros)
                    .getResultList();

            if (!repetido.isEmpty()) {
                return "Ya existen mesas con los números " + repetido + " en este restaurante.";
            }

            List<DiningTable> mesasE = DiningTableMapper.toListEntity(mesas, restaurant);

            tx.begin();
            for (DiningTable mesa : mesasE) {
                restaurant.agregarMesa(mesa);
                em.persist(mesa);
            }
            tx.commit();

            return "Se guardaron " + mesasE.size() + " mesas correctamente.";

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return "Error al guardar las mesas.";

        } finally {
            em.close();
        }
    }

    @Override
    public String actualizar(Long id, DiningTableDTO mesa) {
        if (id == null || id <= 0 || !datosValidos(mesa)) {
            return "Los datos de la mesa no son válidos.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            DiningTable entidad = em.find(DiningTable.class, id);

            if (entidad == null) {
                return "No se encontró la mesa.";
            }

            Restaurant restaurant = em.find(Restaurant.class, mesa.getRestaurantId());

            if (restaurant == null) {
                return "El restaurante seleccionado no existe.";
            }

            boolean numeroDuplicado = !em.createQuery(
                    "SELECT m FROM DiningTable m WHERE m.number = :numero AND m.restaurant.idRestaurant = :restauranteId AND m.idTable <> :id",
                    DiningTable.class)
                    .setParameter("numero", mesa.getNumber())
                    .setParameter("restauranteId", mesa.getRestaurantId())
                    .setParameter("id", id)
                    .setMaxResults(1).getResultList().isEmpty();

            if (numeroDuplicado) {
                return "Error: ese número de mesa ya existe en el restaurante.";
            }

            tx.begin();

            DiningTableMapper.actualizarEntidad(entidad, mesa, restaurant);

            tx.commit();

            return "Mesa actualizada correctamente.";

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return "Error al actualizar la mesa: " + e.getMessage();

        } finally {
            em.close();
        }
    }

    @Override
    public String eliminar(Long id) {
        if (id == null || id <= 0) {
            return "El ID de la mesa no es válido.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            DiningTable entidad = em.find(DiningTable.class, id);

            if (entidad == null) {
                return "No se encontró la mesa.";
            }
            
            Long cantidadReservaciones = em.createQuery(
                    "SELECT COUNT(r) FROM Reservation r WHERE r.diningTable.idTable = :idMesa", 
                    Long.class)
                    .setParameter("idMesa", id)
                    .getSingleResult();

            if (cantidadReservaciones > 0) {
                return "No se puede eliminar la mesa porque ya esta reservada";
            }
            
            tx.begin();
            em.remove(entidad);
            tx.commit();

            return "Mesa eliminada correctamente.";

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return "Error al eliminar la mesa. Comprueba si tiene reservaciones asociadas. " + e.getMessage();

        } finally {
            em.close();
        }
    }

    @Override
    public DiningTableDTO consultarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        EntityManager em = JPAUtil.crearEntityManager();

        try {
            DiningTable entidad = em.find(DiningTable.class, id);
            return DiningTableMapper.toDTO(entidad);

        } catch (Exception e) {
            System.err.println("Error al consultar la mesa: " + e.getMessage());
            return null;

        } finally {
            em.close();
        }
    }

    @Override
    public List<DiningTableDTO> consultarTodos() {
        EntityManager em = JPAUtil.crearEntityManager();

        try {
            List<DiningTable> entidades = em.createQuery("SELECT m FROM DiningTable m ORDER BY m.restaurant.name, m.number", DiningTable.class).getResultList();

            List<DiningTableDTO> resultado = new ArrayList<>();

            for (DiningTable entidad : entidades) {
                resultado.add(DiningTableMapper.toDTO(entidad));
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("Error al consultar las mesas: " + e.getMessage());
            return new ArrayList<>();

        } finally {
            em.close();
        }
    }

    @Override
    public List<DiningTableDTO> consultarPorRestaurante(Long restaurantId) {
        if (restaurantId == null || restaurantId <= 0) {
            return new ArrayList<>();
        }

        EntityManager em = JPAUtil.crearEntityManager();

        try {
            List<DiningTable> entidades = em.createQuery(
                    "SELECT m FROM DiningTable m WHERE m.restaurant.idRestaurant = :restauranteId ORDER BY m.number", DiningTable.class)
                    .setParameter("restauranteId", restaurantId)
                    .getResultList();

            List<DiningTableDTO> resultado = new ArrayList<>();

            for (DiningTable entidad : entidades) {
                resultado.add(DiningTableMapper.toDTO(entidad));
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("Error al consultar las mesas del restaurante: " + e.getMessage());
            return new ArrayList<>();

        } finally {
            em.close();
        }
    }

    private boolean datosValidos(DiningTableDTO mesa) {
        return mesa != null
                && mesa.getNumber() > 0
                && mesa.getCapacity() > 0
                && mesa.getRestaurantId() != null
                && mesa.getRestaurantId() > 0;
    }
}
