/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import service.exception.AjoutFormationInvalideException;
import service.exception.ModificationFormationInvalideException;
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
import service.exception.SuppressionJustificatifInvalideException;

/**
 *
 * @author Arthur
 */
public class FormationService_modifierFormationTest {
    Formation formation;
    Formation formation2;
    Formation formationAvecJustificatif;
    Justificatif justificatif;
    
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
        //formation valide
        formation = new Formation("d", 0, new Date(), new Date(), "test", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation);
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {}
        formation = new FormationDAO().getFormationByIntitule(formation.getIntitule());
        
        formation2 = new Formation("d", 0, new Date(), new Date(), "test2", new ArrayList<Justificatif>());
        try {
            new FormationService().ajouterFormation(formation2);
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
        }
        formation2 = new FormationDAO().getFormationByIntitule(formation2.getIntitule());
        
        justificatif = new Justificatif("t", TypeDossier.inscription, TypeJustificatifEtranger.francais);
        List<Justificatif> justificatifs = new ArrayList<Justificatif>();
        justificatifs.add(justificatif);
        formationAvecJustificatif = new Formation("d", 0, new Date(), new Date(), "formationAvecJustificatif", justificatifs);
        try {
            new FormationService().ajouterFormation(formationAvecJustificatif);
        } catch (AjoutFormationInvalideException | AjoutJustificatifInvalideException | IOException ex) {
        }
        formationAvecJustificatif = new FormationDAO().getFormationByIntitule(formationAvecJustificatif.getIntitule());
        justificatif = formationAvecJustificatif.getLesJustificatifs().get(0);
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
        try {
            new FormationDAO().delete(formationAvecJustificatif.getId());
        } catch (Exception e) {}
    }

    boolean done;
    @Test
    public void testModifierFormationNull(){
        System.out.println("===testModifierFormationNull===");
        //formation == null
        formation = null;
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testModifierFormationVide(){
        System.out.println("===testModifierFormationVide===");
        //formation vide
        formation = new Formation();
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testModifierFormationIdNull(){
        System.out.println("===testModifierFormationIdNull===");
        //formation id null (champ requis)
        formation = new Formation();
        formation.setDebut(new Date());
        formation.setFin(new Date());
        formation.setDescription("");
        formation.setIntitule("test");
        formation.setNombrePlace(0);
        formation.setLesJustificatifs(new ArrayList<Justificatif>());
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testModifierFormationIdInexistante(){
        System.out.println("===testModifierFormationIdInexistante===");
        //formation id inexistant
        int idInutilisee = 0;
        while(new FormationDAO().getById(idInutilisee) != null){
            idInutilisee++;
        }
        formation.setId(idInutilisee);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testModifierFormationIntituleExistant(){
        System.out.println("===testModifierFormationIntituleExistant===");
        //formation intitulé déjà utilisé par une formation        
        String intituleUtilisee = formation2.getIntitule();
        formation.setIntitule(intituleUtilisee);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testModifierFormationDatesIncoherantes(){
        System.out.println("===testModifierFormationDatesIncoherantes===");
        //formation dates de début après dates de fin
        formation.setDebut(new Date(0, 0, 2));
        formation.setFin(new Date(0, 0, 1));
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    
    @Test
    public void testModifierFormationDescriptionNull(){
        System.out.println("===testModifierFormationDescriptionNull===");
        //formation dates de début après dates de fin
        formation = new FormationDAO().getFormationByIntitule(formation.getIntitule());
        formation.setDescription(null);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
    public void testModifierFormationDescriptionValide(){
        System.out.println("===testModifierFormationDescriptionValide===");
        //formation dates de début après dates de fin
        formation.setDescription("nd");
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
    public void testModifierFormationDateDebutNull(){
        System.out.println("===testModifierFormationDateDebutNull===");
        formation.setDebut(null);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
    public void testModifierFormationDateFinNull(){
        System.out.println("===testModifierFormationDateFinNull===");
        formation.setFin(null);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
    public void testModifierFormationIntituleNull(){
        System.out.println("===testModifierFormationIntituleNull===");
        formation.setIntitule(null);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == false));
        assertFalse(done);
    }
    @Test
    public void testModifierFormationIntituleValide(){
        System.out.println("===testModifierFormationIntituleNull===");
        formation.setIntitule("ni");
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
     public void testModifierFormationNbPlaceValide(){
        System.out.println("===testModifierFormationNbPlaceValide===");
        formation.setNombrePlace(1);
        try {
            new FormationService().modifierFormation(formation);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formation.equals(new FormationDAO().getById(formation.getId()))));
        assertTrue(done && formation.equals(new FormationDAO().getById(formation.getId())));
    }
    @Test
     public void testModifierFormationJustificatifEnPlus(){
        System.out.println("===testModifierFormationJustificatifEnPlus===");
        List<Justificatif> justificatifs = new ArrayList<Justificatif>();
        justificatifs.addAll(formation.getLesJustificatifs());
        justificatifs.add(justificatif);
        formation.setLesJustificatifs(justificatifs);
        try {
            new FormationService().modifierFormation(formationAvecJustificatif);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formationAvecJustificatif.equals(new FormationDAO().getById(formationAvecJustificatif.getId()))));
        assertTrue(done && formationAvecJustificatif.equals(new FormationDAO().getById(formationAvecJustificatif.getId())));
    }
    @Test
     public void testModifierFormationJustificatifEnMoins(){
        System.out.println("===testModifierFormationJustificatifEnMoins===");
        formationAvecJustificatif.setLesJustificatifs(new ArrayList<Justificatif>());
        try {
            new FormationService().modifierFormation(formationAvecJustificatif);
            done = true;
        } catch (ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException ex) {
            System.out.println("Exception relevé: " + ex.getMessage());
            done = false;
        }
        System.out.println("Test validé: " + (done == true && formationAvecJustificatif.equals(new FormationDAO().getById(formationAvecJustificatif.getId()))));
        assertTrue(done && formationAvecJustificatif.equals(new FormationDAO().getById(formationAvecJustificatif.getId())));
    }
}
