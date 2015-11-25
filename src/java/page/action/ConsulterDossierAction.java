package page.action;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.Dao;
import modele.dao.DossierDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeCompte;

/**
 * Gestion des dossiers - Consultation en detail un dossier
 * Si dossier non trouvé, retour vers la page des dossiers
 *
 * @author totodunet
 */
public class ConsulterDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        //recuperation de l'id du formulaire
        //String idDossier = request.getParameter("idDossier");
        //String idDossier = "1";
        
        //recuperation du dossier
        //Dossier dossier = new DossierDAO().getById(idDossier);
        /*
        =============================================================================
        
        
        
                            Classe utilisée pour les tests
                                        À supprimer
        
        
        =============================================================================
        */
        
        Formation formation = new Formation();
        formation.setDebut(new Date("02/04/1994"));
        formation.setDescription("cocu");
        formation.setFin(new Date("25/04/1994"));
        formation.setId(1);
        formation.setIntitule("trololol");
        formation.setLesJustificatifs(null);
        formation.setNombrePlace(40);
        
        List<Formation> l = new ArrayList();
        l.add(formation);
        
        Compte compte = new Compte();
        compte.setId(1);
        compte.setLogin("vphan");
        compte.setMail("phan@me.com");
        compte.setMdp("lsdjfmlsdjfklmq");
        compte.setNom("Phan");
        compte.setPrenom("Joseph");
        compte.setType(TypeCompte.admin);
        compte.setFormationAssocie(l);
        
        Adresse adresse = new Adresse();
        adresse.setId(1);
        adresse.setVille("La Rochelle");
        adresse.setCodePostal("17000");
        
        Etudiant etudiant = new Etudiant();
        etudiant.setNom("Phan");
        etudiant.setPrenom("Joseph");
        etudiant.setId(1);
        etudiant.setSexe("M");
        etudiant.setAdresse(adresse);
        etudiant.setAdressePostale("17000");
        
        Historique historique = new Historique();
        historique.setAction("Ajout feuille");
        historique.setDate(new Date("23/09/2014"));
        historique.setId(1);
        historique.setMessage("Cocu !");
        historique.setCompte(compte);
        
        List<Historique> h = new ArrayList();
        h.add(historique);
        
        Dossier dossier = new Dossier();
        dossier.setId("1");
        dossier.setLettre("Coucou");
        dossier.setEtudiant(etudiant);
        dossier.setEtat("En cours");
        dossier.setDemandeFormation(formation);        
        dossier.setHistorique(h);
        dossier.setDate(new Date("23/08/1990"));
        
        //si dossier n'existe pas => retour vers la liste des dossiers 
        if(dossier != null){

            request.setAttribute("dossier", dossier);
            return "consulteDossier.jsp";
        }else{
            request.setAttribute("titre", "Gestion des dossiers");
            List<Dossier> dossiers = new DossierDAO().SelectAll(); //recuperation des comptes pour la page suivante
            request.setAttribute("dossiers", dossiers);
            return "listeDossiers.jsp";
        }
    }
    
}
