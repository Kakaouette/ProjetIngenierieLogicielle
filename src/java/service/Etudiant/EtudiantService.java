/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Etudiant;

import java.util.List;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
import modele.entite.Dossier;
import modele.entite.Etudiant;

/**
 *
 * @author Drachenfels
 */
public class EtudiantService {
    private EtudiantDAO dao;
    
    public Etudiant verifAuthentification(String ine, String nom, String prenom){
        dao = new EtudiantDAO();
        System.out.println(ine);
        Etudiant etu = dao.getEtudiantByINE(ine);
        
        if(etu != null){
            if(etu.getNom().equalsIgnoreCase(nom) && etu.getPrenom().equalsIgnoreCase(prenom)){
                return etu;
            }
        }
        return null;
    }
    
    public List<Dossier> dossiersEtu(Etudiant etu){
        return new DossierDAO().getByEtudiant(etu);
    }
}
