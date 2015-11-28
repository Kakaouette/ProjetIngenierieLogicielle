<%-- 
    Document   : ajoutJustificatifDansAttribut
    Created on : 24 nov. 2015, 15:25:05
    Author     : Arthur
--%>

<%@page import="modele.entite.Justificatif"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<form action="Navigation?action=ajouterJustificatifDansAttribut" method="POST" class="form-horizontal">
    <div class="form-group">
        <label for="justificatifs" class="col-sm-2 control-label">Justificatifs</label>
        <div class="col-sm-3">
            <!-- A faire : positionner le type actuel en selection par défaut -->
            <select name="justificatifAAjouter" id="type" class="form-control">
                <% List<Justificatif> justificatifs=(List<Justificatif>) request.getAttribute("tousJustificatifs");
                   for (Justificatif justificatif : justificatifs){
                %>
                <option><%out.print(justificatif.getTitre());%></option>
                <% }%>
            </select>
        </div>
    </div>
    
    <div class="row">
        <div class="col-md-2 col-md-offset-2">
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer">Ajouter</button>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <a class="btn btn-default" href="Navigation?action=voirAjoutFormation">Annuler</a>
        </div>
    </div>
</form>      
            
<%@include file="Modele/pied.jsp" %>
