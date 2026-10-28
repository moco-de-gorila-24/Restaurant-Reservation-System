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

    public void setId(Long id) {
        this.idRestaurant = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
    this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setRestaurant(this);
        }
    }

    public List<DiningTable> getTables() {
        return tables;
    }

    public void setTables(List<DiningTable> tables) {
        this.tables = tables == null ? new ArrayList<>() : tables;

        for (DiningTable table : this.tables) {
            table.setRestaurant(this);
        }
    }

    public boolean agregarMesa(DiningTable table) {
        if (table == null) {
            return false;
        }

        table.setRestaurant(this);
        return tables.add(table);
    }

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
