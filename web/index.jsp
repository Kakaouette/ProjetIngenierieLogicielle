<%-- 
    Document   : index
    Created on : 4 nov. 2015, 18:35:11
    Author     : nicol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu.jsp" %>
<form class="form-signin" action="Navigation" method="POST">
    <h1 class="form-signin-heading">Identification</h1>
    <label for="identifiant" class="sr-only">Identifiant</label>
    <div class="input-group">
        <span class="input-group-addon" id="addId"><i class="fa fa-user"></i></span>
        <input type="text" name="identifiant" id="identifiant" class="form-control" placeholder="Identifiant" aria-describedby="addId" required autofocus>
    </div>
    <label for="mdp" class="sr-only">Mot de passe</label>
    <div class="input-group" id="divMargin">
        <span class="input-group-addon" id="addMdp"><i class="fa fa-lock"></i></span>
        <input type="password" name="mdp" id="mdp" class="form-control" placeholder="Mot de passe" aria-describedby="addMdp" required>
    </div>
    <!--[if IE]>
    <input type="hidden" name="action" value="gererAuthentification" />
    <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
    <![endif]-->
    <!--[if !IE]><!-->
    <button class="btn btn-lg btn-success btn-block" type="submit" name="action" value="gererAuthentification">Connexion</button>
    <!--<![endif]-->
</form>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-danger">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
<%@include file="Modele/pied.jsp" %>
