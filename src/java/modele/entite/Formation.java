/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nicol
 */

@Entity
public class Formation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Column 
    String description;
    
    @Column
    int nombrePlace;
    
    @Temporal(TemporalType.DATE)
    Date debut;
    
    @Temporal(TemporalType.DATE)
    Date fin;
    
    @Column(nullable = false)
    String intitule;
    
    @OneToMany(targetEntity = Justificatif.class, cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<Justificatif> lesJustificatifs;

    public Formation() {
    }

    public Formation(String description, int nombrePlace, Date debut, Date fin, String intitule, List<Justificatif> lesJustificatifs) {
        this.description = description;
        this.nombrePlace = nombrePlace;
        this.debut = debut;
        this.fin = fin;
        this.intitule = intitule;
        this.lesJustificatifs = lesJustificatifs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNombrePlace() {
        return nombrePlace;
    }

    public void setNombrePlace(int nombrePlace) {
        this.nombrePlace = nombrePlace;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public List<Justificatif> getLesJustificatifs() {
        return lesJustificatifs;
    }

    public void setLesJustificatifs(List<Justificatif> lesJustificatifs) {
        this.lesJustificatifs = lesJustificatifs;
    }
}
