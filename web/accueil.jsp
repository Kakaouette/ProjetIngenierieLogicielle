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
<% 
    DossierDAO dao_dossier=new DossierDAO(); 
    FormationDAO dao_formation=new FormationDAO();
    TypeEtatDossier[] etats_dossier = TypeEtatDossier.values();
    List<Formation> formations=new FormationDAO().SelectAll();
%>
<link href="jQuery/morris.css" rel="stylesheet">
<script src="jQuery/morris.min.js"></script>
<script src="jQuery/raphael.js"></script>
<script type="text/javascript">
    var total_dossier=<%out.print(dao_dossier.count());%>;
    
    function camembertGeneral(){
        $("#etat_dossier").html('<p style=\"text-align:center;font-weight:bold;\"></p>');
        //camenbert nombre de dossiers par état
        Morris.Donut({
        element:'etat_dossier',
        data:[
            <%  
            for(int i=0;i<etats_dossier.length;i++){%>
                    {label:"<%out.print(etats_dossier[i].toString());%>", value:<%out.print(dao_dossier.count(etats_dossier[i]));%>}
                    <%if(i<etats_dossier.length-1){%>,<%}%>
                <%}%>  
            ],
        colors:['#FA7827','#F8BF3B','#D083f1','#F87878','#5691DB','#4CC5FE','#B2B2B2','#2BBB66'],
        formatter:function(y){if(y>1){return y+" dossiers - "+Math.round(((y/total_dossier)*100)*100)/100+"%";}else{return y+" dossier - "+Math.round(((y/total_dossier)*100)*100)/100+"%";}}
        });
        $("#etat_dossier>p").html("Nombre de dossiers par état");
    }
    
    $(document).ready(function(){
        
        camembertGeneral();
        
        Morris.Bar({
            element:'dossier_formation',
            data:[
                <%
                for(int i=0;i<formations.size();i++){%>
                        {x:"<%out.print(formations.get(i).getIntitule());%>", 
                            number:<%out.print(dao_dossier.count(formations.get(i)));%>,
                            <%for(int j=0;j<etats_dossier.length;j++){%>
                                <%out.print(etats_dossier[j].name());%>:<%out.print(dao_dossier.count(formations.get(i),etats_dossier[j]));%>
                                    <%if(j<etats_dossier.length-1){%>,<%}%>
                            <%}%>
                            }
                        <%if(i<formations.size()-1){%>,<%}%>
                    <%}%>  
            ],
            xkey:'x',
            ykeys:['number'],
            hideHover:'auto',
            labels:['Nombre de dossiers'],
            hoverCallback:function(index,options,content,row){
                var chaine = "<p><strong>"+row.x+"</strong></p><p>Nombre de dossiers : "+row.number+" - ("+Math.round(((row.number/total_dossier)*100)*100)/100+"%)</p>";
                chaine+="<br/>";
                chaine+="<p>Transfert vers le secrétariat : "+row.transfert_vers_secretariat+" - ("+Math.round(((row.transfert_vers_secretariat/row.number)*100)*100)/100+"%)</p>";
                chaine+="<p>Traité par le secrétariat : "+row.traité_secretariat_formation+" - ("+Math.round(((row.traité_secretariat_formation/row.number)*100)*100)/100+"%)</p>";
                chaine+="<p>Attente de la commission : "+row.en_attente_commission+" - ("+Math.round(((row.en_attente_commission/row.number)*100)*100)/100+"%)</p>";
                chaine+="<p>En attente de transfert vers le directeur : "+row.en_attente_transfert_vers_directeur+" - ("+Math.round(((row.en_attente_transfert_vers_directeur/row.number)*100)*100)/100+"%)</p>";
                chaine+="<p>Transfert vers le directeur : "+row.en_transfert_vers_directeur+" - ("+Math.round(((row.en_transfert_vers_directeur/row.number)*100)*100)/100+"%)</p>";
                chaine+="<p>Retour vers le secrétariat : "+row.retour_vers_secretariat+" - ("+Math.round(((row.retour_vers_secretariat/row.number)*100)*100)/100+"%)</p>";
                chaine+="<p>Navette : "+row.navette+" - ("+Math.round(((row.navette/row.number)*100)*100)/100+"%)</p>";
                chaine+="<p>Terminé : "+row.terminé+" - ("+Math.round(((row.terminé/row.number)*100)*100)/100+"%)</p>";
                chaine+="<script type=\"text/javascript\">";
                chaine+="$(\"#etat_dossier\").html('<p style=\"text-align:center;font-weight:bold;\"></p>');";
                chaine+="Morris.Donut({";
                chaine+="element:'etat_dossier',";
                chaine+="data:[{label:'Transfert vers le secrétariat',value:"+row.transfert_vers_secretariat+"},";
                chaine+="{label:'Traité par le secrétariat',value:"+row.traité_secretariat_formation+"},";
                chaine+="{label:'Attente de la commission',value:"+row.en_attente_commission+"},";
                chaine+="{label:'En attente de transfert vers le directeur',value:"+row.en_attente_transfert_vers_directeur+"},";
                chaine+="{label:'Transfert vers le directeur',value:"+row.en_transfert_vers_directeur+"},";
                chaine+="{label:'Retour vers le secrétariat',value:"+row.retour_vers_secretariat+"},";
                chaine+="{label:'Navette',value:"+row.navette+"},";
                chaine+="{label:'Terminé',value:"+row.terminé+"}";
                chaine+="],";
                chaine+="colors:['#FA7827','#F8BF3B','#D083f1','#F87878','#5691DB','#4CC5FE','#B2B2B2','#2BBB66'],";
                chaine+="formatter:function(y){if(y>1){return y+\" dossiers - \"+Math.round(((y/total_dossier)*100)*100)/100+\"%\";}else{return y+\" dossier - \"+Math.round(((y/total_dossier)*100)*100)/100+\"%\";}}});";
                chaine+="$(\"#etat_dossier>p\").html(\"Nombre de dossiers par état pour "+row.x+"\");</script";
                chaine+=">";
                return chaine;
            }
        });
        $("#dossier_formation>p").html("Nombre de dossiers par formation");
        
        $("#dossier_formation").on("mouseleave",function(){
            camembertGeneral();
        });
    });
</script>
        <div class="row">
        <%-- AFFICHAGE DE TOUS LES DOSSIERS PAR ETAT (admin, directeur, secrétaire générale et responsable administratif) --%>
        <div class="col-sm-4">
        <%if(c.getType()==TypeCompte.admin||c.getType()==TypeCompte.directeur_pole||c.getType()==TypeCompte.responsable_administrative||c.getType()==TypeCompte.secrétaire_inscription){%>
        <div id="etat_dossier"><p style="text-align:center;font-weight:bold;"></p></div>
        <%}%>
        </div>
        
        <div class="col-sm-8">
        <%-- AFFICHAGE DE TOUS LES DOSSIERS PAR FORMATION (admin, direceteur, secrétaire générale et responsable administratif) --%>
        <%if(c.getType()==TypeCompte.admin||c.getType()==TypeCompte.directeur_pole||c.getType()==TypeCompte.responsable_administrative||c.getType()==TypeCompte.secrétaire_inscription){%>
        <div id="dossier_formation"><p style="text-align:center;font-weight:bold;"></p></div>
        <%}%>
        </div>
        
        </div>
<%@include file="Modele/pied.jsp" %>
