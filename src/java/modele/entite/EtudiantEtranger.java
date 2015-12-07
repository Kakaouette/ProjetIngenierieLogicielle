/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
/**
 *
 * @author Val
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EtudiantEtranger extends Etudiant implements Serializable{
    @Column
    String niveau;
    
    @Column
    String avis;

    public EtudiantEtranger() {
    }

    public EtudiantEtranger(String niveau, String avis, String ine, String nom, String prenom, String adressePostale, String sexe, Adresse adresse) {
        super(ine, nom, prenom, adressePostale, sexe, adresse);
        this.niveau = niveau;
        this.avis = avis;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.niveau);
        hash = 29 * hash + Objects.hashCode(this.avis);
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
        final EtudiantEtranger other = (EtudiantEtranger) obj;
        if (!Objects.equals(this.niveau, other.niveau)) {
            return false;
        }
        if (!Objects.equals(this.avis, other.avis)) {
            return false;
        }
        return true;
    }
    
    
}
