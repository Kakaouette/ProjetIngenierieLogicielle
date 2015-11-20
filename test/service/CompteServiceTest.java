/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modele.entite.Compte;
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

}
