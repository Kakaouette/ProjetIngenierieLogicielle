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
        colors:['#FA7827','#F8BF3B','#D083f1','#F87878','#5691DB','#4CC5FE','#B2B2B2','#2BBB66'],
        formatter:function(y){if(y>1){return y+" dossiers - "+(y/total_dossier)*100+"%";}else{return y+" dossier - "+(y/total_dossier)*100+"%";}}
        });
        $("#etat_dossier>p").html("Nombre de dossiers par état");
        
        Morris.Donut({
            element:'dossier_formation',
            data:[
                <%
                List<Formation> formations=new FormationDAO().SelectAll();
                for(int i=0;i<formations.size();i++){%>
                        {label:"<%out.print(formations.get(i).getIntitule());%>", value:<%out.print(dao_dossier.count(formations.get(i)));%>}
                        <%if(i<formations.size()-1){%>,<%}%>
                    <%}%>  
            ],
            formatter:function(y){if(y>1){return y+" dossiers - "+(y/total_dossier)*100+"%";}else{return y+" dossier - "+(y/total_dossier)*100+"%";}}
        });
        $("#dossier_formation>p").html("Nombre de dossiers par formation");
    });
</script>
        <div class="row">
        <%-- AFFICHAGE DE TOUS LES DOSSIERS PAR ETAT (admin, directeur, secrétaire générale et responsable administratif) --%>
        <div class="col-sm-6">
        <%if(c.getType()==TypeCompte.admin||c.getType()==TypeCompte.directeur_pole||c.getType()==TypeCompte.responsable_administrative||c.getType()==TypeCompte.secrétaire_inscription){%>
        <div id="etat_dossier"><p style="text-align:center;font-weight:bold;"></p></div>
        <%}%>
        </div>
        
        <div class="col-sm-6">
        <%-- AFFICHAGE DE TOUS LES DOSSIERS PAR FORMATION (admin, direceteur, secrétaire générale et responsable administratif) --%>
        <%if(c.getType()==TypeCompte.admin||c.getType()==TypeCompte.directeur_pole||c.getType()==TypeCompte.responsable_administrative||c.getType()==TypeCompte.secrétaire_inscription){%>
        <div id="dossier_formation"><p style="text-align:center;font-weight:bold;"></p></div>
        <%}%>
        </div>
        
        </div>
<%@include file="Modele/pied.jsp" %>
