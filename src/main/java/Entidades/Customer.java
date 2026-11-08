/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * Entidad JPA que representa a un cliente del sistema.
 * Utiliza la estrategia de herencia JOINED para diferenciar a los clientes normales de los Premium.
 * 
 * @author luis
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ElementCollection
    @CollectionTable(name = "customer_telephones", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "telephone", nullable = true, length = 10)
    private List<String> telephone = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "RestaurantCustomer", // Nombre exacto que pediste
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> restaurantCustomer = new ArrayList<>();
    
    public Customer() {
    }

    public Customer(String name, List<String> telephone, String email) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
    }

    public Long getId() {
        return idCustomer;
    }

    public Customer setId(Long id) {
        this.idCustomer = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<String> getTelephone() {
        return telephone;
    }

    public Customer setTelephone(List<String> telephone) {
        this.telephone = telephone;
        return this;
    }

    public List<Restaurant> getRestaurantCustomer() {
        return restaurantCustomer;
    }

    public void setRestaurantCustomer(List<Restaurant> restaurantCustomer) {
        this.restaurantCustomer = restaurantCustomer;
    }
    

}
