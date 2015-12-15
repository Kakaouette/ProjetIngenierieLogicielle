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
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Column(nullable = false)
    String texte;
    
    @Column(nullable = false)
    String icon;
    
    @JoinColumn(nullable = true)
    Menu menuSuperieur;
    
    @OneToOne
    Action action;

    public Menu() {
    }

    public Menu(String texte, Menu menuSuperieur, Action action, String icone) {
        this.texte = texte;
        this.menuSuperieur = menuSuperieur;
        this.action = action;
        this.icon = icone;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Menu getMenuSuperieur() {
        return menuSuperieur;
    }

    public void setMenuSuperieur(Menu menuSuperieur) {
        this.menuSuperieur = menuSuperieur;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final Menu other = (Menu) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
