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
public class ModificationFormationInvalideException extends Exception{  
    public enum cause{
        Formation_Null,
        Formation_Inexistante,
        Intitule_Vide,
        Formation_Existante,
        Date_Incohérentes,
        Inscriptions_En_Cours
    }
    
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public ModificationFormationInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public ModificationFormationInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public ModificationFormationInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public ModificationFormationInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }
}
