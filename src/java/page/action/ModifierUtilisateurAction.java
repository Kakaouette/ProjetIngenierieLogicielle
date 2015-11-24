/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.CompteDAO;
import modele.entite.Compte;
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

            Compte compte = new CompteDAO().getById(idCompte);

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

            Boolean update = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp);
            if (update == false) {
                System.out.println("test");
                request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
                return "modifierUtilisateur.jsp";
            } else {
                System.out.println("test2");
                request.setAttribute("message", "Modification effectuée");

                List<Compte> comptes;

                comptes = new CompteDAO().SelectAll();

                request.setAttribute("comptes", comptes);
                request.setAttribute("compte", compte);

                return "listeUtilisateurs.jsp";
            }
        } else {
            System.out.println("test3");
            request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
            return "modifierUtilisateur.jsp";
        }
    }

}
