<%-- 
    Document   : CreerDossier
    Created on : 19 nov. 2015, 11:35:03
    Author     : Sahare
--%>


<%@page import="modele.entite.Formation"%>
<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<!DOCTYPE html>

<form action="Navigation?action=modifierUtilisateur" method="POST" class="form-horizontal">
<fieldset>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="textinput">Numéro du dossier :</label>  
  <div class="col-md-4">
  <input id="textinput" name="textinput" type="text" placeholder="n° dossier" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="nom">Nom :</label>  
  <div class="col-md-4">
  <input id="nom" name="nom" type="text" placeholder="nom" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="prenom">Prénom : </label>  
  <div class="col-md-4">
  <input id="prenom" name="prenom" type="text" placeholder="prénom" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="adresse">Adresse :</label>  
  <div class="col-md-4">
  <input id="adresse" name="adresse" type="text" placeholder="adresse" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="ville">Ville :</label>  
  <div class="col-md-4">
  <input id="ville" name="ville" type="text" placeholder="ville" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-2 control-label" for="codepostal">Code Postal :</label>  
  <div class="col-md-2">
  <input id="codepostal" name="codepostal" type="text" placeholder="code postal" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Multiple Radios (inline) -->
<div class="form-group">
  <label class="col-md-2 control-label" for="sexe">Sexe :</label>
  <div class="col-md-4"> 
    <label class="radio-inline" for="sexe-0">
      <input type="radio" name="sexe" id="sexe-0" value="1" checked="checked">
      M
    </label> 
    <label class="radio-inline" for="sexe-1">
      <input type="radio" name="sexe" id="sexe-1" value="2">
      F
    </label>
  </div>
</div>

<!-- Textarea -->
<div class="form-group">
  <label class="col-md-2 control-label" for="notes">Notes</label>
  <div class="col-md-4">                     
    <textarea class="form-control" id="notes" name="notes"></textarea>
  </div>
</div>

<!-- Button (Double) -->
<div class="form-group">
  <label class="col-md-2 control-label" for="savenew"></label>
  <div class="col-md-8">
     <button id="save" name="save" class="btn btn-primary">Enregistrer</button>
    <button id="savenew" name="savenew" class="btn btn-success">Enregistrer et nouveau</button>
    <button id="annuler" name="annuler" class="btn btn-danger">Annuler</button>
  </div>
</div>

</fieldset>
</form>

</html>
