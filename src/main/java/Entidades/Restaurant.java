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
 * Entidad JPA que representa un restaurante en el sistema.
 * Contiene relaciones con su dirección y sus mesas asociadas.
 * 
 * @author luis
 */
@Entity
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestaurant;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "telephone", nullable = true, length = 10)
    private String telephone;

    @Column(name = "kitchenType", nullable = false, length = 100)
    private String kitchenType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiningTable> tables = new ArrayList<>();
    
    public Restaurant() {
    }

    public Restaurant(String name, String telephone, String kitchenType) {
        this.name = name;
        this.telephone = telephone;
        this.kitchenType = kitchenType;
    }

    public Long getId() {
        return idRestaurant;
    }

    public Restaurant setId(Long id) {
        this.idRestaurant = id;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public Restaurant setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public Restaurant setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Restaurant setAddress(Address address) {
        this.address = address;
        return this;
    }

    public List<DiningTable> getTables() {
        return tables;
    }

    public Restaurant setTables(List<DiningTable> tables) {
        this.tables = tables == null ? new ArrayList<>() : tables;
        for (DiningTable table : this.tables) {
            table.setRestaurant(this);
        }
        return this;
    }

    /**
     * Agrega una mesa al restaurante y establece la relación bidireccional.
     * 
     * @param table Mesa a agregar.
     * @return true si se agregó correctamente.
     */
    public boolean agregarMesa(DiningTable table) {
        if (table == null) {
            return false;
        }

        table.setRestaurant(this);
        return tables.add(table);
    }

    /**
     * Quita una mesa del restaurante y rompe la relación bidireccional.
     * 
     * @param table Mesa a quitar.
     * @return true si se eliminó correctamente.
     */
    public boolean quitarMesa(DiningTable table) {
        if (table == null) {
            return false;
        }

        boolean eliminada = tables.remove(table);
        if (eliminada) {
            table.setRestaurant(null);
        }
        return eliminada;
    }
}
