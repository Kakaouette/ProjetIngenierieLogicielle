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
public class Dossier implements Serializable {

    @Id
    String id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    @Enumerated(EnumType.STRING)
    TypeEtatDossier etat;

    @Lob
    @Column
    String lettre;

    @Enumerated(EnumType.STRING)
    TypeDossier type;

    @JoinColumn(nullable = false)
    @ManyToOne
    Etudiant etudiant;

    @ManyToOne
    Formation demandeFormation;

    @OneToMany(targetEntity = Historique.class, cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<Historique> historique;

    public Dossier(String id, Date date, TypeEtatDossier etat, String lettre, TypeDossier admissible, Etudiant etudiant, Formation demandeFormation) {
        this.id = id;
        this.date = date;
        this.etat = etat;
        this.lettre = lettre;
        this.type = admissible;
        this.etudiant = etudiant;
        this.demandeFormation = demandeFormation;
    }

    public Dossier(String id, Date date, TypeEtatDossier etat, String lettre, TypeDossier admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique) {
        this.id = id;
        this.date = date;
        this.etat = etat;
        this.lettre = lettre;
        this.type = admissible;
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

    public void setEtat(TypeEtatDossier etat) {
        this.etat = etat;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public void setAdmissible(TypeDossier admissible) {
        this.type = admissible;
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

    public TypeEtatDossier getEtat() {
        return etat;
    }

    public String getLettre() {
        return lettre;
    }

    public TypeDossier getAdmissible() {
        return type;
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
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.date);
        hash = 37 * hash + Objects.hashCode(this.etat);
        hash = 37 * hash + Objects.hashCode(this.lettre);
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.etudiant);
        hash = 37 * hash + Objects.hashCode(this.demandeFormation);
        hash = 37 * hash + Objects.hashCode(this.historique);
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
        if (this.type != other.type) {
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
