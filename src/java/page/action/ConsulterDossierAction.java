package page.action;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.Dao;
import modele.dao.DossierDAO;
import modele.entite.Dossier;

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
        String idDossier = request.getParameter("idDossier");
        
        //recuperation du dossier
        Dossier dossier = new DossierDAO().getById(idDossier);
        
        //si dossier n'existe pas => retour vers la liste des dossiers 
        if(dossier != null){
            /*request.setAttribute("titre","Gestion du dossier "+dossier.getId());
            request.setAttribute("id",dossier.getId());
            request.setAttribute("etat",dossier.getEtat());
            request.setAttribute("admissibilite",dossier.isAdmissible());
            request.setAttribute("etudiant_nom",dossier.getEtudiant().getNom());
            request.setAttribute("etudiant_prenom",dossier.getEtudiant().getPrenom());
            request.setAttribute("etudiant_adresse",dossier.getEtudiant().getAdressePostale());
            request.setAttribute("etudiant_codepostal",dossier.getEtudiant().getAdresse().getCodePostal());
            request.setAttribute("etudiant_ville",dossier.getEtudiant().getAdresse().getVille());
            request.setAttribute("date",dossier.getDate());
            request.setAttribute("formation_id",dossier.getDemandeFormation().getId());
            request.setAttribute("formation_intitule",dossier.getDemandeFormation().getIntitule());
            request.setAttribute("historique",dossier.getHistorique());
            request.setAttribute("lettre",dossier.getLettre());*/
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
