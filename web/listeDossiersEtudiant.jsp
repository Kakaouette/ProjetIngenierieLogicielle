<%-- 
    Document   : listeDossiersEtudiant
    Created on : 15 déc. 2015, 13:10:57
    Author     : Drachenfels
--%>

<%@page import="modele.entite.Dossier"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu_etudiant.jsp" %>

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
                <td><%out.print(d.getEtat());%></td>
            </tr>
            <%}
        %>
    </tbody>
</table>
<div class="row">
    <div class="col-md-2">
        <!--[if IE]>
        <input type="hidden" name="action" value="connexionEtudiant" />
        <a class="btn btn-primary pull-right" href="Etudiant">Deconnexion</a>
        <![endif]-->
        <!--[if !IE]><!-->
        <a class="btn btn-info pull-right" href="Etudiant">Deconnexion</a>
        <!--<![endif]-->
    </div>
</div>
<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
        
<%@include file="Modele/pied.jsp" %>
