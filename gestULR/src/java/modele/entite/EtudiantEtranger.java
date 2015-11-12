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
 * @author Val
 */
@Entity
public class EtudiantEtranger extends Etudiant implements Serializable{
    
    @Column
    private String niveau;
    
    @Column
    private String avis;

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
