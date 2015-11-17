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
        
        //init de l'interface
        Action classeAction = null;
        String vue = "";
        int menuSelect = 0;

        if (action.equals("gererAuthentification")) {
            menuSelect = 0;
            classeAction = new GererAuthentificationAction();
        }else if (action.equals("voirModifierUtilisateur")) {
            menuSelect = 0; //à modifier plus tard
            classeAction = new VoirModifierUtiilisateurAction();
        }else if (action.equals("afficherInformationsUtilisateur")) {
            menuSelect = 0; //à modifier plus tard
            classeAction = new AfficherInformationsUtilisateurAction();
        }else if (action.equals("modifierUtilisateur")) {
            menuSelect = 0; //à modifier plus tard
            classeAction = new ModifierUtilisateurAction();
        }else{
            action = "index";
            menuSelect = 0;
            classeAction = new VoirIndexAction();
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
