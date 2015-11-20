/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;


@Entity
public class Adresse implements Serializable{
    //bonjour ceci est un test
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Column(nullable = false)
    String codePostal;
    
    @Column(nullable = false)
    String ville;

    public Adresse() {
    }

    public Adresse(String codePostal, String ville) {
        this.codePostal = codePostal;
        this.ville = ville;
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
