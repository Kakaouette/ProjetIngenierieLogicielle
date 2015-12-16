/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modele.dao.ActionDAO;
import modele.dao.MenuDAO;
import modele.dao.PageDAO;
import modele.entite.Action;
import modele.entite.Menu;
import modele.entite.Page;

/**
 *
 * @author nicol
 */
public class ActionService {

    ActionDAO actionDAO;

    public ActionService() {
        this.actionDAO = new ActionDAO();
    }

    /**
     * Selectionne toutes les Actions de la base de donnée et l'encapsule dans
     * une map key = id de l'action value = object action
     *
     * @return la map contenant les actions
     */
    public Map<String, Action> SelectAlltoMap() {
        List<Action> lesActions = actionDAO.SelectAll();
        Map<String, Action> mapActions = new HashMap<>();
        for (Action action : lesActions) {
            mapActions.put(action.getId(), action);
        }
        return mapActions;
    }
}
