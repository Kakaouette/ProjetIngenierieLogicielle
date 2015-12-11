package service.GenerationLettres;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.Justificatif;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import page.action.dossier.AfficherInformationsDossiersAction;
import static service.GenerationLettres.CreerAccuseReceptionService.PATH_MODELS;
import static service.GenerationLettres.CreerAccuseReceptionService.PATH_TARGET;



/**
 * 
 * @author Drapeau, Chasseloup, Giguère
 * @version 3
 */
public class CreerLettreAccepteService
{
    static private String getConfigurationPropertiesPathModels() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("serveur.properties"));
            return properties.getProperty("path.models");
        } catch (IOException ex) {
            Logger.getLogger(AfficherInformationsDossiersAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    static private String getConfigurationPropertiesPathTarget() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("serveur.properties"));
            return properties.getProperty("path.target");
        } catch (IOException ex) {
            Logger.getLogger(AfficherInformationsDossiersAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    static final String PATH_MODELS = getConfigurationPropertiesPathModels();
    static final String PATH_TARGET = getConfigurationPropertiesPathTarget();
    
        /**
         * 
         * @param filename - Nom du fichier modèle de la lettre d'acceptation.
         * @param idDossier - Identifiant du dossier pour lequel la lettre d'acceptation est créé
         * @throws InvalidFormatException
         * @throws IOException 
         */
        public void replaceLettreAccepte(String filename, String idDossier)throws InvalidFormatException, IOException
        {
           Dossier dossier = new DossierDAO().getById(idDossier);
            Etudiant etu = dossier.getEtudiant();
            Formation Dossierformation = dossier.getDemandeFormation();
            List<Historique> hist = dossier.getHistorique();
            
            
            Date dateActuelle=new Date();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy",Locale.FRANCE);
            String date = dateForm.format(dateActuelle);
            String nom=etu.getNom();
            String prenom=etu.getPrenom();
            String adresse=etu.getAdressePostale();
            String codePostal=etu.getAdresse().getCodePostal();
            String ville=etu.getAdresse().getVille();
            String civilite ="";
            if(etu.getSexe().equals("Masculin"))
                civilite="Monsieur";
            if(etu.getSexe().equals("Feminin"))
                civilite="Madame";
            String dateCommission="";
            for(Historique h : hist){
                if(h.getAction().equals("Réunion commité"))
                    dateCommission = dateForm.format(h.getDate());
            }
           
            String formation=Dossierformation.getIntitule();
            String debutInscriptions = dateForm.format(Dossierformation.getDebut());
            String finInscriptions = dateForm.format(Dossierformation.getFin());
            List<Justificatif> lesJust = Dossierformation.getLesJustificatifs();
            
            System.out.println(filename);
            
            String newFileName=idDossier+" Lettre accepte.docx";
            
            File file = new File(PATH_MODELS+"/"+filename);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument doc = new XWPFDocument(fis);
            doc.write(new FileOutputStream(PATH_TARGET+"/"+newFileName));
            doc.close();
            
            doc = new XWPFDocument(OPCPackage.open(PATH_TARGET+"/"+newFileName));

            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$formation"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$formation", formation);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la formation effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$date"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$date", date);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la date effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$civilite"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$civilite", civilite);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la civilite effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$prenom"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$prenom", prenom);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement du prenom effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$nom"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$nom", nom);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement du nom effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$adresse"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$adresse", adresse);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de l'adresse effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$codePostal"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$codePostal", codePostal);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement du code postal effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$ville"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$ville", ville);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la ville effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$Commission"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$Commission", dateCommission);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement du type de la formation effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$finInscriptions"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$finInscriptions", finInscriptions);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la fin des inscriptions effectue");
                }
            }
            for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("debutInscriptions"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$debutInscriptions", debutInscriptions);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la date de début d'inscription effectué");
                }
            }
            XWPFTable table = doc.createTable(lesJust.size(),2);
            table.setCellMargins(200, 250, 0, 250);
            int i = 0;
            for(XWPFTableRow r : table.getRows()){
                XWPFTableCell cell = r.getCell(0);
                cell.setText(lesJust.get(i).getTitre());
                cell = r.getCell(1);
                cell.setText(lesJust.get(i).getDescription());
                i++;
            }
            
            doc.write(new FileOutputStream(new File(newFileName)));
            doc.close();

            System.out.println("replaceAccuseAccepte DONE");
        }
}
