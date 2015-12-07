/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

/**
 * <b>Classe entité : Compte</b>
 * <p>
 * Classe servant à conserver les attributs d'un compte : <br/>
 * Cette classe contient un lgin, un mot de passe, le nom et prénom de l'utilisateur.
 * Le type est un enum permettant de gérer le type de compte. La formation indique a quel formation
 * est rattaché le compte (s'il est secretaire, ect, peut être null)
 * </p>
 *
 * @see Formation
 * @see TypeCompte
 * @author Nicolas Roulon
 */
@Entity
public class Compte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Column(unique = true, nullable = false)
    private String login;
    
    @Column (nullable = false)
    private String mdp;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String prenom;
    
    @Column
    private String mail;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeCompte type;
    
    @OneToMany(targetEntity = Formation.class, cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Formation> formationAssocie;

    public Compte() {
    }

    public Compte(String login, String mdp, String nom, String prenom, String mail, TypeCompte type, List<Formation> formationAssocie) {
        this.login = login;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.type = type;
        this.formationAssocie = formationAssocie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public TypeCompte getType() {
        return type;
    }

    public void setType(TypeCompte type) {
        this.type = type;
    }

    public List<Formation> getFormationAssocie() {
        return formationAssocie;
    }

    public void setFormationAssocie(List<Formation> formationAssocie) {
        this.formationAssocie = formationAssocie;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.login);
        hash = 29 * hash + Objects.hashCode(this.mdp);
        hash = 29 * hash + Objects.hashCode(this.nom);
        hash = 29 * hash + Objects.hashCode(this.prenom);
        hash = 29 * hash + Objects.hashCode(this.mail);
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.formationAssocie);
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
        final Compte other = (Compte) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.mdp, other.mdp)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.formationAssocie, other.formationAssocie)) {
            return false;
        }
        return true;
    }
    
}
