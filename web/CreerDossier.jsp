<%-- 
    Document   : CreerDossier
    Created on : 19 nov. 2015, 11:35:03
    Author     : Sahare
--%>


<%@page import="modele.entite.Formation"%>
<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<!DOCTYPE html>

<form action="Navigation?action=ajoutDossierValide" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="formationIntitule" class="col-sm-2 control-label">Formation: </label>
        <div class="col-sm-3">
            <!-- A faire : positionner le type actuel en selection par défaut -->
            <select name="formationIntitule" id="formationIntitule" class="form-control">
                <option>Licence Génie Civil</option>
                <option>Licence Informatique</option>
                <option>Licence Mathématiques</option>
                <option>Licence Physique, chime</option>
                 <option>Master Informatique</option>
                <option>Licence Sciences de la Terre</option>
                <option>Licence Sciences de la Vie</option>
                <option>Master Biotechnologies</option>
                <option>Master Audiovisuel, médias interactifs numériques, jeux</option>
                <option>Master Biotechnologies</option>
                <option>Master Génie civil</option>
                <option>Master Métiers de l'enseignement, de l'éducation et de la formation 2nd degré</option>
                <option>Master Sciences et génie des matériaux</option>
                <option>Master Sciences pour l'environnement</option>
                
                
            </select>
        
        </div>
         </div>
    <div class="row">
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="Retour" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Compte</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-default" href="Navigation?action=voirGestionDossier">Retour</button>
            <!--<![endif]-->
        </div>
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="Retour" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Creation de dossier</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="Suivant">Suivant</button>
            <!--<![endif]-->
        </div>
    </div>
        
    </div>

<%  String message = (String) request.getAttribute("message");
    if(message != null){
        String[] msgSplited = message.split(":", 2);
        int idxMsg = 0; if(msgSplited.length == 2){idxMsg = 1;}
        String typeMsg = (String) request.getAttribute("typeMessage"); %>
    <br>
    <div class="alert <%if(typeMsg != null){
        if(typeMsg.equals("success") || typeMsg.equals("danger") || typeMsg.equals("info") || typeMsg.equals("warning")){
            out.print("alert-"+typeMsg);}}%>">
        <strong><%if(msgSplited.length == 2){out.print(msgSplited[0] + ":");}%></strong><%out.print(msgSplited[idxMsg]);%>
    </div>
<%}%>
<%@include file="Modele/pied.jsp" %>