/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.utilisateur;

import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.CompteDAO;
import modele.dao.FormationDAO;
import modele.entite.Compte;
import modele.entite.Formation;
import modele.entite.TypeCompte;
import page.action.Action;
import service.CompteService;

/**
 *
 * @author Jordan
 */
public class ModifierUtilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des comptes");

        String valueButton = request.getParameter("bouton");
        System.out.println(valueButton);

        if (valueButton.equals("enregistrer")) {
            //System.out.println("test");
            String type = request.getParameter("type");
            String login = request.getParameter("login");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mail = request.getParameter("email");
            String mdp = request.getParameter("motDePasse");
            int idCompte = Integer.parseInt(request.getParameter("id"));
            String[] form = request.getParameterValues("formations");

            List<Formation> lesFormations = new ArrayList<>();
            FormationDAO formationDAO = new FormationDAO();
            boolean aucuneFormations = false;
            if (form != null) {
                for (String idS : form) {
                    if (idS.equals("Aucune formation") && !(!type.equals(TypeCompte.responsable_formation.toString()) || !type.equals(TypeCompte.responsable_commission.toString()) || !type.equals(TypeCompte.secrétaire_formation.toString()))) {
                        lesFormations.clear();
                        //System.out.println("test");
                        break;
                    } else if (idS.equals("Aucune formation") && !(type.equals(TypeCompte.responsable_formation.toString()) || type.equals(TypeCompte.responsable_commission.toString()) || type.equals(TypeCompte.secrétaire_formation.toString()))) {
                        aucuneFormations = true;
                        //System.out.println("test1");
                        break;
                    } else {
                        //System.out.println("test2");
                        try {
                            int id = Integer.parseInt(idS);
                            lesFormations.add(formationDAO.getById(id));
                        } catch (Exception e) {
                        }
                    }
                }
            }

            Compte compte = new CompteDAO().getById(idCompte);

            if (aucuneFormations == true) {
                lesFormations = compte.getFormationAssocie();
            }

            compte.setFormationAssocie(lesFormations);

            if (compte == null) {
                return new VoirGestionUtilisateurAction().execute(request, response);
            }

            if (type == null) {
                type = compte.getType().name();
            }
            if (nom == null) {
                nom = compte.getNom();
            }
            if (prenom == null) {
                prenom = compte.getPrenom();
            }
            if (mail == null) {
                mail = compte.getMail();
            }
            if (mdp == null) {
                mdp = compte.getMdp();
            }

            try {
                InternetAddress emailAddr = new InternetAddress(mail);
                emailAddr.validate();
            } catch (AddressException ex) {
                request.setAttribute("message", "ERREUR : L'adresse email n'est pas valide");
                return new VoirModifierComptesAction().execute(request, response);
            }

            Boolean update = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp, lesFormations);
            if (update == false) {
                //System.out.println("test");
                request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
                request.setAttribute("login", login);
                request.setAttribute("type", TypeCompte.valueOf(type));
                request.setAttribute("nom", nom);
                request.setAttribute("prenom", prenom);
                request.setAttribute("email", mail);
                request.setAttribute("formation", lesFormations);
                return new VoirModifierComptesAction().execute(request, response);
            } else if (aucuneFormations == true) {
                request.setAttribute("message", "WARNING : Les modification ont été effectuée, cependant cet utilisateur necessite d'être associé à des formations. Or il n'en possèdait aucune lors de votre modification. La modification des formations à donc été ignorée.");
                request.setAttribute("login", login);
                System.out.println(TypeCompte.valueOf(type));
                System.out.println(compte.getType().toString());
                request.setAttribute("type", TypeCompte.valueOf(type));
                request.setAttribute("nom", nom);
                request.setAttribute("prenom", prenom);
                request.setAttribute("email", mail);
                request.setAttribute("formation", lesFormations);
                return new VoirModifierComptesAction().execute(request, response);
            } else {
                request.setAttribute("message", "Modification effectuée");

                return new VoirGestionUtilisateurAction().execute(request, response);
            }
        } else {
            System.out.println("test3");
            request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
            return "modifierUtilisateur.jsp";
        }
    }

}
