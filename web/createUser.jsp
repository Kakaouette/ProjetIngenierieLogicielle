<%-- 
    Document   : createUser
    Created on : 17 nov. 2015, 23:00:11
    Author     : totodunet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<form class="form-signin" action="Navigation" method="POST">
    <label for="identifiant" class="sr-only">Identifiant</label>
    <div class="input-group">
        <span class="input-group-addon" id="addId"><i class="fa fa-user"></i></span>
        <input type="text" name="login" id="identifiant" class="form-control" placeholder="Identifiant" aria-describedby="addId" required autofocus>
    </div>
    <label for="type" class="sr-only">Type</label>
    <div class="input-group">
        <span class="input-group-addon" id="addId"><i class="fa fa-users"></i></span>
        <select name="type" id="type" class="form-control" aria-describedby="addType" required>
            <option value="<%out.print(TypeCompte.directeur_pole.name()); %>"><%out.print(TypeCompte.directeur_pole.toString());%></option>
            <option value="<%out.print(TypeCompte.admin.name()); %>"><%out.print(TypeCompte.admin.toString());%></option>
        </select>
    </div>
    <label for="name" class="sr-only">Nom</label>
    <div class="input-group">
        <span class="input-group-addon" id="addName"><i class="fa fa-user"></i></span>
        <input type="text" name="nom" id="name" class="form-control" placeholder="Nom" aria-describedby="addName" required>
    </div>
    <label for="firstname" class="sr-only">Prénom</label>
    <div class="input-group">
        <span class="input-group-addon" id="addFirstname"><i class="fa fa-user"></i></span>
        <input type="text" name="prenom" id="firstname" class="form-control" placeholder="Prénom" aria-describedby="addFirstname" required>
    </div>
    <label for="mail" class="sr-only">E-Mail</label>
    <div class="input-group">
        <span class="input-group-addon" id="addMail"><i class="fa fa-envelope"></i></span>
        <input type="mail" name="email" id="firstname" class="form-control" placeholder="Mail" aria-describedby="addFirstname" required>
    </div>
    <label for="mdp" class="sr-only">Mot de passe</label>
    <div class="input-group" id="divMargin">
        <span class="input-group-addon" id="addMdp"><i class="fa fa-lock"></i></span>
        <input type="password" name="mdp" id="mdp" class="form-control" placeholder="Mot de passe" aria-describedby="addMdp" required>
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