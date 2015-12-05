/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exception.AjoutFormationInvalideException;
import service.exception.SuppressionFormationInvalideException;
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
public class FormationService_supprimerFormationTest {
    
    public FormationService_supprimerFormationTest() {
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
    public void testSupprimerFormationNull(){
        System.out.println("===testSupprimerFormationNull===");
        //formation == null
        Formation formation = null;
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testSupprimerFormationVide(){
        System.out.println("===testSupprimerFormationVide===");
        //formation vide
        Formation formation = new Formation();
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        ;
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testSupprimerFormationIdNull(){
        System.out.println("===testSupprimerFormationIdNull===");
        //formation id null (champ requis)
        Formation formation = new Formation();
        formation.setDebut(new Date());
        formation.setFin(new Date());
        formation.setDescription("");
        formation.setIntitule("test");
        formation.setNombrePlace(0);
        formation.setLesJustificatifs(new ArrayList<Justificatif>());
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testSupprimerFormationIdInexistante(){
        System.out.println("===testSupprimerFormationIdInexistante===");
        //formation id inexistant
        int idInutilisee = 0;
        while(new FormationDAO().getById(idInutilisee) != null){
            idInutilisee++;
        }
        Formation formation = new Formation("", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        formation.setId(idInutilisee);
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertFalse(done);
        System.out.println("Test validé: " + (done == false));
    }
    @Test
    public void testSupprimerFormationValide(){
        System.out.println("===testSupprimerFormationValide===");
        //formation valide
        Formation formation = new Formation("", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
        } catch (AjoutFormationInvalideException ex) {
        }
        
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertTrue("Test validé: " + (done == true), done);
    }
}
