<%-- 
    Document   : modifierDossier
    Created on : 22 nov. 2015, 10:53:41
    Author     : phanjoseph
--%>

<%@page import="modele.entite.Dossier"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu.jsp" %>
<form action="Navigation?action=modifierDossier" method="POST" class="form-horizontal">
    <!--<div class="form-group">
        <label for="type" class="col-sm-2 control-label">Type</label>
        <div class="col-sm-3">-->
            <!-- A faire : positionner le type actuel en selection par défaut -->
            <!--<select name="type" id="type" class="form-control">
                <option>admin</option>
                <option>directeur_pole</option>
                <option>secretaire_general</option>
                <option>secretaire_formation</option>
                <option>commission</option>
            </select>
        </div>
    </div>-->
        <%
            Dossier d = (Dossier) request.getAttribute("dossier");
        %>
        
    <fieldset>
        <legend>Informations postulant</legend>
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Numéro du dossier</label>
        <div class="col-sm-3">
            <input type="text" name="id" id="login" class="form-control" value="<%out.print(d.getId());%>" required disabled>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Nom du postulant</label>
        <div class="col-sm-3">
            <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getNom());%>" required disabled>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Prénom du postulant</label>
        <div class="col-sm-3">
            <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getPrenom());%>" required disabled>
        </div>
    </div>
        
    <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Sexe</label>
            <div class="col-sm-3">
                <%
                    if(d.getEtudiant().getSexe().contains("M"))
                    {
                        out.print("<input type=\"radio\" name=\"sexe\" value=\"male\" checked disabled> Homme<br>");
                        out.print("<input type=\"radio\" name=\"sexe\" value=\"female\" disabled> Femme");
                    }
                    else
                    {
                        out.print("<input type=\"radio\" name=\"sexe\" value=\"male\" disabled> Homme<br>");
                        out.print("<input type=\"radio\" name=\"sexe\" value=\"female\" checked disabled> Femme");
                    }
                    %>
                
            </div>
    </div>
    </fieldset>
        
    <fieldset>
        <legend>Adresse</legend>
        <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Rue</label>
            <div class="col-sm-3">
                <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getAdressePostale());%>" required disabled>
            </div>
        </div>
            
        <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Code Postal</label>
            <div class="col-sm-3">
                <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getAdresse().getCodePostal());%>" required disabled>
            </div>
        </div>
            
        <div class="form-group">
            <label for="login" class="col-sm-2 control-label">Ville</label>
            <div class="col-sm-3">
                <input type="text" name="login" id="login" class="form-control" value="<%out.print(d.getEtudiant().getAdresse().getVille());%>" required disabled>
            </div>
        </div>
            
    </fieldset>
            
        
    <fieldset>
        <legend>Informations dossier</legend>
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Crée le </label>
        <div class="col-sm-3">
            <input type="text" name="date" id="login" class="form-control" value="<%out.print(d.getDate());%>" required>
        </div>
    </div>
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">État du dossier </label>
        <div class="col-sm-3"> 
           <input type="text" name="etat" id="login" class="form-control" value="<%out.print(d.getEtat());%>" required>
        </div>
    </div>
       
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Admissibilité </label>
        <div class="col-sm-3">
            <%
                if(d.isAdmissible())
                {
                    out.print("<INPUT type=\"checkbox\" name=\"admissibilite\" value=\"true\" checked>");
                }
                else
                {
                    out.print("<INPUT type=\"checkbox\" name=\"admissibilite\" value=\"false\">");
                }
            %>
            
        </div>
    </div>
        
    <!--<div class="form-group">
        <label for="login" class="col-sm-2 control-label">Notes </label>
        <div class="col-sm-3">
            <textarea rows="4" cols="50" name="comment"></textarea>
        </div>
    </div>-->
        
    <div class="form-group">
        <label for="login" class="col-sm-2 control-label">Historique </label>
        <div class="col-sm-4">
            <table class="table table-hover">
                <thead
                    <tr>
                      <td>Nom</td>
                      <td>Date</td> 
                      <td>Modification</td>
                    </tr>
                </thead>
                <tbody
            <%  
            if(d.getHistorique().size()>0)
            {
                for(int i = 0 ; i < d.getHistorique().size() ; i++)
                {
                    String nom = d.getHistorique().get(i).getCompte().getNom();
                    String date = String.valueOf(d.getHistorique().get(i).getDate());
                    String modification = d.getHistorique().get(i).getMessage();
                    out.print("<tr><td>" + nom + "</td><td>"+ date +"</td><td>"+ modification+"</td></tr>");
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
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="enregistrerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <button class="btn btn-success" type="submit" name="bouton" id="bouton" value="enregistrer">Enregister</button>
            <!--<![endif]-->
        </div>
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="annulerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-primary" href="Navigation?action=afficherInformationsUtilisateur">Annuler</a>
        </div>
        <div class="col-md-2 col-md-offset-2">
            <!--[if IE]>
            <input type="hidden" name="action" value="supprUtilisateur" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-primary" href="Navigation?action=afficherInformationsUtilisateur">Supprimer</a>
        </div>
    </div>
</form>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-danger">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
<%@include file="Modele/pied.jsp" %>
