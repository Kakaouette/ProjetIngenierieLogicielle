/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.AdresseDAO;
import modele.dao.EtudiantDAO;
import modele.dao.FormationDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import service.AjoutDossierInvalideException;
import service.DossierService;

/**
 *
 * @author Arthur
 */
public class AjoutDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {        
        //recuperation du formulaire
        String idDossier = request.getParameter("idDossier");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String sexe = request.getParameter("sexe");
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String note = request.getParameter("note");
        String formationIntitule = request.getParameter("formationIntitule");
        boolean typeAdmission = (boolean) request.getAttribute("admission");
        
        //verification de la validité du formulaire
        if(idDossier.isEmpty() || nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() || rue.isEmpty() || codePostal.isEmpty() || ville.isEmpty() || formationIntitule.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                Logger.getLogger(AjoutDossierAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //mise en forme des données
        Adresse adresse = new AdresseDAO().getAdresseByCodePostal(codePostal);
        if(adresse == null){
            adresse = new Adresse(codePostal, ville);
        }else if(!adresse.getVille().equals(ville)){
            adresse = new Adresse(codePostal, ville);
        }
        Etudiant etudiant = new EtudiantDAO().getEtudiantByNomPrenom(nom, prenom); //add: gestion etudiant etranger
        if(etudiant == null){
            etudiant = new Etudiant(nom, prenom, rue, sexe, adresse);
        }
        Formation formation = new FormationDAO().getFormationByIntitule(formationIntitule);
        if(formation == null){
            request.setAttribute("error", "true");
            request.setAttribute("message", "Formation inconnue");
            request.setAttribute("focus", "formation");
            return "creerDossier.jsp";
        }
        
        //données complémentaires necessaires pour la formation du dossier
        Compte compteActif = (Compte) request.getSession().getAttribute("compte");
        Date dateNow = new Date(); //recuperation de la date actuelle
        //formation du dossier
        Dossier nouveauDossier = new Dossier();
        nouveauDossier.setId(idDossier);
        nouveauDossier.setDate(dateNow);
        nouveauDossier.setEtudiant(etudiant);
        nouveauDossier.setDemandeFormation(formation);
        nouveauDossier.setAdmissible(typeAdmission);
        nouveauDossier.setHistorique(new ArrayList<Historique>());
        nouveauDossier.getHistorique().add(new Historique(dateNow, "", "Création du dossier", compteActif));
        nouveauDossier.getHistorique().add(new Historique(dateNow, note, "Commentaire à la création du dossier", compteActif));
        
        //demande de creation du dossier
        try{
            new DossierService().ajouterDossier(nouveauDossier);
            request.setAttribute("error", "false");
            request.setAttribute("message", "Dossier créé.");
        }catch(AjoutDossierInvalideException e){
            request.setAttribute("error", "true");
            request.setAttribute("message", "Le dossier n'a pas été créé: " + e.getMessage());
            if(e.getCause().getMessage().equals(AjoutDossierInvalideException.cause.ID_Invalide.toString())){
                request.setAttribute("focus", "id");
            }else if(e.getCause().getMessage().equals(AjoutDossierInvalideException.cause.Dossier_Existant.toString())){
                request.setAttribute("focus", "formation");
            }
        }catch(Exception e){ //exception bdd
            request.setAttribute("error", "true");
            request.setAttribute("message", "Le dossier n'a pas été créé.");
        }
        
        String pageAVoir = "";
        if(request.getParameter("bouton").equals("enregistrer")){
            pageAVoir = request.getParameter("pageRetour");
        }else if(request.getParameter("bouton").equals("enregistrer&nouveau")){
            pageAVoir = "creerDossier.jsp";
        }
        return pageAVoir;
    }
    
}
