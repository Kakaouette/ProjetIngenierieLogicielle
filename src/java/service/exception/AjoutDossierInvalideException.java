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
public class AjoutDossierInvalideException extends Exception{  
    public enum cause{
        ID_Invalide,
        Dossier_Incomplet,
        Formation_Inexistante,
        Dossier_Existant
    }

    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public AjoutDossierInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public AjoutDossierInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutDossierInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutDossierInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }
}
