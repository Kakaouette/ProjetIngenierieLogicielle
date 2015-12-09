/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.CompteDAO;
import modele.dao.DossierDAO;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Historique;
import modele.entite.TypeCompte;
import modele.entite.TypeEtatDossier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author dorian
 */
public class DossierServiceTest {
    DossierService instance = new DossierService();
    DossierDAO dossierDAO = new DossierDAO();
    CompteDAO compteDAO = new CompteDAO();
    int idCompte; //id du compte créé pour les tests
    Compte compte;//Compte créé pour les tests
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        compte = new Compte("adminlogin", "mdpadmin", "Duvalle", "Pierre", "mail@mesmails.fr", TypeCompte.admin, null);
        compteDAO.save(compte);
        compte = compteDAO.getComptebyIdentifiant("adminlogin");
        idCompte = compte.getId();
        System.out.println(idCompte);
    }
    
    @After
    public void tearDown() {
        compteDAO.delete(idCompte);
    }
    
    /**
     * Méthode permettant de comparer deux dossiers
     * @param updatedDossier Le dossier modifier dans la base
     * @param dossierorigin Le dossier qui correspond au résultat attendu
     * @return true si les deux dossier sont identiques sinon return false
     */
    public boolean comparerDossiers(Dossier updatedDossier, Dossier dossierorigin){
        if(updatedDossier.getAdmissible() == dossierorigin.getAdmissible() &&
                updatedDossier.getEtat() == dossierorigin.getEtat() &&
                updatedDossier.getDate() == dossierorigin.getDate() &&
                updatedDossier.getLettre().equals(dossierorigin.getLettre())){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Méthode permettant de comparer deux historiques
     * @param histodossierupdated L'historique modifier dans la base
     * @param histodossier L'historique qui correspond au résultat attendu
     * @return true si les deux historiques sont identiques sinon return false
     */
    public boolean comparerHistoriques(List<Historique> histodossierupdated, List<Historique> histodossier){
        if (histodossier.size() == histodossierupdated.size()){
            for(int i = 0; i <histodossier.size(); i++){
                if (!histodossier.get(i).equals(histodossierupdated.get(i))){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }
    
    
    /**
     * Test de la modification du champ Etat du dossier id = pst181120151  (Premier dossier créer par GestULRPeuplement)
     * avec une note de modification
     */
    @Test
    public void testModifierEtatAvecNote(){
        Dossier dossierorigin = dossierDAO.getById("pst181120151");
        
        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        dossierorigin.setEtat(TypeEtatDossier.en_attente_commission);
        String messageHisto = "Modification pour test";
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage(messageHisto);
        histo.setAction("Changement d'état du dossier pour " + TypeEtatDossier.en_attente_commission + ".");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120151");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
    
    
    /**
     * Test de la modification du champ Etat du dossier id = pst181120152  (deuxième dossier créer par GestULRPeuplement)
     * sans note de modification
     */
    @Test
    public void testModifierEtatSansNote(){
        Dossier dossierorigin = dossierDAO.getById("pst181120152");
           
        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        dossierorigin.setEtat(TypeEtatDossier.en_transfert_vers_directeur);
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage("");
        histo.setAction("Changement d'état du dossier pour " + TypeEtatDossier.en_transfert_vers_directeur + ".");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120152");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
    
    /**
     * Test d'ajout d'une note sur le dossier id = pst181120153  (troisème dossier créer par GestULRPeuplement)
     */
    @Test
    public void testConserverEtatAvecNote(){
        Dossier dossierorigin = dossierDAO.getById("pst181120153");

        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage("Note pour test sans changement d'état.");
        histo.setAction("");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120153");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
    
    /**
     * Test d'ajout d'une note sur le dossier id = pst181120153  (troisème dossier créer par GestULRPeuplement)
     */
    @Test
    public void testAucunChangement(){
        Dossier dossierorigin=new DossierDAO().getById("pst181120154");

        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage("Note pour test sans changement d'état.");
        histo.setAction("");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //Récupération de l'historique du dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120154");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
}
