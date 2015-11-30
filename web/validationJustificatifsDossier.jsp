<%-- 
    Document   : CreerDossier
    Created on : 19 nov. 2015, 11:35:03
    Author     : Sahare
--%>


<%@page import="page.action.VoirValidationJustificatifsDossierAction"%>
<%@page import="modele.entite.Formation"%>
<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<!DOCTYPE html>

<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript">
    <%if(request.getAttribute("focus") != null){%>
        window.onload=function(){
            document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
        };
    <%}%>
    
    document.getElementById("formationIntitule").onchange=function(){
        <%new VoirValidationJustificatifsDossierAction().execute(request, response);%>
    };
    $(document).ready(function(){
        
    });
    
    $("select#formationIntitule").change(function(){
        alert("The select has been changed.");
        <%new VoirValidationJustificatifsDossierAction().execute(request, response);%>
    });
    function loadJustificatifs(){
        //alert("The select has been changed.");
        <%new VoirValidationJustificatifsDossierAction().execute(request, response);%>
            <% List<Justificatif> justificatifs = (List<Justificatif>) request.getAttribute("justificatifs");
            if(justificatifs != null){
                for (Justificatif justificatif : justificatifs){
                %>
                    $("div#justificatifsDiv").append('<label class="checkbox-inline" for="justificatifs-<%out.print(justificatif.getTitre());%>">
                        <input type="checkbox" name="justificatifs" id="justificatifs-<%out.print(justificatif.getTitre());%>" 
                            value="<%out.print(justificatif.getTitre());%>" 
                            <%if(request.getAttribute("justificatifsChecked") != null){
                                List<Justificatif> justificatifsChecked=(List<Justificatif>) request.getAttribute("justificatifsChecked");
                                if(justificatifsChecked.contains(justificatif.getTitre())){%>checked<%}
                            }%>> <%out.print(justificatif.getTitre());%>
                    </label>
                    <br>')
                <%}
            }%>
    }
    $('input[type="checkbox"]').change(function(){
        alert("Changed.");
        var allChecked = $('input[type="checkbox"]:checked').length === $('input[type="checkbox"]').length;
        if(allChecked){
            $("button#bouton").prop("disabled", false );
        }else{
            $("button#bouton").prop("disabled", true );
        }
    });
</script>

<form action="Navigation?action=voirAjoutDossier" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="formationIntitule" class="col-sm-2 control-label">Formation: </label>
        <div class="col-sm-3">
            <!-- A faire : positionner le type actuel en selection par défaut -->
            <select name="formationIntitule" id="formationIntitule" class="form-control" onchange="loadJustificatifs()">
                <% List<Formation> formations=(List<Formation>) request.getAttribute("formations");
                for (Formation formation : formations){
                %>
                    <option <%if(request.getAttribute("formationIntitule") != null){
                                if(request.getAttribute("formationIntitule").equals(formation.getIntitule())){%>selected<%}
                            }%>><%out.print(formation.getIntitule());%></option>
                <%}%>
            </select>
            
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
                            }%>> <%out.print(justificatif.getTitre());%>
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
                <%if(justificatifs == null){/*disabled ->*/%><%}
                else if(request.getAttribute("justificatifsChecked") != null){
                    List<Justificatif> justificatifsChecked = (List<Justificatif>) request.getAttribute("justificatifsChecked");
                    if(justificatifsChecked != justificatifs){%>disabled<%}
                }else{%>disabled<%}%>>Suivant <i class="fa fa-arrow-right"></i></button>
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