/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.entite;

import javax.persistence.*;

/**
 *
 * @author nicol
 */
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Column(nullable = false)
    String texte;
    
    @Column(nullable = true)
    Menu menuSuperieur;
    
    @JoinColumn(nullable = false)
    @OneToOne
    Action action;
}
