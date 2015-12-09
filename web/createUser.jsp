<%-- 
    Document   : createUser
    Created on : 17 nov. 2015, 23:00:11
    Author     : totodunet
--%>

<%@page import="modele.dao.FormationDAO"%>
<%@page import="modele.entite.Formation"%>
<%@page import="modele.entite.Formation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<form class="form-register" action="Navigation" method="POST">
    
    <div id="info_user">
       
    <p><label for="identifiant">Identifiant</label>
        <input type="text" name="login" id="identifiant" required autofocus></p>
        
    <p><label for="name">Nom</label>
        <input type="text" name="nom" id="name" required></p>
    
    <p><label for="firstname">Prénom</label>
        <input type="text" name="prenom" id="firstname" required></p>
    
    <p><label for="mail">E-Mail</label>
        <input type="mail" name="email" id="firstname" required></p>
    
    <p><label for="mdp">Mot de passe</label>
        <input type="password" name="mdp" id="mdp" required></p>
    </div>
    
    <div id="type_account">
    <p><label for="type">Type</label>
    <select name="type" id="type" required>
        <option value="<%out.print(TypeCompte.directeur_pole.name()); %>"><%out.print(TypeCompte.directeur_pole.toString());%></option>
        <option value="<%out.print(TypeCompte.admin.name()); %>"><%out.print(TypeCompte.admin.toString());%></option>
        <option value="<%out.print(TypeCompte.responsable_administrative.name()); %>"><%out.print(TypeCompte.responsable_administrative.toString());%></option>
        <option value="<%out.print(TypeCompte.responsable_commission.name()); %>"><%out.print(TypeCompte.responsable_commission.toString());%></option>
        <option value="<%out.print(TypeCompte.responsable_formation.name()); %>"><%out.print(TypeCompte.responsable_formation.toString());%></option>
        <option value="<%out.print(TypeCompte.secrétaire_formation.name()); %>"><%out.print(TypeCompte.secrétaire_formation.toString());%></option>
        <option value="<%out.print(TypeCompte.secrétaire_inscription.name()); %>"><%out.print(TypeCompte.secrétaire_inscription.toString());%></option>
    </select></p>
        
    <div id="formation">
        <p><label for="recherche">Rechercher</label>
            <input type="text" id="recherche"/></p>
            <p><label for="display">Afficher par</label>
        <input type="number" min="0" id="display" value="4"/>
        </p>
        <p><label for="formations">Formation(s)</label>
    <select name="formations" size="4" multiple>
        <option id="nope_formation" selected="selected">Aucune formation</option>
        <%
            List<Formation> formations=new FormationDAO().SelectAll();
            for(Formation f:formations){
                %><option value="<%out.print(f.getId());%>"><%out.print(f.getIntitule());%></option><%
            }
        %>
    </select></p>
    <p><strong>Important</strong> : Pour sélectionner plusieurs formations, maintenir appuyé la touche <b>Ctrl</b> et cliquer sur les formations.</p></div>
    </div>
    <!--[if IE]>
    <input type="hidden" name="action" value="creerUtilisateur" />
    <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Créer l'utilisateur</button>
    <input type="hidden" name="action" value="" />
    <button class="btn btn-lg btn-danger btn-block" type="reset" name="action">Annuler</button>
    <![endif]-->
    <!--[if !IE]><!-->
    <button class="btn btn-lg btn-success btn-block" type="submit" name="action" value="creerUtilisateur">Créer l'utilisateur</button>
    <button class="btn btn-lg btn-danger btn-block" type="reset" name="action">Annuler</button>
    <!--<![endif]-->
</form>

<% if(request.getAttribute("error") == "true"){ %>
    <div class="alert alert-danger">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}else if(request.getAttribute("error") == "false"){%>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
<%@include file="Modele/pied.jsp" %>