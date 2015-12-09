/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.web;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import page.action.*;

/**
 *
 * @author roulonn
 */
@WebServlet(name = "Navigation", urlPatterns = {"/Navigation"})
public class Navigation extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    @Override
    public void init() {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //récupération de la variable action
        String action = request.getParameter("action");

        if (action == null) {
            action = "index";
        }

        Compte compteActif = (Compte) request.getSession().getAttribute("compte");
        
        //init de l'interface
        Action classeAction = null;
        String vue = "";
        int menuSelect = 0;

        if (action.equals("gererAuthentification")) {
            menuSelect = 0;

        }/***Gestion formations***/
        else if (action.equals("voirAjoutFormation")) {
            menuSelect = 2;
            classeAction = new VoirAjoutFormationAction();
        }else if (action.equals("ajouterFormation")) {
            menuSelect = 2;
            classeAction = new AjoutFormationAction();
        }else if (action.equals("supprimerFormation")) {
            menuSelect = 2;
            classeAction = new SupprFormationAction();
        }else if (action.equals("voirModifFormation")) {
            menuSelect = 2;
            classeAction = new VoirModifFormationAction();
        }else if (action.equals("modifierFormation")) {
            menuSelect = 2;
            classeAction = new ModifFormationAction();
        }else if (action.equals("voirGestionFormations")) {
            menuSelect = 2;
            classeAction = new VoirGestionFormationsAction();
        }else if (action.equals("voirGestionDatesInscription")) {
            menuSelect = 2;
            classeAction = new VoirGestionDatesInscriptionAction();
        }else if (action.equals("modiferDatesInscription")) {
            menuSelect = 2;
            classeAction = new ModifDatesInscriptionAction();
        }

        if (classeAction != null) {
            //vue récupére le nom de la jsp a afficher
            vue = classeAction.execute(request, response);
            
            //menu a mettre en surbrillance
            request.setAttribute("menu", menuSelect);
            
            //affichage de la jsp
            RequestDispatcher rd = request.getRequestDispatcher(vue);
            if (rd != null) {
                rd.forward(request, response);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
