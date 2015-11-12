/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 2054c0997544f8afe3b63e2f89f9426ec981beb2
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

/**
 *
 * @author Val
 */
@Entity
public class Adresse implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
    private int id;
    
    @Column
    private String codePostal;
    
    @Column
    private String ville;
=======
    int id;
    
    @Column(nullable = false)
    String codePostal;
    
    @Column(nullable = false)
    String ville;
>>>>>>> 2054c0997544f8afe3b63e2f89f9426ec981beb2

    public Adresse() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getId() {
        return id;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }
    
    
    
}
