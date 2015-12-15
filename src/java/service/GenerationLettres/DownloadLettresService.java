/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.GenerationLettres;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import page.action.dossier.AfficherInformationsDossiersAction;

/**
 *
 * @author Pierre
 */
public class DownloadLettresService extends HttpServlet
{  
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
    static final String PATH_TARGET = getConfigurationPropertiesPathTarget();
    
    public void downloadFile(String idDossierSource, int typeLettreSource, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException
    {
        String idDossier = idDossierSource;
        String typeLettre;
        
        switch(typeLettreSource)
        {
            case 1:
                typeLettre=" Accuse De Reception.docx";
                break;
            case 2:
                typeLettre=" Lettre accepte.docx";
                break;
            case 3:
                typeLettre=" Lettre refus.docx";
                break;
            case 4:
                typeLettre=" Lettre audition.docx";
                break;
            case 5:
                typeLettre=" Lettre piecesManquantes.docx";
                break;
            default:
                typeLettre="Erreur";
                break;
        }
        
        if(!typeLettre.equals("Erreur"))
        {
            // reads input file from an absolute path
            String filename=idDossier+""+typeLettre;
            String filePath = PATH_TARGET+"/"+filename;
            
            response.setContentType("application/octet-stream");
            String disHeader = "Attachment; Filename=\""+filename+"\"";
            response.setHeader("Content-Disposition", disHeader);
            File fileToDownload = new File(filePath);

            InputStream in = null;
            ServletOutputStream outs = response.getOutputStream();

            try {
                in = new BufferedInputStream(new FileInputStream(fileToDownload));
                int ch;
                while ((ch = in.read()) != -1) {
                    outs.print((char) ch);
                }
            } finally {
                if (in != null) {
                    in.close(); // very important
                }
            }

            outs.flush();
            outs.close();
            in.close();
        }
    }
}
