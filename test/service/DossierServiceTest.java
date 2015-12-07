/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exception.AjoutDossierInvalideException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.AdresseDAO;
import modele.dao.CompteDAO;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
import modele.dao.FormationDAO;
import modele.dao.HistoriqueDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Etienne
 */
public class DossierServiceTest {
    DossierService instance = new DossierService();
    
    public DossierServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new DossierService();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getNewID method, of class DossierService.
     */
    @Test
    public void testGetNewID() {
        System.out.println("getNewID");
        
        DossierDAO dossierDAO = new DossierDAO();
        
        Date dateNow = new Date(); //recuperation de la date actuelle
        String dernierID = dossierDAO.getLastId(dateNow);
        
        //String id, Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation
        Etudiant etudiant = new EtudiantDAO().getEtudiantByNomPrenom("Jean","Pierre");
        String result1 = instance.getNewID();
        dossierDAO.save(new Dossier(result1, new Date(), "testEtat", null, true, etudiant, null));
        String result2 = instance.getNewID();
        dossierDAO.save(new Dossier(result2, new Date(), "testEtat", null, true, etudiant, null));
        String result3 = instance.getNewID();
        dossierDAO.save(new Dossier(result3, new Date(), "testEtat", null, true, etudiant, null));
        String result4 = instance.getNewID();
        dossierDAO.save(new Dossier(result4, new Date(), "testEtat", null, true, etudiant, null));

        ////        / DATE DU DOSSIER        / /    ID DOSSIER                      ////
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+0), result1);
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+1), result2);
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+2), result3);
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+3), result4);
    }

    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossier() throws Exception {
        System.out.println("ajouterDossier");
        Etudiant etudiant = new EtudiantDAO().getEtudiantByNomPrenom("Jean","Pierre");
        Formation formation = new FormationDAO().getFormationByIntitule("M1 ICONE");
        Compte c = new CompteDAO().getById(1);
        Historique historique = new Historique(new Date(), "Message", "Action", c);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        Dossier dossier = new Dossier(new Date(), "UnEtat", "UneLettre", false, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        dossier.setId("NeDoitPasPasser");
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }
        catch(AjoutDossierInvalideException e)
        {
        }
        /// //////////////////////////// TEST AVEC UN ID CORRECTE ////////////////////////////
        Adresse uneAdresse = new Adresse("test_codePoste", "test_Ville");
        Etudiant unEtudiant = new Etudiant("test_Nom", "test_Prenom", "test_adressePostale", "test_Homme", uneAdresse);
        dossier = new Dossier(new Date(), "UnEtat", "UneLettre", false, unEtudiant, formation, sesHistoriques);
        dossier.setId("pst151119750");
        instance.ajouterDossier(dossier);
        
        /// on vérifi son existance.
        Dossier cpt = new DossierDAO().getById("pst151119750");
        assertEquals(dossier, cpt);
        
        new AdresseDAO().delete(uneAdresse);
        new EtudiantDAO().delete(unEtudiant);
        new DossierDAO().delete(dossier);
        
        //new AdresseDAO().delete(uneAdresse);
        //new EtudiantDAO().delete(unEtudiant);
        //dossierDAO.
    }

    /**
     * Test of regexIdDossierValide method, of class DossierService.
     */
    @Test
    public void testRegexIdDossierValide() {
        System.out.println("regexIdDossierValide");
        try {
            assertEquals(true, instance.regexIdDossierValide("pst151120110"));
            assertEquals(false, instance.regexIdDossierValide("pst251220151"));
            assertEquals(true, instance.regexIdDossierValide("pst171119922"));
            assertEquals(true, instance.regexIdDossierValide("pst151120113"));
            assertEquals(true, instance.regexIdDossierValide("pst150020111"));
            assertEquals(false, instance.regexIdDossierValide("pst"));
            assertEquals(false, instance.regexIdDossierValide(""));
            assertEquals(false, instance.regexIdDossierValide("pst451120110"));
            assertEquals(false, instance.regexIdDossierValide("pst601120111"));
            assertEquals(false, instance.regexIdDossierValide("pst151920110"));
        } catch (Exception ex) {
            Logger.getLogger(DossierServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getRegexIdDossier method, of class DossierService.
     */
    @Test
    public void testGetRegexIdDossier() {
        System.out.println("getRegexIdDossier");
        String expResult = "pst(([0-2][0-9])|3[0-1])(0[0-9]|1[0-2])([0-9]{4})[0-9]+";
        String result = "";
        try {
            result = instance.getRegexIdDossier();
        } catch (IOException ex) {
            Logger.getLogger(DossierServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, result);
    }
    
}
