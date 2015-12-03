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
import modele.dao.FormationDAO;
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
public class FormationService_modifierFormationTest {
    
    public FormationService_modifierFormationTest() {
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
    public void testModifierFormationNull(){
        System.out.println("===testModifierFormationNull===");
        //formation == null
        Formation formation = null;
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testModifierFormationVide(){
        System.out.println("===testModifierFormationVide===");
        //formation vide
        Formation formation = new Formation();
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testModifierFormationIdNull(){
        System.out.println("===testModifierFormationIdNull===");
        //formation id null (champ requis)
        Formation formation = new Formation();
        formation.setDebut(new Date());
        formation.setFin(new Date());
        formation.setDescription("");
        formation.setIntitule("test");
        formation.setNombrePlace(0);
        formation.setLesJustificatifs(new ArrayList<Justificatif>());
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testModifierFormationIdInexistante(){
        System.out.println("===testModifierFormationIdInexistante===");
        //formation id inexistant
        int idInutilisee = 0;
        while(new FormationDAO().getById(idInutilisee) != null){
            idInutilisee++;
        }
        Formation formation = new Formation("", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        formation.setId(idInutilisee);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testModifierFormationIntituleExistant(){
        System.out.println("===testModifierFormationIntituleExistant===");
        //formation intitulé déjà utilisé par une formation
        int idUtilisee = 0;
        while(new FormationDAO().getById(idUtilisee) == null){
            idUtilisee++;
        }
        Formation formation = new Formation("", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
        } catch (AjoutFormationInvalideException ex) {
        }
        
        String intituleUtilisee = new FormationDAO().getById(idUtilisee).getIntitule();
        formation.setIntitule(intituleUtilisee);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testModifierFormationDatesIncoherantes(){
        System.out.println("===testModifierFormationDatesIncoherantes===");
        //formation dates de début après dates de fin
        Formation formation = new Formation("", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
        } catch (AjoutFormationInvalideException ex) {
        }
        
        formation = new Formation("", 0, new Date(0, 0, 2), new Date(0, 0, 1), "datesIncoherantes", new ArrayList<Justificatif>());
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
}
