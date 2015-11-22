<%-- 
    Document   : modifierUtilisateur
    Created on : 17 nov. 2015, 16:14:29
    Author     : Pierre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<form action="Navigation?action=modifierCompte" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="type" class="col-sm-2 control-label">Type</label>
        <div class="col-sm-3">
            <select name="type" id="type" class="form-control">
                <option <%if(request.getAttribute("type") == TypeCompte.admin){ %>selected="selected"<%}%>>admin</option>
                <option <%if(request.getAttribute("type") == TypeCompte.directeur_pole){ %>selected="selected"<%}%>>directeur_pole</option>
                <option <%if(request.getAttribute("type") == TypeCompte.secretaire_general){ %>selected="selected"<%}%>>secretaire_general</option>
                <option <%if(request.getAttribute("type") == TypeCompte.secretaire_formation){ %>selected="selected"<%}%>>secretaire_formation</option>
                <option <%if(request.getAttribute("type") == TypeCompte.commission){ %>selected="selected"<%}%>>commission</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Login</label>
        <div class="col-sm-3">
            <input type="text" name="login" id="login" class="form-control" placeholder="Login" value="<% out.print(request.getAttribute("login")); %>" required>
        </div>
    </div>
    
    <div class="form-group">
        <label for="nom" class="col-sm-2 control-label">Nom</label>
        <div class="col-sm-3">
            <input type="text" name="nom" id="nom" class="form-control" placeholder="Nom" value="<% out.print(request.getAttribute("nom")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="prenom" class="col-sm-2 control-label">Prenom</label>
        <div class="col-sm-3">
            <input type="text" name="prenom" id="prenom" class="form-control" placeholder="Prenom" value="<% out.print(request.getAttribute("prenom")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="email" class="col-sm-2 control-label">Email</label>
        <div class="col-sm-3">
            <input type="text" name="email" id="email" class="form-control" placeholder="Email" value="<% out.print(request.getAttribute("email")); %>" >
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
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer"><i class="fa fa-save"></i> Enregister</button>
            <!--<![endif]-->
        </div>
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="supprUtilisateur" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-danger" href="Navigation?action=voirGestionComptes"><i class="fa fa-remove"></i> Supprimer</a>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="annulerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-default" href="Navigation?action=voirGestionComptes">Annuler</a>
        </div>
    </div>
</form>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>            
            
<%@include file="Modele/pied.jsp" %>
