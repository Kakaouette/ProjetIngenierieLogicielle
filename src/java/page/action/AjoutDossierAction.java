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
import modele.dao.AdresseDAO;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
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
        //recuperation du formulaire
        String idDossier = request.getParameter("idDossier");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String sexe = request.getParameter("sexe");
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String note = request.getParameter("note");
        int idFormation = Integer.parseInt(request.getParameter("idFormation"));
        boolean typeAdmission = (boolean) request.getAttribute("admission");
        
        //verification de la validité du formulaire
        if(idDossier.isEmpty() || nom.isEmpty() || prenom.isEmpty() || sexe.isEmpty() || rue.isEmpty() || codePostal.isEmpty() || ville.isEmpty()){
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
        Formation formation = new FormationDAO().getById(idFormation);
        if(formation == null){
            request.setAttribute("error", "true");
            request.setAttribute("message", "Formation inconnue");
            request.setAttribute("focus", "formation");
            return "creerDossier.jsp";
        }
        
        //verification de la validité de la demande
        if(new DossierDAO().getById(idDossier) != null){ //verif id valide //add: verif id suit regex
            request.setAttribute("error", "true");
            request.setAttribute("message", "L'identifiant du dossier est déjà utilisé");
            request.setAttribute("focus", "id");
            return "creerDossier.jsp";
        }
        if(new DossierDAO().getByEtudiantAndFormation(etudiant, formation) != null){ //verif dossier existant
            request.setAttribute("error", "true");
            request.setAttribute("message", "Un dossier pour cet formation existe déjà pour cet étudiant"); //Le dossier existe déjà !
            request.setAttribute("focus", "formation");
            return "creerDossier.jsp";
        }
        
        //données complémentaires necessaires pour la formation du dossier
        Compte compteActif = (Compte) request.getSession().getAttribute("compte");
        Date dateNow = Calendar.getInstance().getTime(); //recuperation de la date actuelle
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
        
        //ajout des entitées inexistante
        if(new AdresseDAO().getById(adresse.getId()) == null){
            new AdresseDAO().save(adresse); //ajout dans la base de donnée
        }
        if(new EtudiantDAO().getById(etudiant.getId()) == null){
            new EtudiantDAO().save(etudiant); //ajout dans la base de donnée
        }
        //demande de creation du dossier
        try{
            if (new DossierService().ajouterDossier(nouveauDossier)) {
                request.setAttribute("error", "false");
                request.setAttribute("message", "Dossier créé.");
            } else {
                request.setAttribute("error", "true");
                request.setAttribute("message", "Le dossier n'a pas été créé.");
            }
        }
        catch(Exception e){
            request.setAttribute("error", "true");
            request.setAttribute("message", e.getMessage());
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
