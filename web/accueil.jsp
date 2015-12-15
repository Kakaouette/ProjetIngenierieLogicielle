<%-- 
    Document   : accueil
    Created on : 4 nov. 2015, 19:05:02
    Author     : nicol
--%>

<%@page import="modele.dao.DossierDAO"%>
<%@page import="modele.entite.TypeEtatDossier"%>
<%@page import="modele.dao.FormationDAO"%>
<%@page import="modele.entite.Formation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<% DossierDAO dao_dossier=new DossierDAO(); %>
<link href="jQuery/morris.css" rel="stylesheet">
<script src="jQuery/morris.min.js"></script>
<script src="jQuery/raphael.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        Morris.Donut({
        element:'etat_dossier',
        data:[
            <%
            TypeEtatDossier[] etats_dossier = TypeEtatDossier.values();       
            for(int i=0;i<etats_dossier.length;i++){%>
                    {label:"<%out.print(etats_dossier[i].toString());%>", value:<%out.print(dao_dossier.count(etats_dossier[i]));%>}
                    <%if(i<etats_dossier.length-1){%>,<%}%>
                <%}%>  
            ]
        });
    });
</script>
        <p>Bonjour
            <%out.print(c.getPrenom());%> <%out.print(c.getNom());%>, vous êtes de type <%out.print(c.getType().toString());%></p>
        
        <%-- FORMATIONS --%>
        <%if(c.getType()==TypeCompte.responsable_commission || c.getType()==TypeCompte.secrétaire_formation || c.getType()==TypeCompte.responsable_commission){%>
        <%if(!c.getFormationAssocie().isEmpty()){%>
        <h2>Liste formations</h2>
        <%for(Formation f : c.getFormationAssocie()){%>
            <p><%out.print(f.getIntitule());%> Début : <%out.print(f.getDebut());%> - Fin : <%out.print(f.getFin());%></p>
        <%}}}%>
        
        <div id="etat_dossier"></div>
<%@include file="Modele/pied.jsp" %>
