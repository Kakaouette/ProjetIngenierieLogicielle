/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Arthur
 */
@Entity
public class Page implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(unique = true, nullable = false)
    private String actionName;
    
    @Column
    private String titre;
    
    @Enumerated(EnumType.STRING)
    @OneToMany(targetEntity = Compte.class, cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<TypeCompte> comptesAutorises;
    
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setComptesAutorises(List<TypeCompte> comptesAutorises) {
        this.comptesAutorises = comptesAutorises;
    }

    public String getActionName() {
        return actionName;
    }

    public String getTitre() {
        return titre;
    }

    public List<TypeCompte> getComptesAutorises() {
        return comptesAutorises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Page{" + "id=" + id + ", jsp=" + actionName + ", titre=" + titre + ", comptesAutorises=" + comptesAutorises + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Page other = (Page) obj;
        if (!Objects.equals(this.actionName, other.actionName)) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (!Objects.equals(this.comptesAutorises, other.comptesAutorises)) {
            return false;
        }
        return true;
    }
}
