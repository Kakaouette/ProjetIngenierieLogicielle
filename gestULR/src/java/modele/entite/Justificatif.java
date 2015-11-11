/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
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
    
    @Enumerated(EnumType.STRING)
    TypeJustificatif typeAdmissible;
    
    @Enumerated(EnumType.STRING)
    TypeJustificatifEtranger typeNationalite;

    public Justificatif() {
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

    public TypeJustificatif getTypeAdmissible() {
        return typeAdmissible;
    }

    public void setTypeAdmissible(TypeJustificatif typeAdmissible) {
        this.typeAdmissible = typeAdmissible;
    }

    public TypeJustificatifEtranger getTypeNationalite() {
        return typeNationalite;
    }

    public void setTypeNationalite(TypeJustificatifEtranger typeNationalite) {
        this.typeNationalite = typeNationalite;
    }
}
