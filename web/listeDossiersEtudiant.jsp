<%-- 
    Document   : listeDossiersEtudiant
    Created on : 15 déc. 2015, 13:10:57
    Author     : Drachenfels
--%>

<%@page import="modele.entite.Dossier"%>
<%@page import="modele.entite.Etudiant"%>
<%@page import="modele.entite.TypeEtatDossier"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu_etudiant.jsp" %>

<% Etudiant etu = (Etudiant) request.getAttribute("etu");%>
<h2>Bonjour <%out.print(etu.getNom());%> <%out.print(etu.getPrenom());%> voici l'état de vos dossiers :</h2><br/>
<table id="myTable" cellspacing="0" class="table table-striped table-bordered table-hover table-condensed dt-responsive text-center" width="100%">
    <thead>
        <tr>
            <th>Numéro</th>
            <th>Formation</th>
            <th>Etat</th>
        </tr>
    </thead>
    <tbody>
        <%List<Dossier> lesDossiers = (List<Dossier>) request.getAttribute("dossiers");
            for(Dossier d :lesDossiers){%>
            <tr>
                <td><%out.print(d.getId());%></td>
                <td><%out.print(d.getDemandeFormation().getIntitule());%></td>
                <td>
                    <%
                        switch(d.getEtat()){
                            case transfert_vers_secretariat:
                            case traité_secretariat_formation:
                                out.print("En cours de traitement administratif");
                                break;
                            case en_attente_commission:
                                out.print("En attente de décision du jury");
                                break;
                            case en_attente_transfert_vers_directeur:
                            case en_transfert_vers_directeur:
                            case retour_vers_secretariat:
                            case navette:
                                out.print("En traitement par le jury");
                                break;
                            case terminé:
                                out.print("Votre dossier a été traité vous devriez recevoir la réponse sous peu");
                                break;
                        }
                    %>
                </td>
            </tr>
            <%}
        %>
    </tbody>
</table>
<div class="row">
    <div class="col-md-1">
        <a class="btn btn-info" href="Etudiant">Deconnexion</a>
    </div>
</div>
<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
        
<%@include file="Modele/pied.jsp" %>
