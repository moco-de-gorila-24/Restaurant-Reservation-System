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
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ElementCollection
    @CollectionTable(name = "customer_telephones", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "telephone", nullable = true, length = 10)
    private List<String> telephone = new ArrayList<>();

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

    public void setId(Long id) {
        this.idCustomer = id;
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
        this.telephone = telephone;
    }

}
