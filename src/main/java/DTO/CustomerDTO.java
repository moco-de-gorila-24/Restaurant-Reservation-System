/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;
import java.util.List;

/**
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getTelephone() {
        return telephone;
    }

    public void setTelephone(List<String> telephone) {
        this.telephone = telephone == null ? new ArrayList<>() : new ArrayList<>(telephone);
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
