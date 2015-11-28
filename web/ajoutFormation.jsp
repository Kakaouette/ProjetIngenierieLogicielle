<%-- 
    Document   : ajouterFormation
    Created on : 24 nov. 2015, 10:35:29
    Author     : Arthur
--%>

<%@page import="modele.dao.JustificatifDAO"%>
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
            <input type="text" name="intitule" id="intitule" class="form-control" 
                   value="<%if(request.getAttribute("intitule") != null){out.print(request.getAttribute("intitule"));}%>" 
                   placeholder="Intitulé" autocomplete="off" required>
        </div>
    </div>
    
    <div class="form-group">
        <label for="description" class="col-sm-2 control-label">Description</label>
        <div class="col-sm-3">
            <textarea type="text" name="description" id="description" class="form-control" placeholder="Description" autocomplete="off"><%if(request.getAttribute("description") != null){out.print(request.getAttribute("description"));}%></textarea>
        </div>
    </div>
    
    <div class="form-group">
        <label for="nbPlace" class="col-sm-2 control-label">Nombre de place</label>
        <div class="col-sm-3">
            <input type="number" min="0" name="nbPlace" id="nbPlace" class="form-control" 
                   value="<%if(request.getAttribute("nbPlace") != null){
                       out.print(request.getAttribute("nbPlace"));
                   }else{
                        out.print("0");
                   }%>" required>
        </div>
    </div>
    
    <div class="form-group">
        <label for="dateDebut" class="col-sm-2 control-label">Date de début</label>
        <div class="col-sm-3">
            <div class="input-daterange input-group" id="datepicker">
                <% SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy"); %>
                <input type="text" id="start" class="form-control" name="dateDebut" readonly="readonly" 
                       value="<%/*if(df.parse(request.getParameter("dateFin")).before(df.parse(request.getAttribute("dateDebut").toString()))){ 
                               out.print(request.getAttribute("dateFin"));
                           }else*/ if(request.getAttribute("dateDebut") != null) {
                               out.print(request.getAttribute("dateDebut"));
                           }%>"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="dateFin" class="col-sm-2 control-label">Date de fin</label>
        <div class="col-sm-3">
            <div class="input-daterange input-group" id="datepicker">
                <input type="text" id="stop" class="form-control" name="dateFin" readonly="readonly"
                       value="<%/*if(df.parse(request.getParameter("dateDebut")).after(df.parse(request.getAttribute("dateFin").toString()))){ 
                               out.print(request.getAttribute("dateDebut"));
                           } else*/ if (request.getAttribute("dateFin") != null) {
                               out.print(request.getAttribute("dateFin"));
                           }%>"/>
            </div>
        </div>
    </div>
    
    <div class="form-group">
        <label for="justificatifs" class="col-sm-2 control-label">Justificatifs</label>
        <div class="row">
            <a class="btn btn-link" href="Navigation?action=voirAjoutJustificatifDansAttribut"><i class="fa fa-plus-circle"></i> Ajouter</a>
        </div>
        
        <div class="row col-sm-offset-1">
        <ul>
        <% List<Justificatif> justificatifs=(List<Justificatif>) request.getSession().getAttribute("justificatifs");
           for (Justificatif justificatif : justificatifs){
        %>
        <li>
            <label id="justificatifs" name="justificatifs" class="control-label"><%out.print(justificatif.getTitre());%></label>
            <a class="btn btn-link" href="Navigation?action=supprimerJustificatifDansAttribut&justificatifASuppr=<%out.print(justificatif.getTitre());%>"><i class="fa fa-remove"></i> Supprimer</a>
        </li>
        <% }%>
        </ul>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-1 col-md-offset-1">
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer"><i class="fa fa-save"></i> Enregister</button>
        </div>
        <div class="col-md-1 col-md-offset-1">
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer&nouveau"><i class="fa fa-save"></i> Enregister et nouveau <i class="fa fa-plus"></i></button>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <a class="btn btn-default" href="Navigation?action=voirGestionFormations">Annuler</a>
        </div>
    </div>
</form>

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
