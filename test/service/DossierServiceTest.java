/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.AdresseDAO;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
import modele.entite.Adresse;
import modele.entite.Dossier;
import modele.entite.Etudiant;
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

        System.out.println("----result4:"+result4);
        System.out.println("----result4_:"+dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+3));
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
        Dossier dossier = new Dossier(new Date(), "UnEtat", "UneLettre", false, null, null);
        dossier.setId("NeDoitPasPasser");
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }
        catch(AjoutDossierInvalideException e)
        {
        }
        dossier.setId("pst151119700");
        Adresse uneAdresse = new Adresse("test_codePoste", "test_Ville");
        Etudiant unEtudiant = new Etudiant("test_Nom", "test_Prenom", "test_adressePostale", "test_Homme", uneAdresse);
        instance.ajouterDossier(dossier);
        
        /// on vérifi son existance.
        Dossier cpt = new DossierDAO().getById("pst151119700");
        assertEquals(dossier, cpt);
        
        new DossierDAO().delete(dossier);
        
        new AdresseDAO().delete(uneAdresse);
        new EtudiantDAO().delete(unEtudiant);
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
            assertEquals(true, instance.regexIdDossierValide("pst251220151"));
            assertEquals(true, instance.regexIdDossierValide("pst171119922"));
            assertEquals(true, instance.regexIdDossierValide("pst151120113"));
            assertEquals(false, instance.regexIdDossierValide("pst"));
            assertEquals(false, instance.regexIdDossierValide(""));
            assertEquals(false, instance.regexIdDossierValide("pst451120110"));
            assertEquals(false, instance.regexIdDossierValide("pst601120111"));
            assertEquals(false, instance.regexIdDossierValide("pst151920110"));
            assertEquals(false, instance.regexIdDossierValide("pst150020111"));
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
