/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author Val
 */
@Entity
public class Dossier implements Serializable{
    
    @Id
    String id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date date;
    
    @Column(nullable = false)
    String etat;
        
    @Lob
    @Column
    String lettre;

    @Column
    boolean admissible;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    Etudiant etudiant;
    
    @ManyToOne
    Formation demandeFormation;
    
    @OneToMany(targetEntity = Historique.class, cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    List<Historique> historique;

    public Dossier(String id, Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation) {
        this.id = id;
        this.date = date;
        this.etat = etat;
        this.lettre = lettre;
        this.admissible = admissible;
        this.etudiant = etudiant;
        this.demandeFormation = demandeFormation;
    }
    
    public Dossier(Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation) {
        this.date = date;
        this.etat = etat;
        this.lettre = lettre;
        this.admissible = admissible;
        this.etudiant = etudiant;
        this.demandeFormation = demandeFormation;
    }

    public Dossier(Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique) {
        this.date = date;
        this.etat = etat;
        this.lettre = lettre;
        this.admissible = admissible;
        this.etudiant = etudiant;
        this.demandeFormation = demandeFormation;
        this.historique = historique;
    }

    public void setId(String id) {
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

    
    public String getId() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.date);
        hash = 67 * hash + Objects.hashCode(this.etat);
        hash = 67 * hash + Objects.hashCode(this.lettre);
        hash = 67 * hash + (this.admissible ? 1 : 0);
        hash = 67 * hash + Objects.hashCode(this.etudiant);
        hash = 67 * hash + Objects.hashCode(this.demandeFormation);
        hash = 67 * hash + Objects.hashCode(this.historique);
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
        final Dossier other = (Dossier) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.etat, other.etat)) {
            return false;
        }
        if (!Objects.equals(this.lettre, other.lettre)) {
            return false;
        }
        if (this.admissible != other.admissible) {
            return false;
        }
        if (!Objects.equals(this.etudiant, other.etudiant)) {
            return false;
        }
        if (!Objects.equals(this.demandeFormation, other.demandeFormation)) {
            return false;
        }
        if (!Objects.equals(this.historique, other.historique)) {
            return false;
        }
        return true;
    }
    
    
}
