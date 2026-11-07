/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.JPAUtil;
import DTO.AddressDTO;
import Entidades.Address;
import Entidades.Restaurant;
import Interfaces.IAddressDAO;
import Mappers.AddressMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Implementación de IAddressDAO para gestionar la persistencia de direcciones en la BD.
 * 
 * @author ACER
 */
public class AddressDAO implements IAddressDAO {
    
    /**
     * Guarda una dirección en la base de datos.
     * @param addressDTO Datos de la dirección.
     * @return El ID autogenerado de la dirección.
     */
    @Override
    public Long agregar(AddressDTO addressDTO) {
        AddressMapper am = new AddressMapper();
        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin();
            Address address = AddressMapper.toEntity(addressDTO);
            em.persist(address);
            tx.commit();
            return address.getId();

        }
        catch (Exception e){
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        }
        finally {
            em.close();
        }
    }
    
    /**
     * Actualiza una dirección existente validando que pertenezca al restaurante.
     * @param id ID de la dirección.
     * @param dto Nuevos datos.
     * @return Mensaje de éxito o error.
     */
    @Override
    public String actualizar(Long id, AddressDTO dto) {
        if (id == null || id <= 0 || dto == null) {
            return "Error: los datos de la dirección no son válidos.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Address address = em.find(Address.class, id);

            if (address == null) {
                return "No se encontró la dirección.";
            }

            Restaurant restaurant = em.createQuery(
                            "SELECT r FROM Restaurant r WHERE r.address = :address", Restaurant.class)
                    .setParameter("address", address)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (restaurant == null) {
                return "Error: la dirección no está asociada a un restaurante.";
            }

            if (dto.getRestaurantId() != null && !dto.getRestaurantId().equals(restaurant.getId())) {
                return "No se puede cambiar el restaurante de una dirección desde esta operación.";
            }

            tx.begin();

            address.setStreet(dto.getStreet());
            address.setState(dto.getState());
            address.setPostCode(dto.getPostCode());
            address.setCity(dto.getCity());
            address.setNumber(dto.getNumber());

            tx.commit();

            return "Dirección actualizada correctamente.";

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return "Error al actualizar la dirección: " + e.getMessage();

        } finally {
            em.close();
        }
    }
    
    /**
     * Elimina una dirección de la base de datos.
     * @param id ID de la dirección.
     * @return Mensaje de éxito o error.
     */
    @Override
    public String eliminar(Long id) {
        if (id == null || id <= 0) {
            return "El ID de la dirección no es válido.";
        }

        EntityManager em = JPAUtil.crearEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Address address = em.find(Address.class, id);

            if (address == null) {
                return "No se encontró la dirección.";
            }

            Restaurant restaurant = em.createQuery(
                            "SELECT r FROM Restaurant r WHERE r.address = :address", Restaurant.class)
                    .setParameter("address", address)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (restaurant == null) {
                return "Error: la dirección no tiene un restaurante asociado.";
            }

            tx.begin();

            //se elimina la dirección al desvincularla.
            restaurant.setAddress(null);

            tx.commit();

            return "Dirección eliminada correctamente.";

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return "Error al eliminar la dirección: " + e.getMessage();

        } finally {
            em.close();
        }
    }
    
    /**
     * Consulta una dirección por su ID.
     * @param id ID de la dirección.
     * @return AddressDTO o null.
     */
    @Override
    public AddressDTO consultarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        EntityManager em = JPAUtil.crearEntityManager();

        try {
            Address address = em.find(Address.class, id);
            return AddressMapper.toDTO(address);

        } catch (Exception e) {
            System.err.println("Error al consultar dirección: " + e.getMessage());
            return null;

        } finally {
            em.close();
        }
    }
    
    /**
     * Consulta todas las direcciones.
     * @return Lista de AddressDTO.
     */
    @Override
    public List<AddressDTO> consultarTodos() {
        EntityManager em = JPAUtil.crearEntityManager();

        try {
            List<Address> direcciones = em.createQuery("SELECT a FROM Address a ORDER BY a.idDireccion", Address.class).getResultList();
            List<AddressDTO> resultado = new ArrayList<>();

            for (Address address : direcciones) {
                resultado.add(AddressMapper.toDTO(address));
            }

            return resultado;

        } catch (Exception e) {
            System.err.println("Error al consultar direcciones: " + e.getMessage());
            return new ArrayList<>();

        } finally {
            em.close();
        }
    }
}
