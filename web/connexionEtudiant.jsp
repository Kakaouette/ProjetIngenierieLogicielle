<%-- 
    Document   : connexionEtudiant
    Created on : 14/12/2015
    Author     : Val
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu_etudiant.jsp" %>

<%if(request.getAttribute("focus") != null){%>
<script type="text/javascript">
    window.onload=function(){
        document.getElementById("<%out.print(request.getAttribute("focus"));%>").focus();
    };
</script>
<%}%>

<form class="form-signin" action="Etudiant" method="POST">
    <label for="INE" class="sr-only">INE</label>
    <div class="input-group">
        <span class="input-group-addon" id="Etudiant"><label>INE</label></span>
        <input type="text" name="INE" id="INE" class="form-control" placeholder="Numéro INE" required autofocus>
    </div>
    <label for="nom" class="sr-only">Nom</label>
    <div class="input-group">
        <span class="input-group-addon" id="Etudiant"><label>Nom</label></span>
        <input type="text" name="nom" id="nom" class="form-control" placeholder="Nom" required>
    </div>
    <label for="prenom" class="sr-only">Prénom</label>
    <div class="input-group" id="divMargin">
        <span class="input-group-addon" id="Etudiant"><label>Prénom</label></span>
        <input type="text" name="prenom" id="prenom" class="form-control" placeholder="Prénom" required>
    </div>
    <button class="btn btn-lg btn-success btn-block" type="submit" name="action" value="connexionEtudiant">Connexion</button>
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