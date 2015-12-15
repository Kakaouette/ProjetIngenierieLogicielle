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
        
        var total_dossier=<%out.print(dao_dossier.count());%>;
        
        //camenbert nombre de dossiers par état
        Morris.Donut({
        element:'etat_dossier',
        data:[
            <%
            TypeEtatDossier[] etats_dossier = TypeEtatDossier.values();       
            for(int i=0;i<etats_dossier.length;i++){%>
                    {label:"<%out.print(etats_dossier[i].toString());%>", value:<%out.print(dao_dossier.count(etats_dossier[i]));%>}
                    <%if(i<etats_dossier.length-1){%>,<%}%>
                <%}%>  
            ],
        colors:['orange','yellow','purple','teal','blue','cyan','grey','green'],
        formatter:function(y){if(y>1){return y+" dossiers - "+(y/total_dossier)*100+"%";}else{return y+" dossier - "+(y/total_dossier)*100+"%";}}
        });
        $("#etat_dossier>p").html("Nombre de dossiers par état");
        
        //bar graph nombre de dossier par état pour une formation
        $("#formation").change(function(){
                <% Formation f=new FormationDAO().getFormationByIntitule(%>$(this).val();<%);%>
            Morris.Donut({
                element:'etat_formation',
                data:[
                    <%      
                    for(int i=0;i<etats_dossier.length;i++){%>
                            {label:"<%out.print(etats_dossier[i].toString());%>", value:<%out.print(dao_dossier.count(etats_dossier[i]));%>}
                            <%if(i<etats_dossier.length-1){%>,<%}%>
                        <%}%>  
                ],
            });
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
        
        <%-- AFFICHAGE DE TOUS LES DOSSIERS PAR ETAT (admin, directeur, secrétaire générale et responsable administratif) --%>
        <%if(c.getType()==TypeCompte.admin||c.getType()==TypeCompte.directeur_pole||c.getType()==TypeCompte.responsable_administrative||c.getType()==TypeCompte.secrétaire_inscription){%>
        <div id="etat_dossier"><p style="text-align:center;font-weight:bold;"></p></div>
        <%}%>
        
        <%-- AFFICHAGE DE TOUS LES DOSSIERS PAR FORMATION ET PAR ETAT (admin, direceteur, secrétaire générale et responsable administratif) --%>
        <%if(c.getType()==TypeCompte.admin||c.getType()==TypeCompte.directeur_pole||c.getType()==TypeCompte.responsable_administrative||c.getType()==TypeCompte.secrétaire_inscription){%>
            <select id="formation">
                <%
                    for(Formation f:new FormationDAO().SelectAll()){
                        out.print("<option value="+f.getIntitule()+">"+f.getIntitule()+"</option>");
                    }
                %>
            </select>
            <div id="etat_formation"><p style="text-align:center;font-weight:bold;"></p></div>
        <%}%>
            
        <%-- AFFICHAGE DE TOUS LES DOSSIERS PAR FORMATION ET PAR ETAT (responsable commission, secrétaire formation et responsable formation) --%>
        <%if(c.getType()==TypeCompte.responsable_commission||c.getType()==TypeCompte.responsable_formation||c.getType()==TypeCompte.secrétaire_formation){%>
            <select id="formation">
                <%
                    for(Formation f:c.getFormationAssocie()){
                        out.print("<option value="+f.getIntitule()+">"+f.getIntitule()+"</option>");
                    }
                %>
            </select>
            <div id="etat_formation"><p style="text-align:center;font-weight:bold;"></p></div>
        <%}%>
<%@include file="Modele/pied.jsp" %>
