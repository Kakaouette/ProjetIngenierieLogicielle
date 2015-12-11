<%-- 
    Document   : modifFormation
    Created on : 29 nov. 2015, 17:48:12
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

    function importerListeJustificatif(from, to) {
        var name;
        if(to.indexOf("inscription") !== -1){
            name = "Inscription";
        }else if(to.indexOf("admission") !== -1){
            name = "Admission";
        }
        
        var copieJustificatifs = $(from + " li").clone();
        copieJustificatifs.each(function() {
            //gestion de l'ajout
            var justificatifsPresent = $(to + " li");
            var labelFrancais = $(this).children("label").text();
            var inserer = true;
            justificatifsPresent.each(function() {
                var labelEtranger = $(this).children("label").text();
                if(labelFrancais===labelEtranger) {
                    inserer = false;
                }
            });
            if(inserer){
                //adapter parametre du new justificatif
                $(this).children('label').attr("name", "justificatifs"+name+"Etranger");
                $(this).children('input').attr("name", "justificatifs"+name+"Etranger");
                var title = $(this).find('label').text().replace("'","\\'");
                $(this).children('a').attr("onclick","deleteJ('" + to + "','"+title+"')");
                //ajouter le new justificatif
                $(to + " ul#justificatifsAdded").append($(this));
            }
        });
    }

    function createDialog(location) {
        //creation et ajout du dialog
        $('body').append(
            '<div id="dialogJustificatifAAjouter" title="Ajouter un justificatif">' + 
                '<form id=ajouterJustificatif>' + 
                    '<div class="form-group">' + 
                        '<label for="titre" class="col-sm-2 control-label">Titre</label>' + 
                        '<div class="col-sm-10">' + 
                            '<input type="text" name="titre" id="titre" class="form-control" placeholder="Titre" autocomplete="off" required autofocus>' + 
                        '</div>' + 
                    '</div>' + 
                '</form>' + 
            '</div>');
        //definition du comportement lors du submit
        $('div#dialogJustificatifAAjouter form#ajouterJustificatif').submit(function(){
            $('div#dialogJustificatifAAjouter div[class = "alert alert-danger"]').remove(); //remove old msg du dialog
            
            if(addJ(location, $("div#dialogJustificatifAAjouter input#titre").val())){ //try to add justificatif
                $("div#dialogJustificatifAAjouter").dialog("close");
            }
            return false; //annuler changement de page dû au submit
        });

        //definition du dialog
        $('div#dialogJustificatifAAjouter').dialog({
            modal: true,
            close:function( event, ui ){
                    $("div#dialogJustificatifAAjouter").remove();
                },
            buttons: {
                "Ajouter":{
                    text : 'Ajouter' ,class : 'btn btn-success', click : function() {
                        $("div#dialogJustificatifAAjouter form#ajouterJustificatif").submit();
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
    
    //ajouter un justificatif//return true: justificatif ajouté; false: justificatif non ajouté
    function addJ(location, val) {
        $path = location + ' ul#justificatifsAdded';
        if(val === ""){
            $('div#dialogJustificatifAAjouter form#ajouterJustificatif').prepend($('<div>').attr('class', 'alert alert-danger').append('<em>Entrez le titre de justificatif.</em>'));
            return false;
        }else if($($path + ' li:contains('+val+')').filter(function(index){return $(this).children('label#justificatifs').text() === val;}).length === 0){
            $name = "justificatifs";
            if(location.indexOf("inscription") !== -1){
                $name += "Inscription";
            }else if(location.indexOf("admission") !== -1){
                $name += "Admission";
            }
            if(location.indexOf("Francais") !== -1){
                $name += "Francais";
            }else if(location.indexOf("Etranger") !== -1){
                $name += "Etranger";
            }

            $($path).append($('<li>').append('<label id="justificatifs" name="justificatifs" class="control-label">' + val + '</label>'));
            $($path + ' li:last').append($('<input>').attr('type', "hidden").attr('name', $name).attr('value', val));            
            $($path + ' li:last').append($('<a>').attr('class', "btn btn-link").attr('onclick', 'deleteJ(\"' + location + '", "' + val + '\")').append('<i class="fa fa-remove"></i> Supprimer'));
            return true;
        }else{
            $('div#dialogJustificatifAAjouter form#ajouterJustificatif').prepend($('<div>').attr('class', 'alert alert-danger').append('<em>Le justificatif existe déja pour cette catégorie.</em>'));
            return false;
        }
    };
    //supprimer un justificatif
    function deleteJ(location, val){
        $(location + ' li:contains('+val+')').filter(function(index){
            return $(this).children('label#justificatifs').text() === val;
        }).remove();
    };
</script>

<form action="Navigation?action=modifierFormation&id=<%out.print(request.getAttribute("id"));%>" method="POST" class="form-horizontal">
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
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsFrancais td#inscription', '<%out.print( justificatif.replace("'", "\\'") );%>')"><i class="fa fa-remove"></i> Supprimer</a>
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
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsFrancais td#admission', '<%out.print( justificatif.replace("'", "\\'") );%>')"><i class="fa fa-remove"></i> Supprimer</a>
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
                        <a class="btn btn-link" onclick="importerListeJustificatif('table#justificatifsFrancais td#inscription', 'table#justificatifsEtranger td#inscription')" name="boutonImporter" id="boutonImporter" value=""><i class="fa fa-arrow-circle-down"></i> Importer justificatifs français</a>
                    </th>
                    <th>Admissibilité
                        <a class="btn btn-link" onclick='createDialog("table#justificatifsEtranger td#admission")'><i class="fa fa-plus-circle"></i> Ajouter</a>
                        <a class="btn btn-link" onclick="importerListeJustificatif('table#justificatifsFrancais td#admission', 'table#justificatifsEtranger td#admission')" name="boutonImporter" id="boutonImporter" value=""><i class="fa fa-arrow-circle-down"></i> Importer justificatifs français</a>
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
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsEtranger td#inscription', '<%out.print( justificatif.replace("'", "\\'") );%>')"><i class="fa fa-remove"></i> Supprimer</a>
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
                                            <a class="btn btn-link" onclick="deleteJ('table#justificatifsEtranger td#admission', '<%out.print( justificatif.replace("'", "\\'") );%>')"><i class="fa fa-remove"></i> Supprimer</a>
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