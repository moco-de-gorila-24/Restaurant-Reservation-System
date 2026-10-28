/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Configuracion.JPAUtil;
import DTO.CustomerDTO;
import Entidades.Customer;
import Entidades.PremiumCustomer;
import Interfaces.ICustomerDAO;
import Mappers.CustomerMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author ACER
 */
public class CustomerDAO implements ICustomerDAO {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("\\d{10}");
    
    @Override
    public String guardar(CustomerDTO cliente) {
        String error = validarCliente(cliente);

        if (error != null) {
            return error;
        }

        Customer entity = CustomerMapper.toEntity(cliente);
        if (entity == null) {
            return "Error: no fue posible convertir los datos del cliente.";
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.crearEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.persist(entity);
            tx.commit();

            return "Cliente guardado correctamente. ID: " + entity.getId();

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return "Error al guardar el cliente: " + ex.getMessage();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public String actualizar(Long id, CustomerDTO cliente) {
        if (id == null || cliente == null) {
            return "Error: se requiere el ID y los datos del cliente.";
        }
        
        String error = validarCliente(cliente);

        if (error != null) {
            return error;
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.crearEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Customer existente = em.find(Customer.class, id);

            if (existente == null) {
                tx.rollback();
                return "No existe un cliente con el ID indicado.";
            }

            // no permitimos cambiar de tipo de cliente.
            boolean esPremium = existente instanceof PremiumCustomer;

            if (esPremium != cliente.isPremium()) {
                tx.rollback();
                return "No se puede cambiar el tipo de cliente durante la actualización. Elimina y registra el tipo correcto.";
            }

            existente.setName(cliente.getName());
            existente.setEmail(cliente.getEmail());
            existente.setTelephone(cliente.getTelephone());

            if (existente instanceof PremiumCustomer) {
                PremiumCustomer premium = (PremiumCustomer) existente;
                premium.setPoints(cliente.getPoints());
                premium.setLevel(cliente.getLevel());
            }

            tx.commit();
            return "Cliente actualizado correctamente.";

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return "Error al actualizar el cliente: " + ex.getMessage();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public String eliminar(Long id) {
        if (id == null) {
            return "Error: el ID del cliente es obligatorio.";
        }

        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = JPAUtil.crearEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Customer cliente = em.find(Customer.class, id);

            if (cliente == null) {
                tx.rollback();
                return "No se encontró el cliente.";
            }

            Long cantidad = em.createQuery("SELECT COUNT(r) FROM Reservation r WHERE r.customer.idCustomer = :id", Long.class).setParameter("id", id).getSingleResult();

            if (cantidad > 0) {
                tx.rollback();
                return "Error: no se puede eliminar el cliente porque tiene reservaciones asociadas.";
            }
            
            em.remove(cliente);
            tx.commit();

            return "Cliente eliminado correctamente.";

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            return "No se pudo eliminar el cliente, puede tener reservaciones asociadas. " + ex.getMessage();

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public CustomerDTO consultarPorId(Long id) {
        if (id == null) {
            return null;
        }

        EntityManager em = null;

        try {
            em = JPAUtil.crearEntityManager();
            Customer cliente = em.find(Customer.class, id);

            return CustomerMapper.toDTO(cliente);

        } catch (Exception ex) {
            return null;

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<CustomerDTO> consultarTodos() {
        EntityManager em = null;
        List<CustomerDTO> resultado = new ArrayList<>();

        try {
            em = JPAUtil.crearEntityManager();

            List<Customer> clientes = em.createQuery( "SELECT c FROM Customer c ORDER BY c.name", Customer.class).getResultList();

            for (Customer cliente : clientes) {
                resultado.add(CustomerMapper.toDTO(cliente));
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
    
    private String validarCliente(CustomerDTO cliente) {
        if (cliente == null) {
            return "Error: los datos del cliente son obligatorios.";
        }

        if (cliente.getName() == null || cliente.getName().trim().isEmpty() || cliente.getName().trim().length() > 50) {
            return "Error: el nombre es obligatorio y debe tener máximo 50 caracteres.";
        }

        if (cliente.getEmail() == null || !EMAIL_PATTERN.matcher(cliente.getEmail().trim()).matches()) {
            return "Error: el correo electrónico no tiene un formato válido.";
        }

        if (cliente.getTelephone() != null) {
            for (String telefono : cliente.getTelephone()) {
                if (telefono != null && !telefono.isEmpty() && !TELEFONO_PATTERN.matcher(telefono).matches()) {
                    return "Error: cada teléfono debe contener exactamente 10 dígitos.";
                }
            }
        }

        if (cliente.isPremium()) {
            if (cliente.getPoints() < 0) {
                return "Error: los puntos no pueden ser negativos.";
            }

            if (cliente.getLevel() < 1 || cliente.getLevel() > 5) {
                return "Error: el nivel Premium debe estar entre 1 y 5.";
            }
        }

        return null;
    }
}
