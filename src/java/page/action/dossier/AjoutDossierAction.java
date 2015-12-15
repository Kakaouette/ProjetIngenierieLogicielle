/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.dossier;

import java.io.IOException;
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
import modele.entite.EtudiantEtranger;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeDossier;
import modele.entite.TypeEtatDossier;
import modele.entite.TypeJustificatifEtranger;
import page.action.Action;
import page.action.GenerationLettres.GenerationLettresAction;
import service.exception.AjoutDossierInvalideException;
import service.DossierService;
import service.exception.AjoutAdresseInvalideException;
import service.exception.AjoutEtudiantInvalideException;
import service.exception.AjoutHistoriqueInvalideException;

/**
 *
 * @author Arthur
 */
public class AjoutDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if(request.getParameter("bouton").equals("demanderPiecesManquantes"))
        {
            return new GenerationLettresAction().execute(request, response);
        }
        Action actionPageSuivante = null;
        
        //recuperation du formulaire
        String formationIntitule = request.getParameter("formationIntitule");
        String type = request.getParameter("type");
        String nationalite = request.getParameter("nationalite");
        String idDossier = request.getParameter("idDossier");
        String ine = request.getParameter("ine");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String sexe = request.getParameter("sexe");
        String pays = request.getParameter("pays");
        String adressePostale = request.getParameter("adresse");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");
        String notes = request.getParameter("notes");
        
        //verification de la validité du formulaire
        String[] required = {formationIntitule, type, nationalite, idDossier, ine, nom, prenom, sexe, pays, adressePostale, codePostal, ville};
        String[] requiredName = {"intitulé de la formation", "type", "nationalité", "id du dossier", "INE", "nom", "prénom", "sexe", "pays", "adresse", "code postal", "ville"};
        try {
            validerFormulaire(required, requiredName);
        } catch (Exception ex) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", ex.getMessage());
            return stayHere(request, response);
        }
        //mise en forme des données
        TypeDossier typeDossier = null;
        if(type.equals(TypeDossier.inscription.toString())){
            typeDossier = TypeDossier.inscription;
        }else if(type.equals(TypeDossier.admissibilite.toString())){
            typeDossier = TypeDossier.admissibilite;
        }
        Adresse adresse = new AdresseDAO().getAdresseByCodePostalAndVille(codePostal, ville);
        if(adresse == null){
            adresse = new Adresse(codePostal, ville);
        }
        Etudiant etudiant = new EtudiantDAO().getEtudiantByINE(ine);
        if(etudiant == null){
            if(nationalite.equals(TypeJustificatifEtranger.francais.toString())){
                etudiant = new Etudiant(ine, nom, prenom, pays, adressePostale, sexe, adresse);
            }else if(nationalite.equals(TypeJustificatifEtranger.etranger.toString())){
                String avis = request.getParameter("avis");
                String niveau = request.getParameter("niveau");
                try {
                    String[] requiredEtranger = {niveau};
                    String[] requiredEtrangerName = {"niveau"};
                    validerFormulaire(requiredEtranger, requiredEtrangerName);
                } catch (Exception ex) {
                    request.setAttribute("typeMessage", "danger");
                    request.setAttribute("message", ex.getMessage());
                    return stayHere(request, response);
                }
                etudiant = new EtudiantEtranger(avis, niveau, nom, prenom, pays, adressePostale, sexe, adresse, ine);
            }
        }else{
            if((nationalite.equals(TypeJustificatifEtranger.francais.toString()) && etudiant instanceof EtudiantEtranger) || (nationalite.equals(TypeJustificatifEtranger.etranger.toString()) && etudiant instanceof Etudiant)){
                    request.setAttribute("typeMessage", "danger");
                    request.setAttribute("message", "La nationalité de l'étudiant n'est pas valide");
                    return stayHere(request, response);
            }
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
        nouveauDossier.setAdmissible(typeDossier);
        nouveauDossier.setHistorique(new ArrayList<Historique>());
        nouveauDossier.getHistorique().add(new Historique(dateNow, "", "Création du dossier", compteActif));
        nouveauDossier.getHistorique().add(new Historique(dateNow, notes, "Commentaire à la création du dossier", compteActif));
        nouveauDossier.setEtat(TypeEtatDossier.transfert_vers_secretariat);
        
        //demande de creation du dossier
        try{
            new DossierService().ajouterDossier(nouveauDossier);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Dossier créé.");
            //redirection
            if(request.getParameter("bouton").equals("enregistrer")){
                actionPageSuivante = new ConsulterDossierAction(); //request.getParameter("pageRetour");
            }else if(request.getParameter("bouton").equals("enregistrer&nouveau")){
                actionPageSuivante = new VoirAjoutDossierAction();
            }
        }catch(AjoutDossierInvalideException | AjoutAdresseInvalideException | AjoutEtudiantInvalideException | AjoutHistoriqueInvalideException | IOException e){
            //set msg d'erreur
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Le dossier n'a pas été créé: " + e.getMessage());
            //modif requete celon le type d'erreur
            if(e instanceof AjoutDossierInvalideException){
                if(e.getCause().getMessage().equals(AjoutDossierInvalideException.cause.ID_Invalide.toString())){
                    request.setAttribute("focus", "idDossier");
                }else if(e.getCause().getMessage().equals(AjoutDossierInvalideException.cause.Dossier_Existant.toString())){
                    request.setAttribute("focus", "formationIntitule");
                }
            }else if(e instanceof AjoutAdresseInvalideException){
                request.setAttribute("focus", "codePostal");
            }else if(e instanceof AjoutEtudiantInvalideException){
                request.setAttribute("focus", "nom");
            }else if(e instanceof IOException){
                request.setAttribute("message", "Le dossier n'a pas été créé.");
            }
            //reload la page
            return stayHere(request, response); //redirection
        }
        
        //free formulaire
        request.setAttribute("type", null);
        request.setAttribute("formationIntitule", null);
        request.setAttribute("nationalite", null);
        request.setAttribute("idDossier", null);
        request.setAttribute("ine", null);
        request.setAttribute("nom", null);
        request.setAttribute("prenom", null);
        request.setAttribute("sexe", null);
        request.setAttribute("pays", null);
        request.setAttribute("adresse", null);
        request.setAttribute("codePostal", null);
        request.setAttribute("ville", null);
        request.setAttribute("notes", null);
        if(request.getParameter("nationalite") != null){
            if(request.getParameter("nationalite").equals(TypeJustificatifEtranger.etranger.toString())){
                request.setAttribute("avis", null);
                request.setAttribute("niveau", null);
            }
        }
        return actionPageSuivante.execute(request, response);
    }
    
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("formationIntitule", request.getParameter("formationIntitule"));
        request.setAttribute("nationalite", request.getParameter("nationalite"));
        request.setAttribute("idDossier", request.getParameter("idDossier"));
        request.setAttribute("ine", request.getParameter("ine"));
        request.setAttribute("nom", request.getParameter("nom"));
        request.setAttribute("prenom", request.getParameter("prenom"));
        request.setAttribute("sexe", request.getParameter("sexe"));
        request.setAttribute("pays", request.getParameter("pays"));
        request.setAttribute("adresse", request.getParameter("adresse"));
        request.setAttribute("codePostal", request.getParameter("codePostal"));
        request.setAttribute("ville", request.getParameter("ville"));
        request.setAttribute("notes", request.getParameter("notes"));
        if(request.getParameter("nationalite") != null){
            if(request.getParameter("nationalite").equals(TypeJustificatifEtranger.etranger.toString())){
                request.setAttribute("avis", request.getParameter("avis"));
                request.setAttribute("niveau", request.getParameter("niveau"));
            }
        }else{
            request.setAttribute("nationalite", TypeJustificatifEtranger.francais.toString());
        }
        return new VoirAjoutDossierAction().execute(request, response); //modif: voir récupérer page precedente
    }
    
    private void validerFormulaire(String[] required, String[] requiredName) throws Exception{
        
        //verification de la validité du formulaire
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i] == null){
                empty.add(requiredName[i]);
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
