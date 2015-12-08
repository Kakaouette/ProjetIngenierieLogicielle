/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.Objects;
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
    int id;
    
    @Column(nullable = false)
    String nom;
    
    @Column(nullable = false)
    String prenom;
    
    @Column(unique = true, nullable = false)
    String ine;
     
    @Column(nullable = false)
    String adressePostale;
    
    @Column(nullable = false)
    String sexe;
    
    @ManyToOne
    Adresse adresse;

    public Etudiant() {
    }

    public Etudiant(String ine, String nom, String prenom, String adressePostale, String sexe, Adresse adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adressePostale = adressePostale;
        this.sexe = sexe;
        this.adresse = adresse;
        this.ine = ine;
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

    public void setIne(String ine) {
        this.ine = ine;
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

    public String getIne() {
        return ine;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.nom);
        hash = 37 * hash + Objects.hashCode(this.prenom);
        hash = 37 * hash + Objects.hashCode(this.adressePostale);
        hash = 37 * hash + Objects.hashCode(this.sexe);
        hash = 37 * hash + Objects.hashCode(this.adresse);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etudiant other = (Etudiant) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.adressePostale, other.adressePostale)) {
            return false;
        }
        if (!Objects.equals(this.sexe, other.sexe)) {
            return false;
        }
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        return true;
    }
}
