<%-- 
    Document   : infoEtudiant
    Created on : 11 déc. 2015, 10:08:11
    Author     : Etienne
--%>

<%@page import="modele.entite.EtudiantEtranger"%>
<%@page import="modele.dao.EtudiantDAO"%>
<%@page import="modele.entite.Etudiant"%>
<%@page contentType="application/json; charset=UTF-8"%>

<%
    Etudiant etu = (Etudiant) request.getAttribute("etudiant");
    String json = "{";
    json+="\"nom\":"+"\""+etu.getNom()+"\""+",";
    json+="\"prenom\":"+"\""+etu.getPrenom()+"\""+",";
    json+="\"sexe\":"+"\""+etu.getSexe()+"\""+",";
    json+="\"adressePostale\":"+"\""+etu.getAdressePostale()+"\""+",";
    json+="\"codePostal\":"+"\""+etu.getAdresse().getCodePostal()+"\""+",";
    json+="\"ville\":"+"\""+etu.getAdresse().getVille()+"\""+",";
    if(etu instanceof EtudiantEtranger)
    {
        EtudiantEtranger etudiantEtranger = (EtudiantEtranger) etu;
        json+="\"avis\":"+"\""+etudiantEtranger.getAvis()+"\""+",";
        json+="\"niveau\":"+"\""+etudiantEtranger.getNiveau()+"\""+",";
        json+="\"nationalite\":"+"\""+"etranger"+"\""+",";
    } else {
        json+="\"nationalite\":"+"\""+"francais"+"\""+",";
        
    }
    json+="\"pays\":"+"\""+etu.getPays()+"\""+"}";
    
    out.print(json);
%>
