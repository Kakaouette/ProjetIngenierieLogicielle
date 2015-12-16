/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exception.AjoutDossierInvalideException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modele.dao.AdresseDAO;
import modele.dao.CompteDAO;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
import modele.dao.FormationDAO;
import modele.dao.HistoriqueDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeCompte;
import modele.entite.TypeDossier;
import modele.entite.TypeEtatDossier;
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
public class DossierService_ajouterDossierTest {
    DossierService instance = new DossierService();
    Adresse adresse;
    Etudiant etudiant;
    Etudiant etudiant2;
    Formation formation;
    Compte c;
    Historique historique;
    Dossier dossier;
        
    public DossierService_ajouterDossierTest() {
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
        
        adresse = new Adresse("1", "v");
        etudiant = new Etudiant("1", "n", "p", "p", "a", "M", adresse);
        etudiant2 = new Etudiant("2", "nn", "pp", "p", "a", "M", adresse);
        formation = new Formation("d", 0, null, null, "i", null);
        c = new Compte("l", "n", "p", "m", TypeCompte.admin, null);
        historique = new Historique(new Date(), "Message", "Action", c);
        dossier = new Dossier();
    }
    
    @After
    public void tearDown() {
        instance = null;
        
        //free BDD
        try {
            new AdresseDAO().delete(adresse.getId());
        } catch (Exception e) {}
        try {
            new EtudiantDAO().delete(etudiant.getId());
        } catch (Exception e) {}
        try {
            new EtudiantDAO().delete(etudiant2.getId());
        } catch (Exception e) {}
        try {
            new FormationDAO().delete(formation.getId());
        } catch (Exception e) {}
        try {
            new CompteDAO().delete(c.getId());
        } catch (Exception e) {}
        try {
            new HistoriqueDAO().delete(historique.getId());
        } catch (Exception e) {}
        try {
            new DossierDAO().delete(dossier.getId());
        } catch (Exception e) {}
    }

    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierIdNull() throws Exception {
        System.out.println("testAjouterDossierIdNull");
        
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(null, new Date(), TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierIdVide() throws Exception {
        System.out.println("testAjouterDossierIdVide");
        
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier("", new Date(), TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierIdIncorrect() throws Exception {
        System.out.println("testAjouterDossierIdIncorrect");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier("azerty", new Date(), TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierDateNull() throws Exception {
        System.out.println("testAjouterDossierDateNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }

    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtatNull() throws Exception {
        System.out.println("testAjouterDossierEtatNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), null, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
        
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierTypeNull() throws Exception {
        System.out.println("testAjouterDossierTypeNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.traité_secretariat_formation, "UneLettre", null, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, null, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantINENull() throws Exception {
        System.out.println("testAjouterDossierEtudiantINENull");
        etudiant = new Etudiant(null, "n", "p", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantINEVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantINEVide");
        etudiant = new Etudiant("", "n", "p", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantNomNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantNomNull");
        etudiant = new Etudiant("1", null, "p", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantNomVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantNomVide");
        etudiant = new Etudiant("1", "", "p", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantPrenomNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantPrenomNull");
        etudiant = new Etudiant("1", "n", null, "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantPrenomVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantPrenomVide");
        etudiant = new Etudiant("1", "n", "", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantAdresseNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantAdresseNull");
        etudiant = new Etudiant("1", "n", "p", "p", null, "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantAdresseVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantAdresseVide");
        etudiant = new Etudiant("1", "n", "p", "p", "", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantSexeNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantSexeNull");
        etudiant = new Etudiant("1", "n", "p", "p", "a", null, adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantSexeVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantSexeVide");
        etudiant = new Etudiant("1", "n", "p", "p", "a", "", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierFormationNull() throws Exception {
        System.out.println("testAjouterDossierFormationNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, null, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierFormationIntituleNull() throws Exception {
        System.out.println("testAjouterDossierFormationIntituleNull");
        formation = new Formation("d", 0, null, null, null, null);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierFormationIntituleVide() throws Exception {
        System.out.println("testAjouterDossierFormationIntituleVide");
        formation = new Formation("d", 0, null, null, "", null);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierValide() throws Exception {
        System.out.println("testAjouterDossierValide");
        new FormationDAO().save(formation);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        new CompteDAO().save(c);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.traité_secretariat_formation, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
        }catch(AjoutDossierInvalideException e){
            fail(e.getMessage());
        }
        
        if(!new DossierDAO().getById(dossier.getId()).equals(dossier)){
             fail("Dossier inégal.");
        }
    }
}
