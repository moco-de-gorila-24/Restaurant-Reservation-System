/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.JPAUtil;
import DTO.RestaurantDTO;
import Entidades.Restaurant;
import Interfaces.IRestaurantDAO;
import Mappers.RestaurantMapper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author ACER
 */
public class RestaurantDAO implements IRestaurantDAO {

    @Override
    public String guardar(RestaurantDTO dto) {
        if (dto == null) {
            return "Error: los datos del restaurante son obligatorios.";
        }

        if (!datosValidos(dto)) {
            return "Error: revisa los campos obligatorios del restaurante y de su dirección.";
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.crearEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Restaurant restaurant = RestaurantMapper.toEntity(dto);
            em.persist(restaurant);

            tx.commit();

            return "Restaurante guardado correctamente. ID: " + restaurant.getId();

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return "Error al guardar el restaurante: " + ex.getMessage();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public String actualizar(Long id, RestaurantDTO dto) {
        if (id == null || dto == null) {
            return "Error: se requiere el ID y los datos del restaurante.";
        }

        if (!datosValidos(dto)) {
            return "Error: revisa los campos obligatorios del restaurante y de su dirección.";
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.crearEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Restaurant restaurant = em.find(Restaurant.class, id);

            if (restaurant == null) {
                tx.rollback();
                return "No se encontró el restaurante indicado.";
            }

            RestaurantMapper.actualizarEntidad(restaurant, dto);

            tx.commit();

            return "Restaurante actualizado correctamente.";

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return "Error al actualizar el restaurante: " + ex.getMessage();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public String eliminar(Long id) {
        if (id == null) {
            return "Error: el ID del restaurante es obligatorio.";
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.crearEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Restaurant restaurant = em.find(Restaurant.class, id);

            if (restaurant == null) {
                tx.rollback();
                return "No se encontró el restaurante.";
            }

            em.remove(restaurant);
            tx.commit();

            return "Restaurante eliminado correctamente.";

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return "No se pudo eliminar el restaurante. Comprueba si tiene reservaciones o mesas relacionadas. Detalle: " + ex.getMessage();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public RestaurantDTO consultarPorId(Long id) {
        if (id == null) {
            return null;
        }

        EntityManager em = null;

        try {
            em = JPAUtil.crearEntityManager();
            Restaurant restaurant = em.find(Restaurant.class, id);
            return RestaurantMapper.toDTO(restaurant);

        } catch (Exception ex) {
            return null;

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<RestaurantDTO> consultarTodos() {
        EntityManager em = null;
        List<RestaurantDTO> resultado = new ArrayList<>();

        try {
            em = JPAUtil.crearEntityManager();
            List<Restaurant> restaurantes = em.createQuery("SELECT r FROM Restaurant r ORDER BY r.name", Restaurant.class).getResultList();

            for (Restaurant restaurant : restaurantes) {
                resultado.add(RestaurantMapper.toDTO(restaurant));
            }

            return resultado;

        } catch (Exception ex) {
            return resultado;

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Valida los datos antes de persistir.
     */
    private boolean datosValidos(RestaurantDTO dto) {
        return dto.getName() != null
            && !dto.getName().trim().isEmpty()
            && dto.getKitchenType() != null
            && !dto.getKitchenType().trim().isEmpty()
            && dto.getStreet() != null
            && !dto.getStreet().trim().isEmpty()
            && dto.getPostCode() > 0
            && dto.getNumber() > 0;
    }
}
