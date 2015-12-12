/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author nicol
 */
@Entity
public class Page {
    @Id
    String id;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TypeCompte typeAuthoriser;
    
    @Column(nullable = false)
    String titre;

    public Page() {
    }

    public Page(String id, TypeCompte typeAuthoriser, String titre) {
        this.id = id;
        this.typeAuthoriser = typeAuthoriser;
        this.titre = titre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TypeCompte getTypeAuthoriser() {
        return typeAuthoriser;
    }

    public void setTypeAuthoriser(TypeCompte typeAuthoriser) {
        this.typeAuthoriser = typeAuthoriser;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
