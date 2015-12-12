/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import modele.dao.MenuDAO;
import modele.entite.Menu;
import modele.entite.TypeCompte;

/**
 *
 * @author nicol
 */
public class MenuService {

    MenuDAO menuDAO;

    public MenuService() {
        this.menuDAO = new MenuDAO();
    }
    /**
     * Selectionne toutes les Actions de la base de donnée et l'encapsule dans une map
     * key = id de l'action
     * value = object action
     * 
     * @return la map contenant les actions
     */
    public List<Pair<Menu, List<Menu>>> SelectAlltoMap(){
        List<Menu> lesMenuRoot = menuDAO.SelectAllRoot();
        List<Pair<Menu, List<Menu>>> lesMenus = new ArrayList<>();
        
        for(Menu menuRoot : lesMenuRoot){
            List<Menu> lesMenuSub = menuDAO.SelectAllSubMenu(menuRoot);
            lesMenus.add(new Pair(menuRoot, lesMenuSub));
        }
        return lesMenus;
    }
    
    public Map<Menu, TypeCompte> SelectAlltoMapTypeCompte(){
        List<Menu> lesMenuRoot = menuDAO.SelectAllRoot();
        Map<Menu, TypeCompte> lesMenus = new HashMap<>();
        
        for(Menu menuRoot : lesMenuRoot){
            List<Menu> lesMenuSub = menuDAO.SelectAllSubMenu(menuRoot);
            TypeCompte type;
            if(lesMenuSub != null && !lesMenuSub.isEmpty()){
                type = lesMenuSub.get(0).getAction().getPage().getTypeAuthoriser();
                for(Menu m : lesMenuSub){
                    if(type.getValue() > m.getAction().getPage().getTypeAuthoriser().getValue()){
                        type = m.getAction().getPage().getTypeAuthoriser();
                    }
                }
            }else{
                type = menuRoot.getAction().getPage().getTypeAuthoriser();
            }
            lesMenus.put(menuRoot, type);
        }
        return lesMenus;
    }
}
