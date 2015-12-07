package page.action.Docx;

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
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Formation;
import modele.entite.Etudiant;
import modele.entite.Historique;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;



/**
 * 
 * A LIRE :
 * Ce que j'ai fait ici est le résultat de mon programme de test que j'ai pu faire.
 * Ce qu'il reste a faire:
 *  - Changer les champs a remplacer dans les lettres par des "$NomDeLaVariable"
 *  - Créer une fonction correspondant au "scénario" de remplacement d'un document
 *      (et non créer une fonction par variable comme je l'ai fait) pour ne lire qu'une seule fois
 *      le document et tout remplacer dès qu'il rencontre tel ou tel variable (s'inspirer d'une de mes
 *      fonctions en mettant plusieurs if correspondant a chacunes des variables)
 *  - Transformer tout cela en action avec comme appel des fonction les éléments de la BD
 * 
 */
public class DocReaderLettreRefus
{

        
        public static String replaceLettreRefus(String filename, String idDossier)throws InvalidFormatException, IOException
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
            
            System.out.println(filename);
            
            String newFileName=idDossier+" Lettre refus.docx";
            
            File file = new File("./lettres/models/"+filename);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument doc = new XWPFDocument(fis);
            doc.write(new FileOutputStream("./lettres/target/"+newFileName));
            doc.close();
            
            doc = new XWPFDocument(OPCPackage.open("./lettres/target/"+newFileName));

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
                    System.out.println("Changement de la date de commission effectue");
                }
            }
            doc.write(new FileOutputStream("./lettres/target/temp.docx"));
            new File("./lettres/target/temp.docx").delete();
            doc.close();
            //copyTempToFile(filename);
            System.out.println("replaceLettreRefus DONE");
            return newFileName;
        }
}
