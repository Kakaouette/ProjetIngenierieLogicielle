/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        Action actionPageSuivante = null;
        
        //recuperation du formulaire
        String idDossier = request.getParameter("idDossier");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String sexe = request.getParameter("sexe");
        String adresse = request.getParameter("adresse");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String notes = request.getParameter("notes");
        String formationIntitule = request.getParameter("formationIntitule");
        String type = request.getParameter("type");
        
        //verification de la validité du formulaire
        String[] required = {idDossier, nom, prenom, sexe, adresse, codePostal, ville, formationIntitule, type};
        String[] requiredToString = {"id du dossier", "nom", "prénom", "sexe", "adresse", "code postal", "ville", "intitulé de la formation", "type"};
        try {
            validerFormulaire(required, requiredToString);
        } catch (Exception ex) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", ex.getMessage());
            return stayHere(request, response);
        }
        //mise en forme des données
        boolean typeAdmission = type.equals("admission");
        Adresse adressePostale = new AdresseDAO().getAdresseByCodePostal(codePostal);
        if(adressePostale == null){
            adressePostale = new Adresse(codePostal, ville);
        }else if(!adressePostale.getVille().equals(ville)){
            adressePostale = new Adresse(codePostal, ville);
        }
        Etudiant etudiant = new EtudiantDAO().getEtudiantByNomPrenom(nom, prenom); //add: gestion etudiant etranger
        if(etudiant == null){
            etudiant = new Etudiant(nom, prenom, adresse, sexe, adressePostale);
        }
        Formation formation = new FormationDAO().getFormationByIntitule(formationIntitule);
        if(formation == null){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Formation inconnue");
            request.setAttribute("focus", "formationIntitule");
            return stayHere(request, response); //redirection
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
        nouveauDossier.getHistorique().add(new Historique(dateNow, notes, "Commentaire à la création du dossier", compteActif));
        
        //demande de creation du dossier
        try{
            new DossierService().ajouterDossier(nouveauDossier);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Dossier créé.");
            //redirection
            if(request.getParameter("bouton").equals("enregistrer")){
                actionPageSuivante = new VoirGestionFormationsAction(); //request.getParameter("pageRetour");
            }else if(request.getParameter("bouton").equals("enregistrer&nouveau")){
                actionPageSuivante = new VoirAjoutDossierAction();
            }
        }catch(AjoutDossierInvalideException e){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Le dossier n'a pas été créé: " + e.getMessage());
                    
            if(e.getCause().getMessage().equals(AjoutDossierInvalideException.cause.ID_Invalide.toString())){
                request.setAttribute("focus", "idDossier");
            }else if(e.getCause().getMessage().equals(AjoutDossierInvalideException.cause.Dossier_Existant.toString())){
                request.setAttribute("focus", "formationIntitule");
            }
            return stayHere(request, response); //redirection
        }catch(Exception e){ //exception bdd
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Le dossier n'a pas été créé.");
            return stayHere(request, response); //redirection
        }
        
        return actionPageSuivante.execute(request, response);
    }
    
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("idDossier", request.getParameter("idDossier"));
        request.setAttribute("nom", request.getParameter("nom"));
        request.setAttribute("prenom", request.getParameter("prenom"));
        request.setAttribute("sexe", request.getParameter("sexe"));
        request.setAttribute("adresse", request.getParameter("adresse"));
        request.setAttribute("codePostal", request.getParameter("codePostal"));
        request.setAttribute("ville", request.getParameter("ville"));
        request.setAttribute("notes", request.getParameter("notes"));
        request.setAttribute("formationIntitule", request.getParameter("formationIntitule"));
        request.setAttribute("type", request.getParameter("type"));
        return new VoirAjoutDossierAction().execute(request, response); //modif: voir récupérer page precedente
    }
    
    private void validerFormulaire(String[] required, String[] requiredToString) throws Exception{
        
        //verification de la validité du formulaire
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i].isEmpty()){
                empty.add(requiredToString[i]);
            }
        }
        if(!empty.isEmpty()){
            String champs = "";
            for(String champ : empty){
                if(!champs.equals("")){champs+=", ";}
                champs += champ;
            }
            if(empty.size()==1){
                throw new Exception("Un champ requis est vide. (" + champs + ")");
            }else{
                throw new Exception("Des champs requis sont vides. (" + champs + ")");
            }
        }
    }
}
