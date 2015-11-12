<<<<<<< HEAD
<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

/**
 *
 * @author nicol
 */
public class Etudiant {
    
}
=======
=======
>>>>>>> 2054c0997544f8afe3b63e2f89f9426ec981beb2
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
/**
 *
 * @author nicol
 */

@Entity
public class Etudiant implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
    private int id;
    
    @Column
    private String nom;
    
     @Column
    private String prenom;
     
    @Column
    private String adressePostale;
    
    @Column
    private String sexe;
    
    @ManyToOne
    private Adresse adresse;
=======
    int id;
    
    @Column(nullable = false)
    String nom;
    
    @Column(nullable = false)
    String prenom;
     
    @Column(nullable = false)
    String adressePostale;
    
    @Column(nullable = false)
    String sexe;
    
    @ManyToOne
    Adresse adresse;
>>>>>>> 2054c0997544f8afe3b63e2f89f9426ec981beb2

    public Etudiant() {
    }

    
    
    
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public String getSexe() {
        return sexe;
    }

    public Adresse getAdresse() {
        return adresse;
    }
    
    
}
<<<<<<< HEAD
>>>>>>> origin/master
=======
>>>>>>> 2054c0997544f8afe3b63e2f89f9426ec981beb2
