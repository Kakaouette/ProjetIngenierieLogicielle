<%-- 
    Document   : listeUtilisateurs
    Created on : 17 nov. 2015, 16:53:53
    Author     : Pierre
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript">
    $(function() {
        $('#dialog').hide();
    });

    function createDialog(id) {
        $('#dialog').dialog({
            modal: true,
            buttons: {
                "Oui": function() {
                    window.location.replace('Navigation?action=supprimerUtilisateur&id=' + id);
                },
                "Non": function() {
                    $(this).dialog("close");
                }
            }
        });
        $('#dialog').show();
    };
</script>
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
    <p>Voulez vous vraiment supprimer cette ligne ?</p>
</div>