/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import service.exception.AjoutFormationInvalideException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modele.dao.FormationDAO;
import modele.dao.JustificatifDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;
import modele.entite.TypeDossier;
import modele.entite.TypeJustificatifEtranger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import service.exception.AjoutJustificatifInvalideException;

/**
 *
 * @author Arthur
 */
public class FormationService_ajouterFormationTest {
    Formation formation;
    Formation formation2;
    Justificatif justificatif;
    
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
        justificatif = new Justificatif("t", "d", TypeDossier.inscription, TypeJustificatifEtranger.francais);
    }
    
    @After
    public void tearDown() {
        try {
            new FormationDAO().delete(formation.getId());
        } catch (Exception e) {}
        try {
            new FormationDAO().delete(formation2.getId());
        } catch (Exception e) {}
        try {
            new JustificatifDAO().delete(justificatif.getId());
        } catch (Exception e) {}
    }
    
    boolean done;
    @Test
    public void testAjouterFormationNull(){
        System.out.println("===testAjouterFormationNull===");
        //formation == null
        formation = null;
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testAjouterFormationVide(){
        System.out.println("===testAjouterFormationVide===");
        //formation vide
        formation = new Formation();
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testAjouterFormationIntituleNull(){
        System.out.println("===testAjouterFormationIntituleNull===");
        //formation intitulé null (champ requis)
        formation = new Formation("", 0, new Date(), new Date(), null, new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testAjouterFormationIntituleStrVide(){
        System.out.println("===testAjouterFormationIntituleStrVide===");
        //formation intitulé "" (champ requis)
        formation = new Formation("", 0, new Date(), new Date(), "", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testAjouterFormationParametresNull(){
        System.out.println("===testAjouterFormationParametresNull===");
        //formation parametre null
        formation = new Formation(null, 0, null, null, "testParametreNull", null);
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
    public void testAjouterFormationDescriptionVide(){
        System.out.println("===testAjouterFormationDescriptionVide===");
        //formation parametre null
        formation = new Formation("",0,new Date(), new Date(), "Test",new ArrayList <>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
    }
    @Test
    public void testAjouterFormationDatesIncoherantes(){
        System.out.println("===testAjouterFormationDatesIncoherantes===");
        //formation dates de début après dates de fin
        formation = new Formation("", 0, new Date(0, 0, 2), new Date(0, 0, 1), "datesIncoherantes", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testAjouterFormationIdExistant(){
        System.out.println("===testAjouterFormationIdExistant===");
        //formation parametre null
        formation = new Formation("d", 0, new Date(), new Date(), "testIdExistant", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {}
        
        //id déjà dans la BDD
        formation.setDescription("nd");
        formation.setIntitule("ni");
        formation.setNombrePlace(1);
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testAjouterFormationIntituleExistant(){
        System.out.println("===testAjouterFormationIntituleExistant===");
        //formation parametre null
        formation = new Formation("d", 0, new Date(), new Date(), "testSameIntitule", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {}
        
        //intitulé déjà dans la BDD
        formation2 = new Formation("d", 0, new Date(), new Date(), "testSameIntitule", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testAjouterFormationValide(){
        System.out.println("===testAjouterFormationValide===");
        //formation valide
        formation = new Formation("d", 0, new Date(), new Date(), "valide", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
    public void testAjouterFormationValidePlusJustificatif(){
        System.out.println("===testAjouterFormationValidePlusJustificatif===");
        //formation valide
        List<Justificatif> justificatifs = new ArrayList<Justificatif>();
        justificatifs.add(justificatif);
        formation = new Formation("d", 0, new Date(), new Date(), "valide", justificatifs);
        try {
            new FormationService().ajouterFormation(formation);
            done = true;
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
}
 