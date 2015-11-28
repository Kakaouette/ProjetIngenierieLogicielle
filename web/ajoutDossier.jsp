<%-- 
    Document   : CreerDossier
    Created on : 19 nov. 2015, 11:35:03
    Author     : Sahare
--%>


<%@page import="service.DossierService"%>
<%@page import="modele.entite.Formation"%>
<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<%@page import="service.DossierService"%>
<!DOCTYPE html>

<%if(request.getAttribute("focus") != null){%>
<script type="text/javascript">
    window.onload=function(){
        document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
    };
</script>
<%}%>

<form action="Navigation?action=ajouterDossier" method="POST" class="form-horizontal">
<fieldset>
<div class="form-group">
  <input id="formationIntitule" name="formationIntitule" type="hidden" value="<%out.print(request.getAttribute("formationIntitule"));%>">
</div>
    
<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="idDossier">Numéro du dossier :</label>  
  <div class="col-md-4">
  <input id="idDossier" name="idDossier" type="text" placeholder="n° dossier" class="form-control input-md" 
         value="<%if(request.getAttribute("idDossier") != null){
                    out.print(request.getAttribute("idDossier"));
                }else{
                    out.print(new DossierService().getNewID());
                }%>" autocomplete="off" required autofocus>
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="nom">Nom :</label>  
  <div class="col-md-4">
  <input id="nom" name="nom" type="text" placeholder="nom" class="form-control input-md" value="<%if(request.getAttribute("nom") != null){out.print(request.getAttribute("nom"));}%>" autocomplete="off" required>
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="prenom">Prénom : </label>  
  <div class="col-md-4">
  <input id="prenom" name="prenom" type="text" placeholder="prénom" class="form-control input-md" value="<%if(request.getAttribute("prenom") != null){out.print(request.getAttribute("prenom"));}%>" autocomplete="off" required>
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="adresse">Adresse :</label>  
  <div class="col-md-4">
  <input id="adresse" name="adresse" type="text" placeholder="adresse" class="form-control input-md" value="<%if(request.getAttribute("adresse") != null){out.print(request.getAttribute("adresse"));}%>" autocomplete="off" required>
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="ville">Ville :</label>  
  <div class="col-md-4">
  <input id="ville" name="ville" type="text" placeholder="ville" class="form-control input-md" value="<%if(request.getAttribute("ville") != null){out.print(request.getAttribute("ville"));}%>" autocomplete="off" required>
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="codePostal">Code Postal :</label>  
  <div class="col-md-2">
  <input id="codePostal" name="codePostal" type="text" placeholder="code postal" class="form-control input-md" value="<%if(request.getAttribute("codePostal") != null){out.print(request.getAttribute("codePostal"));}%>" autocomplete="off" required>
    
  </div>
</div>

<!-- Multiple Radios (inline) -->
<div class="form-group">
  <label class="col-md-2 control-label" for="sexe">Sexe :</label>
  <div class="col-md-4"> 
    <label class="radio-inline" for="sexe-0">
      <input type="radio" name="sexe" id="sexe-0" value="M" checked="checked">
      M
    </label> 
    <label class="radio-inline" for="sexe-1">
      <input type="radio" name="sexe" id="sexe-1" value="F">
      F
    </label>
  </div>
</div>

<div class="form-group">
  <label class="col-md-2 control-label" for="type">Type:</label>
  <div class="col-md-4"> 
      <input type="radio" name="type" value="inscription" <%if(request.getAttribute("type") == null){%>checked<%}else if(request.getAttribute("type").equals("inscription")){%>checked<%}%>>Inscription
      <input type="radio" name="type" value="admission" <%if(request.getAttribute("type") != null){if(request.getAttribute("type").equals("admission")){%>checked<%}}%>>Admission
  </div>
</div>

<!-- Textarea -->
<div class="form-group">
  <label class="col-md-2 control-label" for="notes">Notes</label>
  <div class="col-md-4">                     
      <textarea class="form-control" id="notes" name="notes" placeholder="Notes" autocomplete="off"><%if(request.getAttribute("notes") != null){out.print(request.getAttribute("notes"));}%></textarea>
  </div>
</div>

<!-- Button (Double) -->
<div class="row col-md-offset-2">
    <button id="save" name="bouton" type="submit" class="btn btn-primary" value="enregistrer"><i class="fa fa-save"></i> Enregistrer</button>
    <button id="savenew" name="bouton" type="submit" class="btn btn-success" value="enregistrer&nouveau"><i class="fa fa-save"></i> Enregistrer et nouveau <i class="fa fa-plus"></i></button>
    <a class="btn btn-default" href="Navigation?action=voirValidationJustificatifsDossier&formationIntitule=<%out.print(request.getAttribute("formationIntitule"));%>">Annuler</a>
</div>

</fieldset>
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
