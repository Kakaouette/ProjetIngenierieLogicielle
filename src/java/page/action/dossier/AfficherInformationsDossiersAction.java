/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.dossier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import page.action.Action;
import service.DossierService;

/**
 *
 * @author Jordan
 */
public class AfficherInformationsDossiersAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des dossiers");
        List<Dossier> dossiers = new DossierDAO().SelectAll(); //recuperation des comptes pour la page suivante        
        List<Object[]> Tab = new ArrayList<Object[]>();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        boolean DossierUrgent = false;
        boolean DossierPerdu = false;
        DossierService service = new DossierService();

        for (Dossier c : dossiers) {
            Object[] o = new Object[9];
            
            String verifEtat = service.verifDossierPerdu(c);
            
            if(verifEtat.equals("Perdu") || verifEtat.equals("En retard")){
                DossierPerdu = true;
            }
            
            o[0] = service.verifDossierPerdu(c);
            o[1] = c.getId();
            o[2] = c.getAdmissible();
            o[3] = format.format(c.getDate());
            int tpsRestant = service.calculDeadlineDossier(c);

            if (tpsRestant <= 7 && tpsRestant >= 0){
                DossierUrgent = true;
                o[0] = o[0] + " - Urgent";
            }
            
            if (tpsRestant >= 0) { 
                o[4] = tpsRestant;
            }else{
                o[4] = "";
            }

            o[5] = c.getDemandeFormation().getIntitule();
            o[6] = c.getEtudiant().getNom();
            o[7] = c.getEtudiant().getPrenom();
            o[8] = "<a class=\\\"btn btn-info btn-block\\\" href='Navigation?action=consulterDossier&idDossier=" + c.getId() + "'><span class='fa fa-edit fa-2x'></span></a>";
            Tab.add(o);
        }
        request.setAttribute("addScript", ""
                + "\"createdRow\": function ( row, data, index ) {\n"
                + "            if (data[4] <= 7 && data[0] != 'Terminé') {\n"
                + "                       $(row).addClass('rouge');\n"
                + "            }else if ( data[0] == 'Perdu' || data[0] == 'En retard') {\n"
                + "                       $(row).addClass('jauneFonce');\n"
                + "            }else if ( data[0] == 'En attente de transfert vers le directeur' ) {\n"
                + "                       $('td', row).eq(0).addClass('pink');\n"
                + "            }else if ( data[0] == 'Transfert vers le secrétariat' ) {\n"
                + "                       $('td', row).eq(0).addClass('orange');\n"
                + "            }else if ( data[0] == 'Traité par le secrétariat' ) {\n"
                + "                       $('td', row).eq(0).addClass('jaune');\n"
                + "            }else if ( data[0] == 'Attente de la commission' ) {\n"
                + "                       $('td', row).eq(0).addClass('purple');\n"
                + "            }else if ( data[0] == 'Transfert vers le directeur' ) {\n"
                + "                       $('td', row).eq(0).addClass('bleu');\n"
                + "            }else if ( data[0] == 'Retour vers le secrétariat' ) {\n"
                + "                       $('td', row).eq(0).addClass('cyan');\n"
                + "            }else if ( data[0] == 'Terminé' ) {\n"
                + "                       $('td', row).eq(0).addClass('vert');\n"
                + "            }else if ( data[0] == 'Navette' ) {\n"
                + "                       $('td', row).eq(0).addClass('gris');\n"
                + "            }"
                + "        },\n");
        request.setAttribute("leTableau", Tab);
        request.setAttribute("sortL", 0);
        request.setAttribute("sortC", "asc");
        request.setAttribute("DossierUrgent", DossierUrgent);
        request.setAttribute("DossierPerdu", DossierPerdu);
        return "listeDossiers.jsp";
    }
}
