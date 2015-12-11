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
    
    @Column(nullable = false)
    String classAction;
    
    @OneToOne
    Page page;

    public Action() {
        
    }

    public Action(String id, String classAction, Page page) {
        this.id = id;
        this.classAction = classAction;
        this.page = page;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassAction() {
        return classAction;
    }

    public void setClassAction(String classAction) {
        this.classAction = classAction;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
