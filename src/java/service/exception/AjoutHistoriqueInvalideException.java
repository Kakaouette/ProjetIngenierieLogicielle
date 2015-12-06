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
public class AjoutHistoriqueInvalideException extends Exception{
    public enum cause{
        Historique_Null,
        Historique_Incomplet,
        Historique_Existant
    }

    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public AjoutHistoriqueInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public AjoutHistoriqueInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutHistoriqueInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutHistoriqueInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }    
}
