/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) para la entidad Customer.
 * Incluye campos para manejar la herencia de PremiumCustomer.
 * 
 * @author ACER
 */
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    private List<String> telephone = new ArrayList<>();
    private boolean premium;
    private int points;
    private int level;

    public CustomerDTO() {
    }

    public CustomerDTO(String name, String email, List<String> telephone, boolean premium, int points, int level) {
        this.name = name;
        this.email = email;
        setTelephone(telephone);
        this.premium = premium;
        this.points = points;
        this.level = level;
    }

    public CustomerDTO(Long id, String name, String email, List<String> telephone, boolean premium, int points, int level) {
        this.id = id;
        this.name = name;
        this.email = email;
        setTelephone(telephone);
        this.premium = premium;
        this.points = points;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public CustomerDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<String> getTelephone() {
        return telephone;
    }

    public CustomerDTO setTelephone(List<String> telephone) {
        this.telephone = telephone == null ? new ArrayList<>() : new ArrayList<>(telephone);
        return this;
    }

    public boolean isPremium() {
        return premium;
    }

    public CustomerDTO setPremium(boolean premium) {
        this.premium = premium;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public CustomerDTO setPoints(int points) {
        this.points = points;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public CustomerDTO setLevel(int level) {
        this.level = level;
        return this;
    }
}
