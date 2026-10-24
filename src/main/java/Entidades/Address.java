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
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idDireccion;

    public Long getId() {
        return idDireccion;
    }

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "state", nullable = true, length = 100)
    private String state;

    @Column(name = "postCode", nullable = false, length = 5) //5 por que es el estandar en mexico
    private int postCode;

    @Column(name = "city", nullable = true, length = 100)
    private String city;

    @Column(name = "number", nullable = true, length = 6) //solo lo puse por legibilidad y por que es simetrico
    private int number;

    public Address() {
    }

    public Address(String street, String state, int postCode, String city, int number) {
        this.street = street;
        this.state = state;
        this.postCode = postCode;
        this.city = city;
        this.number = number;
    }

    public void setId(Long id) {
        this.idDireccion = id;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
