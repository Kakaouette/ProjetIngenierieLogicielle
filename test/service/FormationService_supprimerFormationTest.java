/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import service.exception.AjoutFormationInvalideException;
import service.exception.SuppressionFormationInvalideException;
import java.util.ArrayList;
import java.util.Date;
import modele.dao.FormationDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import service.exception.AjoutJustificatifInvalideException;
import service.exception.SuppressionJustificatifInvalideException;

/**
 *
 * @author Arthur
 */
public class FormationService_supprimerFormationTest {
    Formation formation;
    
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
        try {
            new FormationDAO().delete(formation.getId());
        } catch (Exception e) {}
    }

    boolean done;
    @Test
    public void testSupprimerFormationNull(){
        System.out.println("===testSupprimerFormationNull===");
        //formation == null
        formation = null;
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testSupprimerFormationVide(){
        System.out.println("===testSupprimerFormationVide===");
        //formation vide
        formation = new Formation();
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testSupprimerFormationIdNull(){
        System.out.println("===testSupprimerFormationIdNull===");
        //formation id null (champ requis)
        formation = new Formation();
        formation.setDebut(new Date());
        formation.setFin(new Date());
        formation.setDescription("");
        formation.setIntitule("test");
        formation.setNombrePlace(0);
        formation.setLesJustificatifs(new ArrayList<Justificatif>());
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testSupprimerFormationIdInexistante(){
        System.out.println("===testSupprimerFormationIdInexistante===");
        //formation id inexistant
        int idInutilisee = 0;
        while(new FormationDAO().getById(idInutilisee) != null){
            idInutilisee++;
        }
        formation = new Formation("d", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        formation.setId(idInutilisee);
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testSupprimerFormationValide(){
        System.out.println("===testSupprimerFormationValide===");
        //formation valide
        formation = new Formation("d", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {}
        
        try {
            new FormationService().supprimerFormation(formation);
            done = true;
        } catch (SuppressionFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertTrue("Test validé: " + (done == true && new FormationDAO().getById(formation.getId()) == null), done && new FormationDAO().getById(formation.getId()) == null);
    }
}
