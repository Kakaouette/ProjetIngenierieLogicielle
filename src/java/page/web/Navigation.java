/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.PageDAO;
import page.action.Action;
import page.action.accueil.*;
import service.ActionService;
import service.MenuService;

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
        if (this.getServletContext().getAttribute("action") == null) {
            this.getServletContext().setAttribute("action", new ActionService().SelectAlltoMap());
        }
        if (this.getServletContext().getAttribute("menuSelect") == null) {
            this.getServletContext().setAttribute("menuSelect", new ActionService().SelectAllSurbrillance());
        }
        if (this.getServletContext().getAttribute("menu") == null) {
            this.getServletContext().setAttribute("menu", new MenuService().SelectAlltoMap());
        }
        if (this.getServletContext().getAttribute("menuType") == null) {
            this.getServletContext().setAttribute("menuType", new MenuService().SelectAlltoMapTypeCompte());
        }
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

        Map<String, modele.entite.Action> lesActions = (Map<String, modele.entite.Action>) this.getServletContext().getAttribute("action");
        Map<String, Integer> lesActionMenuSelect = (Map<String, Integer>) this.getServletContext().getAttribute("menuSelect");
        try {
            Class classeActionImpl = Class.forName("page.action." + lesActions.get(action).getClassAction()); // Accès à la classe DAO correspondant
            Constructor constr = classeActionImpl.getConstructor(); // Obtenir le constructeur ()
            classeAction = (Action) constr.newInstance(); // -> new xDao()
        } catch (Exception e){
            classeAction = new VoirIndexAction();
        }

        if (classeAction != null) {
            //vue récupére le nom de la jsp a afficher
            vue = classeAction.execute(request, response);
            //menu a mettre en surbrillance

            Integer menu = lesActionMenuSelect.get(action);
            
            if(menu == null){
                menu = lesActionMenuSelect.get("index");
            }
            
            request.setAttribute("titre", new PageDAO().getById(vue).getTitre());
            request.setAttribute("menuS", menu);


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
