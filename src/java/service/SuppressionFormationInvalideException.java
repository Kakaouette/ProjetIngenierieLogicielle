/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author Arthur
 */
public class SuppressionFormationInvalideException extends Exception{  
    public enum cause{
        Formation_Inexistante,
        Inscriptions_En_Cours
    }
    
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public SuppressionFormationInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public SuppressionFormationInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public SuppressionFormationInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public SuppressionFormationInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }
}
