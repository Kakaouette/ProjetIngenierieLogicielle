<%-- 
    Document   : modifierDossier
    Created on : 22 nov. 2015, 10:42:42
    Author     : phanjoseph
--%>

<%@page import="modele.entite.TypeEtatDossier"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="modele.entite.TypeDossier"%>
<%@page import="modele.entite.Dossier"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
<%
            Dossier d = (Dossier) request.getAttribute("dossier");
        %>
<form action="<%out.print("Navigation?action=modifierDossier&idDossier="+d.getId());%>" method="POST" class="form-horizontal">        
        
    <fieldset>
        <legend>Informations postulant</legend>
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Numéro du dossier</label>
        <div class="col-sm-3">
            <input type="text" name="id" id="login" class="form-control" value="<%out.print(d.getId());%>" required readonly>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Nom du postulant</label>
        <div class="col-sm-3">
            <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getNom());%>" required readonly>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Prénom du postulant</label>
        <div class="col-sm-3">
            <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getPrenom());%>" required readonly>
        </div>
    </div>
        
    <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Sexe</label>
            <div class="col-sm-3">
                <input type="radio" name="sexe" value="male" <%if(d.getEtudiant().getSexe().contains("M")){%>checked<%}%> disabled> Homme<br>
                <input type="radio" name="sexe" value="female" <%if(!d.getEtudiant().getSexe().contains("M")){%>checked<%}%> disabled> Femme
            </div>
    </div>
    </fieldset>
        
    <fieldset>
        <legend>Adresse</legend>
        <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Rue</label>
            <div class="col-sm-3">
                <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getAdressePostale());%>" required readonly>
            </div>
        </div>
            
        <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Code Postal</label>
            <div class="col-sm-3">
                <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getAdresse().getCodePostal());%>" required readonly>
            </div>
        </div>
            
        <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Ville</label>
            <div class="col-sm-3">
                <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getAdresse().getVille());%>" required readonly>
            </div>
        </div>
            
    </fieldset>
            
        
    <fieldset>
        <legend>Informations dossier</legend>
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Créé le </label>
        <div class="col-sm-3">
            <input type="text" name="date" id="login" class="form-control" value="<%SimpleDateFormat formater = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE); out.print(formater.format(d.getDate()));%>" required readonly>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">État du dossier </label>
        <div class="col-sm-3"> 
            <select name="etat" class="form-control">
                <%for(TypeEtatDossier etat : TypeEtatDossier.values())
                {%>
                <option value="<% out.print(etat.name());%>" <% if(d.getEtat() == etat){%> selected="true" <%}%>><%out.print(etat.toString());%></option>
                <%}%>
            
                
            </select>
        </div>
    </div>
       
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Type </label>
        <div class="col-sm-3">
            <label><input type="radio" name="admissibilite" value="<% TypeDossier.inscription.name(); %>" <%if(d.getAdmissible() == TypeDossier.inscription){%>checked<%};%> disabled> Inscription</label>
            <label><input type="radio" name="admissibilite" value="<% TypeDossier.admissibilite.name(); %>" <%if(d.getAdmissible() == TypeDossier.admissibilite){%>checked<%};%> disabled> Admission</label>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Notes </label>
        <div class="col-sm-3">
            <textarea rows="4" cols="50" name="msg_histo" maxlength="255"></textarea>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Historique </label>
        <div class="col-sm-offset-2">
            <table class="table table-hover">
                <thead
                    <tr>
                      <td>Nom</td>
                      <td>Date</td> 
                      <td>Modification</td>
                      <td>Action</td>
                    </tr>
                </thead>
                <tbody>
            <%  
            if(d.getHistorique().size()>0)
            {
                int max=0;
                if(d.getHistorique().size() > 3)
                {
                    max = 3;
                }else{
                    max = d.getHistorique().size();
                }
                for(int i = 1 ; i <= max ; i++)
                {
                    int size = d.getHistorique().size();
                    String nom = d.getHistorique().get(size-i).getCompte().getNom();
                    String date = formater.format(d.getHistorique().get(size-i).getDate());
                    String modification = d.getHistorique().get(size-i).getMessage();
                    String action = d.getHistorique().get(size-i).getAction();
                    out.print("<tr><td>" + nom + "</td><td>"+ date +"</td><td>"+ modification+"</td><td>"+action+"</td></tr>");
                }
            }
            else
            {
                out.print("<tr><td></td><td>Aucune donnée</td><td></td></tr>");
            }
            %>
            </tbody>
            </table>
        </div>
    </div>
    </fieldset>
            
            
            
    
        
    <div class="row">
        <div class="col-md-1 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="enregistrerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer">Enregister</button>
            <!--<![endif]-->
        </div>
        <div class="col-md-1">
            <!--[if IE]>
            <input type="hidden" name="action" value="supprDossier" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-danger" href="<%out.print("Navigation?action=supprimerDossier&idDossier="+d.getId());%>">Supprimer</a>
        </div>
        <div class="col-md-2 col-md-offset-1">
            <!--[if IE]>
            <input type="hidden" name="action" value="annulerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-default" href="Navigation?action=afficherInformationsDossiers">Annuler</a>
        </div>
    </div>
</form>

<% if(request.getAttribute("messageError") != null){ %>
<br>
    <div class="alert alert-danger">
        <%out.print(request.getAttribute("messageError"));%>
    </div>
<%}%>
<% if(request.getAttribute("message") != null){ %>
<br>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
<%@include file="Modele/pied.jsp" %>
