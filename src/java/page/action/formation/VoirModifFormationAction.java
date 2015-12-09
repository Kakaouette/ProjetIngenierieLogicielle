/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.formation;

import page.action.formation.VoirGestionFormationsAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;
import modele.entite.TypeDossier;
import modele.entite.TypeJustificatifEtranger;
import page.action.Action;

/**
 *
 * @author Arthur
 */
public class VoirModifFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Modifer une formation");
        
        //recuperation de l'id
        String idForm = request.getParameter("id");
        
        //verification de la validité du parametre
        String[] required = {idForm};
        String[] requiredName = {"ID de la formation"};
        try {
            validerFormulaire(required, requiredName);
        } catch (Exception ex) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", ex.getMessage());
            return new VoirGestionFormationsAction().execute(request, response); //redirection
        }
        
        int id = Integer.parseInt(idForm);
        Formation formation = new FormationDAO().getById(id);
        if(formation == null){
            try {
                throw new Exception("Formation inexistante.");
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return new VoirGestionFormationsAction().execute(request, response); //redirection
            }
        }
        if(formation.getDebut() != null && formation.getFin()!= null){
            if(formation.getDebut().before(new Date()) && formation.getFin().after(new Date())){ //verif formation editable
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", "La formation ne peut être modifier pendant la période d'inscription");
                return new VoirGestionFormationsAction().execute(request, response); //redirection
            }
        }
        
        //recuperation des données
        String intitule = formation.getIntitule();
        String description = formation.getDescription();
        int nbPlace = formation.getNombrePlace();
        Date debut = formation.getDebut();
        Date fin = formation.getFin();

        List<Justificatif> justificatifs = formation.getLesJustificatifs();
                
        //remplissage du formulaire
        if(request.getParameter("id") != null){
            request.setAttribute("id", idForm);
        }
        if(request.getParameter("intitule") == null){
            if(intitule != null){
                request.setAttribute("intitule", intitule);
            }
        }
        if(request.getParameter("description") == null){
            if(description != null){
                request.setAttribute("description", description);
            }
        }
        if(request.getParameter("nbPlace") != null){
            request.setAttribute("nbPlace", nbPlace);
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if(request.getParameter("dateDebut") == null){
            if(debut != null){
                request.setAttribute("dateDebut", df.format(debut));
            }
        }
        if(request.getParameter("dateFin") == null){
            if(fin != null){
                request.setAttribute("dateFin", df.format(fin));
            }
        }
        if(request.getAttribute("message") == null){
            if(justificatifs != null){
                String[] justificatifsToString = new String[0];
                for(Justificatif jTemp : justificatifs){
                    if(jTemp.getTypeAdmissible() == TypeDossier.inscription && jTemp.getTypeNationalite() == TypeJustificatifEtranger.francais){
                        justificatifsToString = addSlot(justificatifsToString); //add slot

                        justificatifsToString[justificatifsToString.length-1] = jTemp.getTitre();
                    }
                }
                request.setAttribute("justificatifsInscriptionFrancais", justificatifsToString);
                justificatifsToString = new String[0];
                for(Justificatif jTemp : justificatifs){
                    if(jTemp.getTypeAdmissible() == TypeDossier.admissibilite && jTemp.getTypeNationalite() == TypeJustificatifEtranger.francais){
                        justificatifsToString = addSlot(justificatifsToString); //add slot

                        justificatifsToString[justificatifsToString.length-1] = jTemp.getTitre();
                    }
                }
                request.setAttribute("justificatifsAdmissionFrancais", justificatifsToString);
                justificatifsToString = new String[0];
                for(Justificatif jTemp : justificatifs){
                    if(jTemp.getTypeAdmissible() == TypeDossier.inscription && jTemp.getTypeNationalite() == TypeJustificatifEtranger.etranger){
                        justificatifsToString = addSlot(justificatifsToString); //add slot

                        justificatifsToString[justificatifsToString.length-1] = jTemp.getTitre();
                    }
                }
                request.setAttribute("justificatifsInscriptionEtranger", justificatifsToString);
                justificatifsToString = new String[0];
                for(Justificatif jTemp : justificatifs){
                    if(jTemp.getTypeAdmissible() == TypeDossier.admissibilite && jTemp.getTypeNationalite() == TypeJustificatifEtranger.etranger){
                        justificatifsToString = addSlot(justificatifsToString); //add slot

                        justificatifsToString[justificatifsToString.length-1] = jTemp.getTitre();
                    }
                }
                request.setAttribute("justificatifsAdmissionEtranger", justificatifsToString);
            }
        }else{
            request.setAttribute("justificatifsInscriptionFrancais", request.getParameterValues("justificatifsInscriptionFrancais"));
            request.setAttribute("justificatifsAdmissionFrancais", request.getParameterValues("justificatifsAdmissionFrancais"));
            request.setAttribute("justificatifsInscriptionEtranger", request.getParameterValues("justificatifsInscriptionEtranger"));
            request.setAttribute("justificatifsAdmissionEtranger", request.getParameterValues("justificatifsAdmissionEtranger"));
        }
        
        return "modifFormation.jsp";
    }
    
    private void validerFormulaire(String[] required, String[] requiredName) throws Exception{
        
        //verification de la validité du formulaire
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i].equals("null")){
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
    
    public String[] addSlot(String[] tab){
        if (tab==null)
            tab = new String[1];
        else {
            int position = tab.length;
            String[] tabTemp = new String [position+1];
            for( int i = 0; i<tab.length; i++){
                 tabTemp[i] = tab[i];
            }
            tab=tabTemp;
        }
        return tab;
    }
}
