/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modele.dao.CompteDAO;
import modele.dao.HistoriqueDAO;
import modele.entite.Compte;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeCompte;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Val
 */
public class CompteServiceTest {
    
    public CompteServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of verifierAuthentification method, of class CompteService.
     */
    /*
    @Test
    public void testVerifierAuthentification() {
        System.out.println("verifierAuthentification");
        CompteService instance = new CompteService();
        String identifiantTest = "admin";
        String mdpTest = "azerty";
        Compte result = instance.verifierAuthentification(identifiantTest, mdpTest);
        assertNotNull(result);
    }
    
    @Test
    public void testVerifierAuthentificationIDNull() {
        System.out.println("verifierAuthentification ID Null");
        CompteService instance = new CompteService();
        String identifiantTest = null;
        String mdpTest = "azerty";
        
        Compte result = instance.verifierAuthentification(identifiantTest, mdpTest);
        assertNull(result);
    }
    
    @Test
    public void testVerifierAuthentificationMdpNull() {
        System.out.println("verifierAuthentification Mdp Null");
        CompteService instance = new CompteService();
        String identifiantTest = "admin";
        String mdpTest = null;
        
        Compte result = instance.verifierAuthentification(identifiantTest, mdpTest);
        assertNull(result);
    }
    
    @Test
    public void testVerifierAuthentificationNomNonExistant() {
        System.out.println("verifierAuthentification Nom faux");
        CompteService instance = new CompteService();
        String identifiantTest = "jenexistepas";
        String mdpTest = "azerty";
        
        Compte result = instance.verifierAuthentification(identifiantTest, mdpTest);
        assertNull(result);
    }
    
    @Test
    public void testVerifierAuthentificationMauvaisPass() {
        System.out.println("verifierAuthentification mauvais pass");
        CompteService instance = new CompteService();
        String identifiantTest = "admin";
        String mdpTest = "blabla";
        
        Compte result = instance.verifierAuthentification(identifiantTest, mdpTest);
        assertNull(result);
    }
    */
    @Test
    public void testSupprimerUtilisateurNonExistant() {
        System.out.println("SupprimerUtilisateur utisateur non existant");
        CompteService instance = new CompteService();
        String identifiantTest = "AucunChanceDapparaitre_j_j_j_j_dj_sjqfdjs_qf_ds_qfd";
        String mdpTest = "Sproutch";
        
        boolean done = instance.supprimerUtilisateur(identifiantTest);
        assertFalse(done);
    }
    
    @Test
    public void testSupprimerUtilisateurSansHistorique() {
        System.out.println("SupprimerUtilisateur sans historique");
        CompteService instance = new CompteService();
        CompteDAO compteDAO = new CompteDAO();
        
        List<Formation> uneListeDeFormationVide = new ArrayList<Formation>();
        
        //String login, String mdp, String nom, String prenom, String mail, TypeCompte type,
        String identifiantTest = "test1";
        String mdpTest = "pass";
        Compte unCompte = new Compte(identifiantTest, mdpTest, "NomTest", "PrenomTest", "a@e.com", TypeCompte.admin, uneListeDeFormationVide);
        compteDAO.save(unCompte);
        
        boolean done = instance.supprimerUtilisateur(identifiantTest);
        assertTrue(done);
    }
    @Test
    public void testSupprimerUtilisateurAvecHistorique() {
        System.out.println("SupprimerUtilisateur avec historique");
        CompteService instance = new CompteService();
        CompteDAO compteDAO = new CompteDAO();
        HistoriqueDAO histoDAO = new HistoriqueDAO();
        
        List<Formation> uneListeDeFormationVide = new ArrayList<Formation>();
        
        //String login, String mdp, String nom, String prenom, String mail, TypeCompte type,
        String identifiantTest = "test";
        String mdpTest = "pass";
        Compte unCompte = new Compte(identifiantTest, mdpTest, "NomTest", "PrenomTest", "a@e.com", TypeCompte.admin, uneListeDeFormationVide);
        
        //Date date, String message, String action, Compte compte
        Historique historique = new Historique(new Date(),"Simple test unitaire de suppression de compte","simple test unitaire de suppression de compte",unCompte);
        compteDAO.save(unCompte);
        unCompte = compteDAO.getComptebyIdentifiant(unCompte.getLogin());
        histoDAO.save(historique);
        
        
        boolean done = instance.supprimerUtilisateur(identifiantTest);
        assertTrue(done);
    }
}
