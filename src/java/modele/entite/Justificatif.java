/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nicol
 */
@Entity
public class Justificatif implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Column(nullable = false)
    String titre;
    
    @Column(nullable = false)
    String description;
    
    @Enumerated(EnumType.STRING)
    TypeDossier typeAdmissible;
    
    @Enumerated(EnumType.STRING)
    TypeJustificatifEtranger typeNationalite;

    public Justificatif() {
    }

    public Justificatif(String titre, String description, TypeDossier typeAdmissible, TypeJustificatifEtranger typeNationalite) {
        this.titre = titre;
        this.description = description;
        this.typeAdmissible = typeAdmissible;
        this.typeNationalite = typeNationalite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public TypeDossier getTypeAdmissible() {
        return typeAdmissible;
    }

    public void setTypeAdmissible(TypeDossier typeAdmissible) {
        this.typeAdmissible = typeAdmissible;
    }

    public TypeJustificatifEtranger getTypeNationalite() {
        return typeNationalite;
    }

    public void setTypeNationalite(TypeJustificatifEtranger typeNationalite) {
        this.typeNationalite = typeNationalite;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.titre);
        hash = 11 * hash + Objects.hashCode(this.typeAdmissible);
        hash = 11 * hash + Objects.hashCode(this.typeNationalite);
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
        final Justificatif other = (Justificatif) obj;
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (this.typeAdmissible != other.typeAdmissible) {
            return false;
        }
        if (this.typeNationalite != other.typeNationalite) {
            return false;
        }
        return true;
    }
    
    
}
