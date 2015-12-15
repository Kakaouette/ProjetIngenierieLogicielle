/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import page.action.Action;
import page.action.Etudiant.afficherDossierAction;
import page.action.Etudiant.connexionEtudiantAction;


/**
 *
 * @author Drachenfels
 */
@WebServlet(name = "Etudiant", urlPatterns = {"/Etudiant"})
public class Etudiant extends HttpServlet {

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
        
        Action classeAction = new connexionEtudiantAction();
        String vue = "";
        int menuSelect = 0;
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "index";
        }else if(action.equals("afficherDossier")){
            classeAction = new afficherDossierAction();
        }else if(action.equals("connexionEtudiant")){
            classeAction = new connexionEtudiantAction();
        }
                
        if (action.equals("index")) {
            RequestDispatcher rd = request.getRequestDispatcher("connexionEtudiant.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
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
