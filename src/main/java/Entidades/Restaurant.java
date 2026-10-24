/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author luis
 */
@Entity
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRestaurant;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "telephone", nullable = false, length = 10)
    private String telephone;

    @Column(name = "kitchenType", nullable = false, length = 100)
    private String kitchenType;

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
}
