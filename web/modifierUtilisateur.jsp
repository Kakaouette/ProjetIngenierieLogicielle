<%-- 
    Document   : modifierUtilisateur
    Created on : 17 nov. 2015, 16:14:29
    Author     : Pierre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<form action="Navigation?action=modifierUtilisateur" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="type" class="col-sm-2 control-label">Type</label>
        <div class="col-sm-3">
            <!-- A faire : positionner le type actuel en selection par défaut -->
            <select name="type" id="type" class="form-control">
                <option>admin</option>
                <option>directeur_pole</option>
                <option>secretaire_general</option>
                <option>secretaire_formation</option>
                <option>commission</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Login</label>
        <div class="col-sm-3">
            <input type="text" name="login" id="login" class="form-control" value="<% out.print(request.getSession().getAttribute("login")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="nom" class="col-sm-2 control-label">Nom</label>
        <div class="col-sm-3">
            <input type="text" name="nom" id="nom" class="form-control" value="<% out.print(request.getSession().getAttribute("nom")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="prenom" class="col-sm-2 control-label">Prenom</label>
        <div class="col-sm-3">
            <input type="text" name="prenom" id="prenom" class="form-control" value="<% out.print(request.getSession().getAttribute("prenom")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="email" class="col-sm-2 control-label">Email</label>
        <div class="col-sm-3">
            <input type="text" name="email" id="email" class="form-control" value="<% out.print(request.getSession().getAttribute("email")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="motDePasse" class="col-sm-2 control-label">Mot de passe</label>
        <div class="col-sm-3">
            <input type="text" name="motDePasse" id="motDePasse" class="form-control" placeholder="" >
        </div>
    </div>
    <div class="row">
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="enregistrerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-success" type="submit" name="action" value="enregister">Enregister</button>
            <!--<![endif]-->
        </div>
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="annulerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-primary" type="submit" name="action" value="annuler">Annuler</button>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="supprUtilisateur" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-danger" type="submit" name="action" value="supprimer">Supprimer</button>
        </div>
    </div>
</form>

<%@include file="Modele/pied.jsp" %>
