/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Action correspond à un appel d'une action dans le gestionnaire de servlet :<br/>
 * Lorsque Navigation est appellé, elle contient un aparamètre action qui redirige vers un traitement ou une page en particulier.
 * Cette classe représente l'action demandé via la variable id. La liste des TypeCompte permet de définir les compte qui ont accès à ce traitement particulier.
 * 
 * @author nicol
 */
@Entity
public class Action implements Serializable{
    @Id
    String id;
    
    @ElementCollection(targetClass = TypeCompte.class)
    @Enumerated(EnumType.STRING)
    List<TypeCompte> lesTypeCompte;

    public Action() {
    }

    public Action(String id, List<TypeCompte> lesTypeCompte) {
        this.id = id;
        this.lesTypeCompte = lesTypeCompte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TypeCompte> getLesTypeCompte() {
        return lesTypeCompte;
    }

    public void setLesTypeCompte(List<TypeCompte> lesTypeCompte) {
        this.lesTypeCompte = lesTypeCompte;
    }
}
