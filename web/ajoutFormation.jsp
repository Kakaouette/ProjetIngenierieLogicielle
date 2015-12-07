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

<script src="jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<script src="jQuery/bootstrap-datepicker.js"></script>
<link href="jQuery/bootstrap-datepicker3.css" rel="stylesheet">
<script type="text/javascript">
    $(function() {
        $('.input-daterange').datepicker({ //adaptation auto date start et date end compris
            format: "dd/mm/yyyy",
            todayBtn: true,
            language: "fr",
            autoclose: true,
            todayHighlight: true
        });
    });
</script>

<link rel="stylesheet" href="bootstrap/jquery-custom/jquery-ui-1.10.0.custom.css">
<script type="text/javascript">
    <%if(request.getAttribute("focus") != null){%>
        window.onload=function(){
            document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
        };
    <%}%>

    function createDialog(location) {
        //creation et ajout du dialog
        $('body').append(
            '<div id="dialogJustificatifAAjouter" title="Ajouter un justificatif">' + 
                '<div class="row">' + 
                    '<label for="titre" class="col-sm-2 control-label">Titre</label>' + 
                    '<div class="col-sm-10">' + 
                        '<input type="text" name="titre" id="titre" class="form-control" placeholder="Titre" autocomplete="off" required autofocus>' + 
                    '</div>' + 
                '</div>' + 
            '</div>');
        
        
        $('div#dialogJustificatifAAjouter').dialog({
            modal: true,
            close:function( event, ui ){
                    $("div#dialogJustificatifAAjouter").remove();
                },
            buttons: {
                "Ajouter":{
                    text : 'Ajouter' ,class : 'btn btn-success', click : function() {
                        $('div#dialogJustificatifAAjouter br').remove();
                        $('div#dialogJustificatifAAjouter div[class = "alert alert-danger"]').remove();

                        $path = location + ' ul#justificatifsAdded';
                        if($("div#dialogJustificatifAAjouter input#titre").val() === ""){
                            $('div#dialogJustificatifAAjouter').append($('<br>'))
                            $('div#dialogJustificatifAAjouter').append($('<div>').attr('class', 'alert alert-danger').append('<em>Entrez le titre de justificatif.</em>'))
                            //<em></em>
                        }else if($($path + ' li:contains('+$("div#dialogJustificatifAAjouter input#titre").val()+')').length === 0){
                            $name = "justificatifs";
                            if(location.contains("inscription")){
                                $name += "Inscription";
                            }else if(location.contains("admission")){
                                $name += "Admission"
                            }
                            if(location.contains("Francais")){
                                $name += "Francais"
                            }else if(location.contains("Etranger")){
                                $name += "Etranger"
                            }

                            $($path).append($('<li>').append('<label id="justificatifs" name="justificatifs" class="control-label">' + $("div#dialogJustificatifAAjouter input#titre").val() + '</label>'));
                            $($path + ' li:last').append($('<input>').attr('type', "hidden").attr('name', $name).attr('value', $("div#dialogJustificatifAAjouter input#titre").val()));            
                            $($path + ' li:last').append($('<a>').attr('class', "btn btn-link").attr('onclick', 'deleteJ(\"' + location + '", "' + $("div#dialogJustificatifAAjouter input#titre").val() + '\")').append('<i class="fa fa-remove"></i> Supprimer'));
                            $(this).dialog("close");
                        }else{
                            $('div#dialogJustificatifAAjouter').append($('<br>'))
                            $('div#dialogJustificatifAAjouter').append($('<div>').attr('class', 'alert alert-danger').append('<em>Le justificatif existe déja pour cette catégorie</em>'))
                            //<em></em>
                        }
                    }
                },
                "Annuler": {
                    text : 'Annuler' ,class : 'btn btn-default', click : function() {
                        $(this).dialog("close");
                    }
                }
            }
        });
    };
    
    function deleteJ(location, val){
        $path = location + ' li:contains('+val+')';
        $($path).remove();
    };
</script>

<form action="Navigation?action=ajouterFormation" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="intitule" class="col-sm-2 control-label">Intitulé</label>
        <div class="col-sm-3">
            <input type="text" name="intitule" id="intitule" class="form-control" 
                   value="<%if(request.getAttribute("intitule") != null){out.print(request.getAttribute("intitule"));}%>" 
                   placeholder="Intitulé" autocomplete="off" required autofocus>
        </div>
    </div>
    
    <div class="form-group">
        <label for="description" class="col-sm-2 control-label">Description</label>
        <div class="col-sm-3">
            <textarea type="text" name="description" id="description" class="form-control" placeholder="Description"><%if(request.getAttribute("description") != null){out.print(request.getAttribute("description"));}%></textarea>
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
    
    <div class="form-group">
        <label for="justificatifsAdded" class="col-sm-2 control-label">Justificatifs français</label>
        
        <div class="row col-sm-offset-1">
            <table cellspacing="0" class="table table-bordered table-condensed dt-responsive" width="100%" id="justificatifsFrancais">
                <thead>
                    <th>Inscription
                        <a class="btn btn-link" onclick='createDialog("table#justificatifsFrancais td#inscription")'><i class="fa fa-plus-circle"></i> Ajouter</a>
                    </th>
                    <th>Admissibilité
                        <a class="btn btn-link" onclick='createDialog("table#justificatifsFrancais td#admission")'><i class="fa fa-plus-circle"></i> Ajouter</a>
                    </th>
                </thead>
                <tbody>
                    <tr>
                        <td id="inscription">
                            <ul id="justificatifsAdded" name="justificatifsAdded">
                                <% if(request.getAttribute("justificatifsInscriptionFrancais") != null){
                                    String[] justificatifs=(String[]) request.getAttribute("justificatifsInscriptionFrancais");
                                    for (String justificatif : justificatifs){
                                %>
                                        <li>
                                            <label id="justificatifs" name="justificatifsInscriptionFrancais" class="control-label"><%out.print(justificatif);%></label>
                                            <input type="hidden" name="justificatifsInscriptionFrancais" value="<%out.print(justificatif);%>"/>
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsFrancais td#inscription', '<%out.print( justificatif );%>')"><i class="fa fa-remove"></i> Supprimer</a>
                                        </li>
                                    <%}
                                }%>
                            </ul>
                        </td>
                        <td id="admission">
                            <ul id="justificatifsAdded" name="justificatifsAdded">
                                <% if(request.getAttribute("justificatifsAdmissionFrancais") != null){
                                    String[] justificatifs=(String[]) request.getAttribute("justificatifsAdmissionFrancais");
                                    for (String justificatif : justificatifs){
                                %>
                                        <li>
                                            <label id="justificatifsInscriptionEtranger" name="justificatifsAdmissionFrancais" class="control-label"><%out.print(justificatif);%></label>
                                            <input type="hidden" name="justificatifsAdmissionFrancais" value="<%out.print(justificatif);%>"/>
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsFrancais td#admission', '<%out.print( justificatif );%>')"><i class="fa fa-remove"></i> Supprimer</a>
                                        </li>
                                    <%}
                                }%>
                            </ul>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="form-group">
        <label for="justificatifsAdded" class="col-sm-2 control-label">Justificatifs étranger</label>        
        <div class="row col-sm-offset-1">
            <table cellspacing="0" class="table table-bordered table-condensed dt-responsive" width="100%" id="justificatifsEtranger">
                <thead>
                    <th>Inscription
                        <a class="btn btn-link" onclick='createDialog("table#justificatifsEtranger td#inscription")'><i class="fa fa-plus-circle"></i> Ajouter</a>
                    </th>
                    <th>Admissibilité
                        <a class="btn btn-link" onclick='createDialog("table#justificatifsEtranger td#admission")'><i class="fa fa-plus-circle"></i> Ajouter</a>
                    </th>
                </thead>
                <tbody>
                    <tr>
                        <td id="inscription">
                            <ul id="justificatifsAdded" name="justificatifsAdded">
                                <% if(request.getAttribute("justificatifsInscriptionEtranger") != null){
                                    String[] justificatifs=(String[]) request.getAttribute("justificatifsInscriptionEtranger");
                                    for (String justificatif : justificatifs){
                                %>
                                        <li>
                                            <label id="justificatifsInscriptionEtranger" name="justificatifsInscriptionEtranger" class="control-label"><%out.print(justificatif);%></label>
                                            <input type="hidden" name="justificatifsInscriptionEtranger" value="<%out.print(justificatif);%>"/>
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsEtranger td#inscription', '<%out.print( justificatif );%>')"><i class="fa fa-remove"></i> Supprimer</a>
                                        </li>
                                    <%}
                                }%>
                            </ul>
                        </td>
                        <td id="admission">
                            <ul id="justificatifsAdded" name="justificatifsAdded">
                                <% if(request.getAttribute("justificatifsAdmissionEtranger") != null){
                                    String[] justificatifs=(String[]) request.getAttribute("justificatifsAdmissionEtranger");
                                    for (String justificatif : justificatifs){
                                %>
                                        <li>
                                            <label id="justificatifsInscriptionEtranger" name="justificatifsAdmissionEtranger" class="control-label"><%out.print(justificatif);%></label>
                                            <input type="hidden" name="justificatifsAdmissionEtranger" value="<%out.print(justificatif);%>"/>
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsEtranger td#admission', '<%out.print( justificatif );%>')"><i class="fa fa-remove"></i> Supprimer</a>
                                        </li>
                                    <%}
                                }%>
                            </ul>
                        </td>
                    </tr>
                </tbody>
            </table>
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
        <strong><%if(msgSplited.length == 2){out.print(msgSplited[0] + ":");}%></strong><em><%out.print(msgSplited[idxMsg]);%></em>
    </div>
<%}%>

<%@include file="Modele/pied.jsp" %>