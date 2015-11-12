/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;


import java.io.Serializable;
<<<<<<< HEAD
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
=======
>>>>>>> 2054c0997544f8afe3b63e2f89f9426ec981beb2
import javax.persistence.*;
/**
 *
 * @author Val
 */
@Entity
<<<<<<< HEAD
public class EtudiantEtranger extends Etudiant implements Serializable{
    
    @Column
    private String niveau;
    
    @Column
    private String avis;
=======
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EtudiantEtranger extends Etudiant implements Serializable{
    
    @Column(nullable = false)
    String niveau;
    
    @Column
    String avis;
>>>>>>> 2054c0997544f8afe3b63e2f89f9426ec981beb2

    public EtudiantEtranger() {
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getAvis() {
        return avis;
    }
    
    
}
