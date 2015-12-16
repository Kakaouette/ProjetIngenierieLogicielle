<%-- 
    Document   : listeUtilisateurs
    Created on : 17 nov. 2015, 16:53:53
    Author     : Pierre
--%>

<%@page import="modele.entite.Dossier"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<% Boolean DossierUrgent = (Boolean) request.getAttribute("DossierUrgent");
    Boolean DossierPerdu = (Boolean) request.getAttribute("DossierPerdu");
    if(DossierUrgent|| DossierPerdu){ %>
    <div class="alert alert-danger">
        <% if(DossierUrgent){ %>
        Vous avez des dossiers en cours qui dépasseront la limite de temps dans moins de 15 jours !
        <%}
        if(DossierUrgent && DossierPerdu){
        out.print("<br/>");
        }
        if(DossierUrgent){ %>
        Vous avez des dossiers perdu ou en retard !
        <% } 
        if(request.getAttribute("msgSuppression") != null){
        if((boolean) request.getAttribute("msgSuppression")==true){
            out.print(request.getAttribute("message"));
        }}%>
    </div>
<%}%>
<div class="row">
    <div class="col-lg-2 col-lg-offset-5">
        <a href="Navigation?action=voirAjoutDossier" class="btn btn-block btn-success"><span class="fa fa-plus"></span> Ajouter</a>
    </div>
</div>
<%@include file="Modele/dataTablesScript.jsp" %>
<table id="myTable" cellspacing="0" class="table table-striped table-bordered table-hover table-condensed dt-responsive text-center" width="100%">
    <thead>
        <tr>
            <th>Etat</th>
            <th>Numéro</th>
            <th data-class-name="none">Type</th>
            <th data-class-name="none">Créé le</th>
            <th data-class-name="none">Jours restant</th>
            <th>Formation demandé</th>
            <th data-class-name="none">INE</th>
            <th>Nom de l'etudiant</th>
            <th>Prenom de l'etudiant</th>
            <th>Modifier</th>
        </tr>
    </thead>
</table>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
        
<%@include file="Modele/pied.jsp" %>
