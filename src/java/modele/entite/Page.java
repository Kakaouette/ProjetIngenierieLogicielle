/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import javax.persistence.*;

/**
 *
 * @author nicol
 */
public class Page {
    @Id
    String id;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TypeCompte typeAuthoriser;
    
    @Column(nullable = false)
    String titre;

    public Page(String id, TypeCompte typeAuthoriser) {
        this.id = id;
        this.typeAuthoriser = typeAuthoriser;
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
}
