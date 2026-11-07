/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.JPAUtil;
import DTO.RestaurantDTO;
import Entidades.Address;
import Entidades.Restaurant;
import Interfaces.IRestaurantDAO;
import Mappers.RestaurantMapper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Implementación de IRestaurantDAO para gestionar la persistencia de restaurantes.
 * 
 * @author ACER
 */
public class RestaurantDAO implements IRestaurantDAO {

    /**
     * Guarda un restaurante en la base de datos.
     * @param dto Datos del restaurante.
     * @return El ID generado o null si falla.
     */
    @Override
    public Long guardar(RestaurantDTO dto) {
        if (dto == null || !datosValidos(dto)) {
            return null;
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.crearEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Address address = em.find(Address.class, dto.getAddressId());
            if (address == null) {
                tx.rollback();
                return null;
            }

            Restaurant restaurant = RestaurantMapper.toEntity(dto);
            restaurant.setAddress(address);

            em.persist(restaurant);
            tx.commit();

            return restaurant.getId();

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            ex.printStackTrace();   // temporal
            return null;

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    /**
     * Actualiza los datos de un restaurante.
     * @param id ID del restaurante.
     * @param dto Nuevos datos.
     * @return Mensaje de éxito o error.
     */
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
    
    /**
     * Elimina un restaurante de la BD.
     * @param id ID del restaurante.
     * @return Mensaje de éxito o error.
     */
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
    
    /**
     * Consulta un restaurante por ID.
     * @param id ID del restaurante.
     * @return RestaurantDTO.
     */
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

    /**
     * Consulta todos los restaurantes.
     * @return Lista de RestaurantDTO.
     */
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
     * Valida que los campos del restaurante sean correctos antes de guardar.
     * @param dto DTO a validar.
     * @return true si los datos son válidos.
     */
    private boolean datosValidos(RestaurantDTO dto) {
        String name = dto.getName();
        String kitchen = dto.getKitchenType();
        String phone = dto.getTelephone();

        if (name == null || name.trim().isEmpty() || name.length() > 30) {
            return false;
        }
        if (kitchen == null || kitchen.trim().isEmpty() || kitchen.length() > 100) {
            return false;
        }
        if (phone != null && !phone.trim().isEmpty() && !phone.matches("\\d{1,10}")) {
            return false;
        }
        return dto.getAddressId() != null && dto.getAddressId() > 0;
    }
}
