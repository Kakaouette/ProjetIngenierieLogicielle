/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.DossierDAO;
import modele.dao.FormationDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import service.DossierService;

/**
 *
 * @author Arthur
 */
public class AjoutDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Compte compteActif = (Compte) request.getSession().getAttribute("compte");
        Date dateNow = Calendar.getInstance().getTime(); //recuperation de la date actuelle
        
        String idDossier = request.getParameter("idDossier"); //add: verifier id valide
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String sexe = request.getParameter("sexe");
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String note = request.getParameter("note");
        int idFormation = Integer.parseInt(request.getParameter("idFormation"));
        
        if(idDossier.isEmpty() || nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() || rue.isEmpty()){
            try {
                throw new Exception("Un des champs est vide.");
            } catch (Exception ex) {
                Logger.getLogger(AjoutDossierAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Dossier nouveauDossier = new Dossier();
        nouveauDossier.setId(idDossier);
        nouveauDossier.setDate(dateNow);
        Adresse adresse = new Adresse(codePostal, ville);
        Etudiant etudiant = new Etudiant(nom, prenom, rue, sexe, adresse);
        nouveauDossier.setEtudiant(etudiant);
        nouveauDossier.setHistorique(new ArrayList<Historique>());
        nouveauDossier.getHistorique().add(new Historique(dateNow, "", "Création du dossier", compteActif));
        nouveauDossier.getHistorique().add(new Historique(dateNow, note, "Ajout d'une commentaire", compteActif));
        Formation formation = new FormationDAO().getById(idFormation); //add: verif formation != null
        nouveauDossier.setDemandeFormation(formation);
        
        try
        {
            new DossierService().ajouterDossier(nouveauDossier);
            Dossier dossier = new DossierDAO().getById(Integer.parseInt(idDossier));

            if (dossier == null) {
                request.setAttribute("error", "true");
                request.setAttribute("message", "Le dossier n'a pas été crée.");
            } else {
                request.setAttribute("error", "false");
                request.setAttribute("message", "Le dossier a été crée.");
            }
        }
        catch(Exception e)
        {
            request.setAttribute("error", "true");
            request.setAttribute("message", "Le dossier n'a pas été crée.");
            Dossier dossier = new DossierDAO().getById(Integer.parseInt(idDossier));
            if(dossier!=null){
                request.setAttribute("error", "true");
                request.setAttribute("message", "Le dossier existe déjà !");
            } 
        }

        return "creerDossier.jsp";
    }
    
}
