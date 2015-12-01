<%-- 
    Document   : CreerDossier
    Created on : 19 nov. 2015, 11:35:03
    Author     : Sahare
--%>


<%@page import="modele.dao.FormationDAO"%>
<%@page import="page.action.VoirValidationJustificatifsDossierAction"%>
<%@page import="modele.entite.Formation"%>
<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<!DOCTYPE html>

<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript">
    <%if(request.getAttribute("focus") != null){%>
        window.onload=function(){
            document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
        };
    <%}%>
    
    function loadJustificatifs(){
       $("form#formation").prop("action", "Navigation?action=voirValidationJustificatifsDossier");
       $("form#formation").submit();
    };
    function verifAllChecked(){
        var allChecked = $('input[type="checkbox"]:checked').length === $('input[type="checkbox"]').length;
        if(allChecked){
            $("button#bouton").prop("disabled", false );
        }else{
            $("button#bouton").prop("disabled", true );
        }
    };
</script>

<form action="Navigation?action=voirAjoutDossier" method="POST" class="form-horizontal" id="formation">
    <div class="form-group">
        <label class="col-md-2 control-label" for="type">Type:</label>
        <div class="col-md-4">
            <label class="radio-inline" for="type-0">
                <input type="radio" name="type" id="type-0" value="inscription" onchange="loadJustificatifs()" <%if(request.getAttribute("type") == null){%>checked<%}else if(request.getAttribute("type").equals("inscription")){%>checked<%}%>> Inscription
            </label>
            <label class="radio-inline" for="type-1">
                <input type="radio" name="type" id="type-1" value="admission" onchange="loadJustificatifs()" <%if(request.getAttribute("type") != null){if(request.getAttribute("type").equals("admission")){%>checked<%}}%>> Admission
            </label>
        </div>
    </div>
            
    <div class="form-group">
        <label for="formationIntitule" class="col-sm-2 control-label">Formation: </label>
        <div class="col-sm-3">
            <select name="formationIntitule" id="formationIntitule" class="form-control" onchange="loadJustificatifs()">
                <% List<Formation> formations=(List<Formation>) request.getAttribute("formations");
                for (Formation formation : formations){
                %>
                <option value="<%out.print(formation.getIntitule());%>"<%if(request.getAttribute("formationIntitule") != null){
                                if(request.getAttribute("formationIntitule").equals(formation.getIntitule())){%>selected<%}
                            }%>><%out.print(formation.getIntitule());%></option>
                <%}%>
            </select>
            
        </div>
    </div>
            
    <div class="form-group">
        <label class="col-md-2 control-label" for="nationalite">Nationalité: </label>
        <div class="col-md-4">
            <label class="radio-inline" for="nationalite-0">
                <input type="radio" name="nationalite" id="type-0" value="francais" onchange="loadJustificatifs()" <%if(request.getAttribute("nationalite") == null){%>checked<%}else if(request.getAttribute("nationalite").equals("francais")){%>checked<%}%>> Français
            </label>
            <label class="radio-inline" for="nationalite-1">
                <input type="radio" name="nationalite" id="type-1" value="etranger" onchange="loadJustificatifs()" <%if(request.getAttribute("nationalite") != null){if(request.getAttribute("nationalite").equals("etranger")){%>checked<%}}%>> Etranger
            </label>
        </div>
    </div>
        
    <div class="form-group">
        <label for="justificatifs" class="col-sm-2 control-label">Justificatifs: </label>
        <div class="col-sm-3" id="justificatifsDiv">
            <% List<Justificatif> justificatifs = (List<Justificatif>) request.getAttribute("justificatifs");
            if(justificatifs != null){
                for (Justificatif justificatif : justificatifs){
                %>
                    <label class="checkbox-inline" for="justificatifs-<%out.print(justificatif.getTitre());%>">
                        <input type="checkbox" name="justificatifs" id="justificatifs-<%out.print(justificatif.getTitre());%>" 
                            value="<%out.print(justificatif.getTitre());%>" 
                            <%if(request.getAttribute("justificatifsChecked") != null){
                                List<Justificatif> justificatifsChecked=(List<Justificatif>) request.getAttribute("justificatifsChecked");
                                if(justificatifsChecked.contains(justificatif.getTitre())){%>checked<%}
                            }%> onchange="verifAllChecked()"> <%out.print(justificatif.getTitre());%>
                    </label>
                    <br>
                <%}
            }%>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2 col-md-offset-2">
            <a class="btn btn-default" href="Navigation?action=voirGestionDossiers">Retour</a>
        </div>
        <div class="col-md-2">
            <a class="btn btn-default" href="Navigation?action=voirLettreDocManquant"><i class="fa fa-envelope"></i> Demander les documents manquants</a>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="suivant"
                <%if(justificatifs != null){
                    if(justificatifs.size()>0){
                        if(request.getAttribute("justificatifsChecked") != null){
                            List<Justificatif> justificatifsChecked = (List<Justificatif>) request.getAttribute("justificatifsChecked");
                            if(justificatifsChecked != justificatifs){%>disabled<%}
                }else{%>disabled<%}}}%>>Suivant <i class="fa fa-arrow-right"></i></button>
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