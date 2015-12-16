<%-- 
    Document   : listeUtilisateurs
    Created on : 17 nov. 2015, 16:53:53
    Author     : Pierre
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<script src="jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<link rel="stylesheet" href="bootstrap/jquery-custom/jquery-ui-1.10.0.custom.css">
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
<div class="row">
    <div class="col-lg-2 col-lg-offset-5">
        <a href="Navigation?action=voirAjoutCompte" class="btn btn-block btn-success"><span class="fa fa-plus"></span> Ajouter</a>
    </div>
</div>
<%@include file="Modele/dataTablesScript.jsp" %>
<table id="myTable" cellspacing="0" class="table table-striped table-bordered table-hover table-condensed dt-responsive" width="100%">
    <thead>
        <tr>
            <th>Login</th>
            <th>Nom</th>
            <th>Prénom</th> 
            <th>Type</th>
            <th>Email</th>
            <th>Modifier</th>
            <th>Supprimer</th>
        </tr>
    </thead>
</table>

<%  String message = (String) request.getAttribute("message");
    if(message != null){ %>
    <div class="alert <% if(message.toLowerCase().contains("erreur")){%>alert-danger<%}else{%>alert-success<%}%>">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
        
<%@include file="Modele/pied.jsp" %>
<div id="dialog" title="Confirmer la suppression">
    <p>Voulez vous vraiment ce compte ?</p>
</div>