<%-- 
    Document   : gestionFormation
    Created on : 26 nov. 2015, 14:28:01
    Author     : Arthur
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<script src="jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<link rel="stylesheet" href="bootstrap/jquery-custom/jquery-ui-1.10.0.custom.css">
<script type="text/javascript">
    function createDialog(id) {
        //creation et ajout du dialog
        $('body').append(
            '<div id="dialogValiderSuppr" title="Confirmer la suppression">' + 
                '<p>Voulez vous vraiment supprimer cet élément?</p>' + 
            '</div>');
    
    
        $('div#dialogValiderSuppr').dialog({
            modal: true,
            close:function( event, ui ){
                    $("div#dialogValiderSuppr").remove();
                },
            buttons: {
                "Oui":{
                    text : 'Oui' ,class : 'btn btn-success', click : function() {
                        window.location.replace('Navigation?action=supprimerFormation&id=' + id);
                    }
                },
                "Non":{
                    text : 'Non' ,class : 'btn btn-danger', click : function() {
                        $(this).dialog("close");
                    }
                }
            }
        });
    };
</script>

<%if(request.getAttribute("focus") != null){%>
<script type="text/javascript">
    window.onload=function(){
        document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
    };
</script>
<%}%>
<div class="row">
    <div class="col-lg-2 col-lg-offset-5">
        <a href="Navigation?action=voirAjoutFormation" class="btn btn-block btn-success"><span class="fa fa-plus"></span> Ajouter</a>
    </div>
</div>
<%@include file="Modele/dataTablesFormationScript.jsp" %>
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
        <strong><%if(msgSplited.length == 2){out.print(msgSplited[0] + ":");}%></strong><em><%out.print(msgSplited[idxMsg]);%></em>
    </div>
<%}%>

<%@include file="Modele/pied.jsp" %>