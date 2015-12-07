/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.exception;

/**
 *
 * @author Arthur
 */
public class AjoutEtudiantInvalideException extends Exception{
    public enum cause{
        Etudiant_Null,
        Etudiant_Incomplet,
        Etudiant_Existant
    }

    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public AjoutEtudiantInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public AjoutEtudiantInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutEtudiantInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutEtudiantInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }    
}
