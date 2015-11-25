<%-- 
    Document   : ajouterFormation
    Created on : 24 nov. 2015, 10:35:29
    Author     : Arthur
--%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<script src="jQuery/bootstrap-datepicker.js"></script>
<link href="jQuery/bootstrap-datepicker3.css" rel="stylesheet">

<script>
    $(function() {
        $('.input-daterange').datepicker({
            format: "dd/mm/yyyy",
            language: "fr",
            todayBtn: true,
            autoclose: true
        });
    });
</script>
<form action="Navigation?action=ajouterFormation" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="intitule" class="col-sm-2 control-label">Intitulé</label>
        <div class="col-sm-3">
            <input type="text" name="intitule" id="intitule" class="form-control" value="" placeholder="Intitulé" autocomplete="off" required>
        </div>
    </div>
    
    <div class="form-group">
        <label for="description" class="col-sm-2 control-label">Description</label>
        <div class="col-sm-3">
            <input type="text" name="description" id="description" class="form-control" value="" placeholder="Description" autocomplete="off">
        </div>
    </div>
    
    <div class="form-group">
        <label for="nbPlace" class="col-sm-2 control-label">Nombre de place</label>
        <div class="col-sm-3">
            <input type="number" min="0" name="nbPlace" id="nbPlace" class="form-control" value="0" required>
        </div>
    </div>
    
    <div class="form-group">
        <label for="dateDebut" class="col-sm-2 control-label">Date de début</label>
        <div class="col-sm-3">
            <div class="input-daterange input-group" id="datepicker">
                <% SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); %>
                <input type="text" id="start" class="form-control" name="dateDebut" required readonly="readonly" 
                       value="<%if (request.getAttribute("dateDebut") == null) {
                               out.print(df.format(new Date()));
                           }else if(df.parse(request.getParameter("dateFin")).before(df.parse(request.getAttribute("dateDebut").toString()))){ 
                               out.print(request.getAttribute("dateFin"));
                           }else {
                               out.print(request.getAttribute("dateDebut"));
                           }%>"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="dateFin" class="col-sm-2 control-label">Date de fin</label>
        <div class="col-sm-3">
            <div class="input-daterange input-group" id="datepicker">
                <input type="text" id="stop" class="form-control" name="dateFin" required readonly="readonly"
                       value="<%
                           if (request.getAttribute("dateFin") == null) {
                               out.print(df.format(new Date()));
                           } else {
                               out.print(request.getAttribute("dateFin"));
                           }%>"/>
            </div>
        </div>
    </div>
    
    <div class="form-group">
        <label for="justificatifs" class="col-sm-2 control-label">Justificatifs</label>
        <div class="row">
            <a class="btn btn-success" href="Navigation?action=voirAjoutJustificatifDansAttribut">Ajouter</a>
        </div>
        <div class="row">
        <ul>
        <% List<Justificatif> justificatifs=(List<Justificatif>) request.getSession().getAttribute("justificatifs");
           for (Justificatif justificatif : justificatifs){
        %>
        <li>
            <label for="justificatifs" class="control-label"><%out.print(justificatif.getTitre());%></label>
            <a class="btn btn-danger" href="Navigation?action=supprimerJustificatifDansAttribut&justificatifASuppr=<%out.print(justificatif.getTitre());%>">Supprimer</a>
        </li>
        <% }%>
        </ul>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-2 col-md-offset-2">
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer">Enregister</button>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <a class="btn btn-danger" href="Navigation?action=voirGestionFormation">Supprimer</a>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <a class="btn btn-default" href="Navigation?action=voirGestionFormation">Annuler</a>
        </div>
    </div>
</form>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>            
            
<%@include file="Modele/pied.jsp" %>
