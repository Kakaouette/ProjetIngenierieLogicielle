<%-- 
    Document   : gestionDatesInscription
    Created on : 1 déc. 2015, 18:06:36
    Author     : Arthur
--%>

<%@page import="modele.entite.Formation"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<script src="jQuery/bootstrap-datepicker.js"></script>
<link href="jQuery/bootstrap-datepicker3.css" rel="stylesheet">
<script type="text/javascript">
    $(function() {
        $('.input-daterange').datepicker({
            format: "dd/mm/yyyy",
            todayBtn: true,
            language: "fr",
            autoclose: true,
            todayHighlight: true
        });
    });
    
    <%if(request.getAttribute("focus") != null){%>
        window.onload=function(){
            document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
        };
    <%}%>
    
    function reloadPage(){
       $("form#dates").prop("action", "Navigation?action=voirGestionDatesInscription");
       $("form#dates").submit();
    };
</script>

<form id="dates" action="Navigation?action=modiferDatesInscription" method="POST" class="form-horizontal"> 
    <div class="form-group">
        <label for="intitule" class="col-sm-2 control-label">Formation: </label>
        <div class="col-sm-3">
            <select name="intitule" id="intitule" class="form-control" onchange="reloadPage()">
                <% List<Formation> formations=(List<Formation>) request.getAttribute("formations");
                for (Formation formation : formations){
                %>
                <option value="<%out.print(formation.getIntitule());%>"<%if(request.getAttribute("intitule") != null){
                                if(request.getAttribute("intitule").equals(formation.getIntitule())){%>selected<%}
                            }%>><%out.print(formation.getIntitule());%></option>
                <%}%>
            </select>
            
        </div>
    </div>
    
    <div class="form-group">
        <label for="description" class="col-sm-2 control-label">Description</label>
        <div class="col-sm-3">
            <textarea type="text" name="description" id="description" class="form-control" placeholder="Description" readonly><%if(request.getAttribute("description") != null){out.print(request.getAttribute("description"));}%></textarea>
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
                    }%>" required readonly>
        </div>
    </div>
    
    <div class="form-group">
        <label for="datepicker" class="col-sm-2 control-label">Période d'inscription</label>
        <div class="col-sm-3">    
            <div class="input-daterange input-group" id="datepicker">
                <input type="text" class="input-sm form-control" name="dateDebut" placeholder="Date de début" autocomplete="off"
                   value="<%if (request.getAttribute("dateDebut") != null) {
                           out.print(request.getAttribute("dateDebut"));
                       }%>"/>
                <span class="input-group-addon">au</span>
                <input type="text" class="input-sm form-control" name="dateFin" placeholder="Date de fin" autocomplete="off"
                   value="<%if(request.getAttribute("dateFin") != null) {
                           out.print(request.getAttribute("dateFin"));
                       }%>"/>
            </div>
        </div>
    </div>
                
    <div class="row">
        <div class="col-md-1 col-md-offset-1">
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer">Enregister</button>
        </div>
        <div class="col-md-1 col-md-offset-1">
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer&nouveau">Enregister et nouveau</button>
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
        <strong><%if(msgSplited.length == 2){out.print(msgSplited[0] + ":");}%></strong><em><%out.print(msgSplited[idxMsg]);%></em>
    </div>
<%}%>
            
<%@include file="Modele/pied.jsp" %>
