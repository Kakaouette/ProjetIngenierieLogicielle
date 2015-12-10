package service.GenerationLettres;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;



/**
 * 
 * @author Drapeau, Chasseloup, Giguère
 * @version 3
 */
public class CreerLettreAuditionService
{
    /**
     * 
     * @param filename - Nom du fichier modèle de la lettre d'audition.
     * @param idDossier - Identifiant du dossier pour lequel la lettre d'audition est créée
     * @throws InvalidFormatException
     * @throws IOException 
     */
    public void replaceLettreAudition(String filename, String idDossier)throws InvalidFormatException, IOException
    {
        Dossier dossier = new DossierDAO().getById(idDossier);

        Formation formation = dossier.getDemandeFormation();
        Date dateActuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy",Locale.FRANCE);
        String date = dateFormat.format(dateActuelle);
        //System.out.println(date);

        String annee;
        if(formation.getIntitule().contains("1"))
        {
            annee="1ère";
        }
        else if(formation.getIntitule().contains("2"))
        {
            annee="2ème";
        }
        else if(formation.getIntitule().contains("3"))
        {
            annee="3ème";
        }
        else
        {
            annee="1ère";
            System.out.println("Erreur pour l'année, fonction replaceAccuseReception");
        }

        String type;
        if(formation.getDescription().contains("Licence"))
        {
            type="licence";
        }
        else if(formation.getDescription().contains("Master"))
        {
            type="master";
        }
        else
        {
            type="master";
            System.out.println("Erreur pour le type, fonction replaceAccuseReception");
        }

        String mention=formation.getDescription();

        String parcours=formation.getIntitule();

        String typeFormation="formation initiale";

        Etudiant etudiant = dossier.getEtudiant();
        String nom=etudiant.getNom();
        String prenom=etudiant.getPrenom();
        String adresse=etudiant.getAdressePostale();
        String codePostal = etudiant.getAdresse().getCodePostal();
        String ville = etudiant.getAdresse().getVille();
        String civilite;
        if(etudiant.getSexe().contains("Masculin"))
        {
            civilite="Monsieur ";
        }
        else if(etudiant.getSexe().contains("Feminin"))
        {
            civilite="Madame ";
        }
        else
        {
            civilite="Monsieur ";
            System.out.println("Erreur civilite, DocReaderLettreAudition");
        }

        String dateCommission="25/06/2015";

        /*String date="6 decembre 2015";String nom="Gaunt";String prenom="Damian";String adresse="9 rue leconte";String codePostal="84450";
        String ville="BOUHET";String civilite="Monsieur";String dateCommission="25/06/2015";String annee="1ère";
        String type="master";String mention="Informatique";String parcours="ICONE";String typeFormation="formation initiale";*/

        System.out.println(filename);

        String newFileName=idDossier+" Lettre audition.docx";

        XWPFDocument doc = new XWPFDocument(OPCPackage.open("./lettres/models/"+filename));
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

            if(sb.length() > 0 && sb.toString().contains("$annee"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$annee", annee);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de l'annee effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$type"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$type", type);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du type effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$mention"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$mention", mention);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de la mention effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$parcours"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$parcours", parcours);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du parcours effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$formation"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$formation", typeFormation);
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
        doc.write(new FileOutputStream("./lettres/target/temp.docx"));
        new File("./lettres/target/temp.docx").delete();
        doc.close();
        //copyTempToFile(filename);
        System.out.println("replaceLettreAudition DONE");
    }
}
