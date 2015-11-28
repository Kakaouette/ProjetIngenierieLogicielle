/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
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
        SimpleDateFormat formater = new SimpleDateFormat("ddMMyyyy");
        String str = "pst" + formater.format(dateNow);
        
        String dernierID = dossierDAO.getLastId(dateNow);
        
        String result1 = instance.getNewID();
        String result2 = instance.getNewID();
        String result3 = instance.getNewID();
        String result4 = instance.getNewID();
        assertEquals(str+(dernierID+1), result1);
        assertEquals(str+(dernierID+2), result2);
        assertEquals(str+(dernierID+3), result3);
        assertEquals(str+(dernierID+4), result4);
    }

    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossier() throws Exception {
        System.out.println("ajouterDossier");
        Dossier dossier = null;
        instance.ajouterDossier(dossier);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of regexIdDossierValide method, of class DossierService.
     */
    @Test
    public void testRegexIdDossierValide() {
        System.out.println("regexIdDossierValide");
        String idDossier = "";
        boolean expResult = false;
        boolean result = instance.regexIdDossierValide(idDossier);
        assertEquals(expResult, result);
        // TODO
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegexIdDossier method, of class DossierService.
     */
    @Test
    public void testGetRegexIdDossier() {
        System.out.println("getRegexIdDossier");
        String expResult = "";
        String result = instance.getRegexIdDossier();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
