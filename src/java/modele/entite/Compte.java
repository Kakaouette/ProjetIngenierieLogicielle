/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.List;
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
    
    @OneToMany(targetEntity = Formation.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Formation> formationAssocie;

    public Compte() {
    }

    public Compte(TypeCompte type, String login, String nom, String prenom, String mail, String mdp) {
        this.type = type;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
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
}
