<%-- 
    Document   : modifierUtilisateur
    Created on : 17 nov. 2015, 16:14:29
    Author     : Pierre
--%>

<%@page import="modele.entite.Formation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<script src="jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<link rel="stylesheet" href="bootstrap/jquery-custom/jquery-ui-1.10.0.custom.css">
<link href="bootstrap/css/register.css" rel="stylesheet">
<script src="jQuery/register.js"></script>
<script type="text/javascript">
    $(function() {
        $('#dialog').hide();
    });

    function createDialog(id) {
        $('#dialog').dialog({
            modal: true,
            buttons: {
                "Oui": {
                    text : 'Oui' ,class : 'btn btn-success', click : function() {
                    window.location.replace('Navigation?action=supprimerUtilisateur&id=' + id);
                }
                },
                "Non": {text : 'Non' ,class : 'btn btn-danger', click : function() {
                    $(this).dialog("close");}
                }
            }
        });
        $('#dialog').show();
    };
    </script>

<form action="Navigation?action=modifierUtilisateur&id=<% out.print(request.getAttribute("id")); %>" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Login</label>
        <div class="col-sm-3">
            <input type="text" name="login" id="login" class="form-control" value="<% out.print(request.getAttribute("login")); %>" required>
        </div>
    </div>
    
    <div class="form-group">
        <label for="nom" class="col-sm-2 control-label">Nom</label>
        <div class="col-sm-3">
            <input type="text" name="nom" id="nom" class="form-control" value="<% out.print(request.getAttribute("nom")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="prenom" class="col-sm-2 control-label">Prenom</label>
        <div class="col-sm-3">
            <input type="text" name="prenom" id="prenom" class="form-control" value="<% out.print(request.getAttribute("prenom")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="email" class="col-sm-2 control-label">Email</label>
        <div class="col-sm-3">
            <input type="text" name="email" id="email" class="form-control" value="<% out.print(request.getAttribute("email")); %>" >
        </div>
    </div>
    
    <div class="form-group">
        <label for="motDePasse" class="col-sm-2 control-label">Mot de passe</label>
        <div class="col-sm-3">
            <input type="password" name="motDePasse" id="motDePasse" class="form-control" placeholder="" >
        </div>
    </div>
    <div id="type_account">
        <div class="form-group">
            <label for="mdp" class="col-md-2 control-label">Type</label>
            <div class="col-md-3">
                <select name="type" id="type" required class="form-control">
                    <%
                        TypeCompte[] lesTypes = TypeCompte.values();
                        TypeCompte leType = (TypeCompte) request.getAttribute("type");
                        for (TypeCompte t : lesTypes) {
                    %>
                    <option value="<%out.print(t.name()); %>" <%if(leType == t){ %>selected="selected"<%}%>><%out.print(t.toString());%></option>
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
            <!--[if IE]>
            <input type="hidden" name="action" value="enregistrerModifs" />
            <a class="btn btn-primary pull-right" href="Navigation?action=voirGestionComptes">Annuler</a>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-default pull-right" href="Navigation?action=voirGestionComptes">Annuler</a>
            <!--<![endif]-->
        </div>
        <div class="col-md-3">
            <!--[if IE]>
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer">Enregister</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer">Enregister</button>
            <!--[if IE]>
            <input type="hidden" name="action" value="supprUtilisateur" />
            <button class="btn btn-primary btn-danger pull-right" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-primary btn-danger pull-right" onclick='createDialog(<% out.print(request.getAttribute("id")); %>)'>Supprimer</a>
        </div>
    </div>
</form><br/>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-danger">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>            
            
<%@include file="Modele/pied.jsp" %>
<div id="dialog" title="Confirmer la suppression">
    <p>Voulez vous vraiment supprimer ce compte ?</p>
</div>