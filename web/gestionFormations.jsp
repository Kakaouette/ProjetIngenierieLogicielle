<%-- 
    Document   : gestionFormation
    Created on : 26 nov. 2015, 14:28:01
    Author     : Arthur
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
                    window.location.replace('Navigation?action=supprimerFormation&id=' + id);
                },
                "Non": function() {
                    $(this).dialog("close");
                }
            }
        });
        $('#dialog').show();
    };
</script>

<%if(request.getAttribute("focus") != null){%>
<script type="text/javascript">
    window.onload=function(){
        document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
    };
</script>
<%}%>

<%@include file="Modele/dataTablesFormationScript.jsp" %>
<div class="col-sm-3">
    <a class="btn btn-success" href="Navigation?action=voirAjoutFormation"><i class="fa fa-plus"></i> Ajouter</a>
</div><br>
<table id="myTable" cellspacing="0" class="table table-striped table-bordered table-hover table-condensed dt-responsive" width="100%">
    <thead>
        <tr>
            <th>Intitulé</th>
            <th>Description</th>
            <th>Nombre de place</th>
            <th>Date de début</th>
            <th>Date de fin</th>
            <th>Modifier</th>
            <th>Supprimer</th>
        </tr>
    </thead>
</table>

<%  String message = (String) request.getAttribute("message");
    if(message != null){
        String[] msgSplited = message.split(":", 2);
        int idxMsg = 0; if(msgSplited.length == 2){idxMsg = 1;}
        String typeMsg = (String) request.getAttribute("typeMessage"); %>
    <br>
    <div class="alert <%if(typeMsg != null){
        if(typeMsg.equals("success") || typeMsg.equals("danger") || typeMsg.equals("info") || typeMsg.equals("warning")){
            out.print("alert-"+typeMsg);}}%>">
        <strong><%if(msgSplited.length == 2){out.print(msgSplited[0] + ":");}%></strong><%out.print(msgSplited[idxMsg]);%>
    </div>
<%}%>

<%@include file="Modele/pied.jsp" %>
<div id="dialog" title="Confirmer la suppression">
    <p>Voulez vous vraiment supprimer cette ligne ?</p>
</div>