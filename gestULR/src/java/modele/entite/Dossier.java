/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

/**
 *
 * @author Val
 */
@Entity
public class Dossier implements Serializable{
    
    @Id
    int id;
    
    @Temporal(TemporalType.TIMESTAMP)
    Date date;
    
    @Column
    String etat;
        
    @Lob
    @Column
    String lettre;

    @Column
    boolean admissible;
    
    @ManyToOne
    Etudiant etudiant;
    
    @ManyToOne
    Formation demandeFormation;
    
    @OneToMany(targetEntity = Historique.class, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    List<Historique> historique;

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public void setAdmissible(boolean admissible) {
        this.admissible = admissible;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public void setDemandeFormation(Formation demandeFormation) {
        this.demandeFormation = demandeFormation;
    }

    public void setHistorique(List<Historique> historique) {
        this.historique = historique;
    }

    public Dossier() {
    }

    
    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getEtat() {
        return etat;
    }

    public String getLettre() {
        return lettre;
    }

    public boolean isAdmissible() {
        return admissible;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public Formation getDemandeFormation() {
        return demandeFormation;
    }

    public List<Historique> getHistorique() {
        return historique;
    }
    
    
    
    
    
}
