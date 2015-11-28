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
        Dossier dossier = new Dossier(new Date(), "UnEtat", "UneLettre", false, null, null);
        dossier.setId("NeDoitPasPasser");
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }
        catch(AjoutDossierInvalideException e)
        {
            assertEquals("123","123");
        }
        
        DossierDAO dossierDAO = new DossierDAO();
        //dossierDAO.
    }

    /**
     * Test of regexIdDossierValide method, of class DossierService.
     */
    @Test
    public void testRegexIdDossierValide() {
        System.out.println("regexIdDossierValide");
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
    }

    /**
     * Test of getRegexIdDossier method, of class DossierService.
     */
    @Test
    public void testGetRegexIdDossier() {
        System.out.println("getRegexIdDossier");
        String expResult = "pst(([0-2][0-9])|3[0-1])(0[0-9]|1[0-2])([0-9]{4})[0-9]+";
        String result = instance.getRegexIdDossier();
        assertEquals(expResult, result);
    }
    
}
