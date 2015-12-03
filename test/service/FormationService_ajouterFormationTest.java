/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.entite.Formation;
import modele.entite.Justificatif;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arthur
 */
public class FormationService_ajouterFormationTest {
    
    public FormationService_ajouterFormationTest() {
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
    
    boolean done;
    @Test
    public void testAjouterFormationNull(){
        System.out.println("===testAjouterFormationNull===");
        //formation == null
        Formation formation = null;
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testAjouterFormationVide(){
        System.out.println("===testAjouterFormationVide===");
        //formation vide
        Formation formation = new Formation();
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testAjouterFormationIntituleNull(){
        System.out.println("===testAjouterFormationIntituleNull===");
        //formation intitulé null (champ requis)
        Formation formation = new Formation("", 0, new Date(), new Date(), null, new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testAjouterFormationIntituleStrVide(){
        System.out.println("===testAjouterFormationIntituleStrVide===");
        //formation intitulé "" (champ requis)
        Formation formation = new Formation("", 0, new Date(), new Date(), "", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testAjouterFormationParametresNull(){
        System.out.println("===testAjouterFormationParametresNull===");
        //formation parametre null
        Formation formation = new Formation(null, 0, null, null, "test", null);
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertTrue(done);
        System.out.println("Test validé: " + (done == true));
        
        //id déjà dans la BDD
        Formation formation2 = new Formation(null, 0, null, null, "sameID", null);
        formation2.setId(formation.getId());
        try {
            new FormationService().ajouterFormation(formation2);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
        
        //intitulé déjà dans la BDD
        formation = new Formation("", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testAjouterFormationDatesIncoherantes(){
        System.out.println("===testAjouterFormationDatesIncoherantes===");
        //formation dates de début après dates de fin
        Formation formation = new Formation("", 0, new Date(0, 0, 2), new Date(0, 0, 1), "datesIncoherantes", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testAjouterFormationValide(){
        System.out.println("===testAjouterFormationValide===");
        //formation valide
        Formation formation = new Formation("", 0, new Date(), new Date(), "valide", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertTrue(done);
        System.out.println("Test validé: " + (done == true));
    }
}
