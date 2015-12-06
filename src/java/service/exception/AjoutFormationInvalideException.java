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
public class AjoutFormationInvalideException extends Exception{  
    public enum cause{
        Formation_Null,
        Intitule_Vide,
        Formation_Existante,
        Date_Incohérentes
    }
    
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public AjoutFormationInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public AjoutFormationInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutFormationInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutFormationInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }
}
