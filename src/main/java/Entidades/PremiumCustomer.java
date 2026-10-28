/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ACER
 */
@Entity
@Table(name = "premiumCustomers")
public class PremiumCustomer extends Customer {

    @Column(name = "points", nullable = false)
    private int points = 0;

    @Column(name = "level", nullable = false)
    private int level = 1;

    public PremiumCustomer() {
        super();
    }

    public PremiumCustomer(String name, List<String> telephone, String email, int points, int level) {
        super(name, telephone, email);
        setPoints(points);
        setLevel(level);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Los puntos no pueden ser negativos.");
        }
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException("El nivel premium debe estar entre 1 y 5.");
        }
        this.level = level;
    }
}
