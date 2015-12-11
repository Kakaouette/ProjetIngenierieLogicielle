<%-- 
    Document   : createUser
    Created on : 17 nov. 2015, 23:00:11
    Author     : totodunet
--%>

<%@page import="modele.dao.FormationDAO"%>
<%@page import="modele.entite.Formation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<link href="bootstrap/css/register.css" rel="stylesheet">
<script src="jQuery/register.js"></script>
<form action="Navigation" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="identifiant" class="col-md-2 control-label">Identifiant</label>
        <div class="col-md-3">
            <input type="text" name="login" class="form-control" id="identifiant" required>
        </div>
    </div>
    <div class="form-group">
        <label for="name" class="col-md-2 control-label">Nom</label>
        <div class="col-md-3">
            <input type="text" name="nom" id="name" class="form-control" required>
        </div>
    </div>
    <div class="form-group">
        <label for="prenom" class="col-md-2 control-label">Prénom</label>
        <div class="col-md-3">
            <input type="text" name="prenom" id="prenom" class="form-control" required>
        </div>
    </div>
    <div class="form-group">
        <label for="email" class="col-md-2 control-label">E-Mail</label>
        <div class="col-md-3">
            <input type="text" name="email" id="mail" class="form-control" required>
        </div>
    </div>
    <div class="form-group">
        <label for="mdp" class="col-md-2 control-label">Mot de passe</label>
        <div class="col-md-3">
            <input type="password" name="mdp" id="mdp" class="form-control" required>
        </div>
    </div>
    <div id="type_account">
        <div class="form-group">
            <label for="mdp" class="col-md-2 control-label">Type</label>
            <div class="col-md-3">
                <select name="type" id="type" required class="form-control">
                    <%
                        TypeCompte[] lesTypes = TypeCompte.values();
                        for (TypeCompte t : lesTypes) {
                    %>
                    <option value="<%out.print(t.name()); %>"><%out.print(t.toString());%></option>
                    <% } %>
                </select>
            </div>
        </div>
        <div id="formation">
            <div class="form-group">
                <label for="recherche" class="col-md-2 control-label">Rechercher</label>
                <div class="col-md-3">
                    <input type="number" min="0" id="display" value="4" name="recherche" id="recherche" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="formations" class="col-md-2 control-label">Formation(s)</label>
                <div class="col-md-3">
                    <select name="formations" size="4" multiple>
                        <option id="nope_formation" selected="selected">Aucune formation</option>
                        <%
                            List<Formation> formations = (List<Formation>) request.getAttribute("lesFormations");
                            for (Formation f : formations) {
                        %><option value="<%out.print(f.getId());%>"><%out.print(f.getIntitule());%></option><%
                            }
                        %>
                    </select>
                </div>
            </div>
            <div class="col-md-offset-2">
                <p><strong>Important</strong> : Pour sélectionner plusieurs formations, maintenir appuyé la touche <b>Ctrl</b> et cliquer sur les formations.</p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <button class="btn btn-default pull-right" type="reset" name="action">Annuler</button>
        </div>
        <div class="col-md-3">
            <button class="btn btn-success" type="submit" name="action" value="creerUtilisateur">Créer</button>
        </div>
    </div>
</form><br/>

<% if (request.getAttribute("error") == "true") { %>
<div class="alert alert-danger">
    <%out.print(request.getAttribute("message"));%>
</div>
<%} else if (request.getAttribute("error") == "false") {%>
<div class="alert alert-success">
    <%out.print(request.getAttribute("message"));%>
</div>
<%}%>
<%@include file="Modele/pied.jsp" %>