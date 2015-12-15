<%-- 
    Document   : CreerDossier
    Created on : 19 nov. 2015, 11:35:03
    Author     : Sahare
--%>


<%@page import="modele.entite.TypeJustificatifEtranger"%>
<%@page import="modele.entite.TypeDossier"%>
<%@page import="service.DossierService"%>
<%@page import="modele.dao.FormationDAO"%>
<%@page import="modele.dao.EtudiantDAO"%>
<%@page import="modele.entite.Etudiant"%>
<%@page import="modele.entite.Formation"%>
<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<!DOCTYPE html>

<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript">
    
    window.onload=function(){
        <%if(request.getAttribute("focus") != null){%>
            document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
        <%}%>
        verifAllChecked();
    };
    
    function loadJustificatifs(){
        if($('input#nationalite-0').attr('checked') === true){
           $('input#pays').val('FRANCE');
       }
       $("form#formation").prop("action", "Navigation?action=voirAjoutDossier");
       $("form#formation").submit();
    };
    function verifAllChecked(){
        var allChecked = $('input[type="checkbox"]:checked').length === $('input[type="checkbox"]').length || $('input[type="checkbox"]').length === 0;
        //gestion de l'utilité des boutons
        $("button#save").prop("disabled", !allChecked);
        $("button#savenew").prop("disabled", !allChecked);
        $("button#askDocMissed").prop("disabled", allChecked);

        //gestion de la contrainte de remplissage sûr les champs du formulaire
        $("input#ine").prop("required", !allChecked);
        $("input#idDossier").prop("required", !allChecked);
        $("input#niveau").prop("required", !allChecked);
    };
    
    //Autocompletion pour l'INE.
    var availableINE = [
        <%
        List<String> lesINE = (List<String>) request.getAttribute("lesINE");
        String listINE = "";
        for(int i=0;i<lesINE.size();i++) {
            listINE += "\""+lesINE.get(i)+"\"";
            if(i != lesINE.size()-1) listINE+=",";
        }
        out.print(listINE);
        %>
    ];
    $(function() {
        $("input#ine").autocomplete({
            source: availableINE,
            select: function(event,ui) {
                console.log("SELECT:FUNCTION:"+ui.item.value);
                $("input#ine").val(ui.item.value);
                chargerEtudiant();
            }
        });
        //$("input#ine").on("autocompletechange", function() {chargerEtudiant();});
    });
    
    function chargerEtudiant() {
        var value = $("input#ine").val();
        console.log("value:"+value);
        if($.inArray(availableINE,value))
        {
            $.ajax({
                method:'GET',
                url:'Navigation',
                data:{action:"etudiantAutocompletion",numeroINE:value},
                dataType:'json',
                success: function(msg) {
                    console.log(msg);
                    //nationalite-0 français
                    $("input#nom").val(msg.nom);
                    $("input#prenom").val(msg.prenom);
                    $("input#codePostal").val(msg.codePostal);
                    $("input#adresse").val(msg.adressePostale);
                    $("input#ville").val(msg.ville);
                    $("input#pays").val(msg.pays);
                    if(msg.sexe === "Masculin")
                    {
                        $("input#sexe-0").prop("checked",true);
                        $("input#sexe-1").prop("checked",false);
                    } else {
                        $("input#sexe-0").prop("checked",false);
                        $("input#sexe-1").prop("checked",true);
                    }
                    if(msg.nationalite==="francais")
                    {
                        $("input#nationalite-0").prop("checked",true);
                        $("input#nationalite-1").prop("checked",false);
                        /// supprimer les justificatif
                        $("div#conteneurNationalite").empty();
                    } else {
                        $("input#nationalite-0").prop("checked",false);
                        $("input#nationalite-1").prop("checked",true);
                        /// append les justificatifs
                        $("div#conteneurNationalite").empty();
                        $("div#conteneurNationalite").append( 
        "<div class='form-group' id='divNationalite'>"+
            "<label class='col-md-2 control-label' for='niveau'>Niveau :</label>  "+
            "<div class='col-md-4'>"+
                "<input id='niveau' name='niveau' type='text' placeholder='niveau' class='form-control input-md' value='' autocomplete='off' required>"+
            "</div>"+
        "</div>"+
        "<div class='form-group'>"+
            "<label class='col-md-2 control-label' for='avis'>Avis</label>"+
            "<div class='col-md-4'>"+
                "<textarea class='form-control' id='avis' name='avis' placeholder='avis' autocomplete='off'></textarea>"+
            "</div>"+
        "</div>"
                        );
                    $("input#niveau").val(msg.niveau);
                    $("textarea#avis").val(msg.avis);
                    }
                },
                error: function() {
                    //http://localh ost:8080/gist/Navigation?action=etudiantAutocompletion&numeroINE=ine1
                    console.log("ERROR_URL:"+'Navigation?action=etudiantAutocompletion&numeroINE='+value);
                    effacerChamp();
                }
            });
        }
        else
        {
            effacerChamp();
        }
    }
    
    function effacerChamp() {
        $("input#nom").val("");
        $("input#prenom").val("");
        $("input#codePostal").val("");
        $("input#adresse").val("");
        $("input#ville").val("");
        $("input#pays").val("");
    }
    
</script>

<form action="Navigation?action=ajouterDossier" method="POST" class="form-horizontal" id="formation">
    <div class="form-group">
        <label class="col-md-2 control-label" for="nom">N° INE : </label>  
        <div class="col-md-4">
            <input id="ine" name="ine" onkeyup="chargerEtudiant();" type="text" placeholder="N° INE" class="form-control input-md" value="<%if(request.getAttribute("ine") != null){out.print(request.getAttribute("ine"));}%>" autocomplete="off" required autofocus>
        </div>
    </div>
        
    <div class="form-group">
        <label class="col-md-2 control-label" for="nom">Nom : </label>  
        <div class="col-md-4">
            <input id="nom" name="nom" type="text" placeholder="nom" class="form-control input-md" value="<%if(request.getAttribute("nom") != null){out.print(request.getAttribute("nom"));}%>" autocomplete="off" required>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-2 control-label" for="prenom">Prénom : </label>  
        <div class="col-md-4">
            <input id="prenom" name="prenom" type="text" placeholder="prénom" class="form-control input-md" value="<%if(request.getAttribute("prenom") != null){out.print(request.getAttribute("prenom"));}%>" autocomplete="off" required>
        </div>
    </div>

    <div class="form-group">
        <label for="pays" class="col-sm-2 control-label">Pays : </label>
        <div class="col-md-4">
            <input id="pays" name="pays" type="text" placeholder="pays" class="form-control input-md" value="<%if(request.getAttribute("pays") != null){out.print(request.getAttribute("pays"));}%>" autocomplete="on" required>
        </div>
    </div>
        
    <div class="form-group">
        <label class="col-md-2 control-label" for="adresse">Adresse : </label>  
        <div class="col-md-4">
            <input id="adresse" name="adresse" type="text" placeholder="adresse" class="form-control input-md" value="<%if(request.getAttribute("adresse") != null){out.print(request.getAttribute("adresse"));}%>" autocomplete="off" required>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-2 control-label" for="ville">Ville : </label>  
        <div class="col-md-4">
            <input id="ville" name="ville" type="text" placeholder="ville" class="form-control input-md" value="<%if(request.getAttribute("ville") != null){out.print(request.getAttribute("ville"));}%>" autocomplete="off" required>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-2 control-label" for="codePostal">Code Postal : </label>  
        <div class="col-md-2">
            <input id="codePostal" name="codePostal" type="text" placeholder="code postal" class="form-control input-md" value="<%if(request.getAttribute("codePostal") != null){out.print(request.getAttribute("codePostal"));}%>" autocomplete="off" required>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-2 control-label" for="sexe">Sexe : </label>
        <div class="col-md-4"> 
            <label class="radio-inline" for="sexe-0">
                <input type="radio" name="sexe" id="sexe-0" value="M" <%if(request.getAttribute("sexe") != null){if(request.getAttribute("sexe").equals("M")){%>checked<%}}else{%>checked<%}%>>
                M
            </label> 
            <label class="radio-inline" for="sexe-1">
                <input type="radio" name="sexe" id="sexe-1" value="F" <%if(request.getAttribute("sexe") != null){if(request.getAttribute("sexe").equals("F")){%>checked<%}}%>>
                F
            </label>
        </div>
    </div>
    
    <div id="conteneurNationalite">
        <%if(request.getAttribute("nationalite") != null){
        if(request.getAttribute("nationalite").equals(TypeJustificatifEtranger.etranger.toString())){%>
        <div class="form-group">
            <label class="col-md-2 control-label" for="niveau">Niveau : </label>  
            <div class="col-md-4">
                <input id="niveau" name="niveau" type="text" placeholder="niveau" class="form-control input-md" value="<%if(request.getAttribute("niveau") != null){out.print(request.getAttribute("niveau"));}%>" autocomplete="off" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label" for="avis">Avis</label>
            <div class="col-md-4">                     
                <textarea class="form-control" id="avis" name="avis" placeholder="avis" autocomplete="off"><%if(request.getAttribute("avis") != null){out.print(request.getAttribute("avis"));}%></textarea>
            </div>
        </div>
        <%}}%>
    </div>
    
    <div class="form-group">
        <label class="col-md-2 control-label" for="notes">Notes</label>
        <div class="col-md-4">                     
            <textarea class="form-control" id="notes" name="notes" placeholder="Notes" autocomplete="off"><%if(request.getAttribute("notes") != null){out.print(request.getAttribute("notes"));}%></textarea>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-2 control-label" for="type">Type : </label>
        <div class="col-md-4">
            <label class="radio-inline" for="type-0">
                <input type="radio" name="type" id="type-0" value="<%out.print(TypeDossier.inscription);%>" onchange="loadJustificatifs()" <%if(request.getAttribute("type") == null){%>checked<%}else if(request.getAttribute("type").equals(TypeDossier.inscription.toString())){%>checked<%}%>> Inscription
            </label>
            <label class="radio-inline" for="type-1">
                <input type="radio" name="type" id="type-1" value="<%out.print(TypeDossier.admissibilite);%>" onchange="loadJustificatifs()" <%if(request.getAttribute("type") != null){if(request.getAttribute("type").equals(TypeDossier.admissibilite.toString())){%>checked<%}}%>> Admission
            </label>
        </div>
    </div>

    <div class="form-group">
        <label for="formationIntitule" class="col-sm-2 control-label">Formation : </label>
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
        <label class="col-md-2 control-label" for="nationalite">Nationalité : </label>
        <div class="col-md-4">
            <label class="radio-inline" for="nationalite-0">
                <input type="radio" name="nationalite" id="nationalite-0" value="<%out.print(TypeJustificatifEtranger.francais.toString());%>" onchange="loadJustificatifs()" <%if(request.getAttribute("nationalite") == null){%>checked<%}else if(request.getAttribute("nationalite").equals(TypeJustificatifEtranger.francais.toString())){%>checked<%}%>> Français
            </label>
            <label class="radio-inline" for="nationalite-1">
                <input type="radio" name="nationalite" id="nationalite-1" value="<%out.print(TypeJustificatifEtranger.etranger.toString());%>" onchange="loadJustificatifs()" <%if(request.getAttribute("nationalite") != null){if(request.getAttribute("nationalite").equals(TypeJustificatifEtranger.etranger.toString())){%>checked<%}}%>> Etranger
            </label>
        </div>
    </div>
        
    <div class="form-group">
        <label for="justificatifs" class="col-sm-2 control-label">Justificatifs : </label>
        <div class="col-sm-3" id="justificatifsDiv">
            <% List<Justificatif> justificatifs = (List<Justificatif>) request.getAttribute("justificatifs");
            if(justificatifs != null){
                for (Justificatif justificatif : justificatifs){%>
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
               
    <div class="form-group">
        <label class="col-md-2 control-label" for="idDossier">Numéro du dossier : </label>  
        <div class="col-md-4">
            <input id="idDossier" name="idDossier" type="text" placeholder="n° dossier" class="form-control input-md" 
                value="<%if(request.getAttribute("idDossier") != null){
                    out.print(request.getAttribute("idDossier"));
                }else{
                    out.print(new DossierService().getNewID());
                }%>" pattern="<%out.print(new DossierService().getRegexIdDossier());%>" 
                title="<%out.print(new DossierService().getRegexIdDossier());%>" autocomplete="off" required autofocus>
        </div>
    </div>


    <div class="row">
        <div class="col-md-2 col-md-offset-2">
            <a class="btn btn-default" href="Navigation?action=voirGestionDossiers">Annuler</a>
        </div>
        <div class="col-md-2">
            <button class="btn btn-default" id="askDocMissed" name="action" value="voirLettreDocManquant"><i class="fa fa-envelope"></i> Demander les documents manquants</button>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <button id="save" name="bouton" type="submit" class="btn btn-success" value="enregistrer"><i class="fa fa-save"></i> Enregistrer</button>
            <button id="savenew" name="bouton" type="submit" class="btn btn-primary" value="enregistrer&nouveau"><i class="fa fa-save"></i> Enregistrer et nouveau <i class="fa fa-plus"></i></button>
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
