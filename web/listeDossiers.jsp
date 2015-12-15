<%-- 
    Document   : listeUtilisateurs
    Created on : 17 nov. 2015, 16:53:53
    Author     : Pierre
--%>

<%@page import="modele.entite.Dossier"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<% if((boolean) request.getAttribute("DossierUrgent") == true || (boolean) request.getAttribute("DossierPerdu") == true){ %>
    <div class="alert alert-danger">
        <% if((boolean) request.getAttribute("DossierUrgent") == true){ %>
        Vous avez des dossiers en cours qui dépasseront la limite de temps dans moins de 15 jours !
        <%}
        if((boolean) request.getAttribute("DossierUrgent") == true || (boolean) request.getAttribute("DossierPerdu") == true){
        out.print("<br/>");
        }
        if((boolean) request.getAttribute("DossierUrgent") == true){ %>
        Vous avez des dossiers perdu ou en retard!
        <% } if((boolean) request.getAttribute("msgSuppression")==true){
            out.print(request.getAttribute("message"));
        }%>
    </div>
<%}%>

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
