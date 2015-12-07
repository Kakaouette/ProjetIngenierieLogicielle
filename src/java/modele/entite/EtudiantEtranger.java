/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;


import java.io.Serializable;
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

    public EtudiantEtranger(String niveau, String avis, String nom, String prenom, String adressePostale, String sexe, Adresse adresse, String ine) {
        super(nom, prenom, adressePostale, sexe, adresse, ine);
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
}
