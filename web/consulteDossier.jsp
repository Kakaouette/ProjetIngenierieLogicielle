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
<script src="jQuery/jquery-ui-1.9.2.custom.min.js"></script>
<link rel="stylesheet" href="bootstrap/jquery-custom/jquery-ui-1.10.0.custom.css">
<%
            Dossier d = (Dossier) request.getAttribute("dossier");
        %>
<script type="text/javascript">
    $(function() {
        $('#dialog').hide();
    });

    function createDialog(id) {
        $('#dialog').dialog({
            modal: true,
            buttons: {
                "Oui": {
                    text : 'Oui' ,class : 'btn btn-success', click : function() {
                    window.location.replace('Navigation?action=supprimerDossier&idDossier=' + id);
                }
                },
                "Non": {text : 'Non' ,class : 'btn btn-danger', click : function() {
                    $(this).dialog("close");}
                }
            }
        });
        $('#dialog').show();
    };
</script>
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
            <a class="btn btn-danger" onclick="createDialog('<% out.print(d.getId()); %>')">Supprimer</a>
        </div>
        <div class="col-md-2 col-md-offset-1">
            <!--[if IE]>
            <input type="hidden" name="action" value="annulerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <a class="btn btn-default" href="Navigation?action=afficherInformationsDossiers">Annuler</a>
        </div>
        <div class="col-md-2 col-md-offset-1">
            <!--[if IE]>
            <input type="hidden" name="action" value="annulerModifs" />
            <button class="btn btn-lg btn-success btn-block" type="submit" name="change" id="change">Connexion</button>
            <![endif]-->
            <!--[if !IE]><!-->
            <%
                if(d.getEtat().equals(TypeEtatDossier.transfert_vers_secretariat))
                {
            %>
                <a class="btn btn-default" href="Navigation?action=genererLettre&idDossier=<%out.print(d.getId()); %>&typeLettre=1">Generer accuse de reception</a>
            <%
                }
                else if(d.getEtat().equals(TypeEtatDossier.en_attente_transfert_vers_directeur))
                {
                    //En attente de l'ajout d'un champs dans la table dossier pour l'avis du directeur
                    /*if()
                    {
                        <a class="btn btn-default" href="Navigation?action=genererLettre&idDossier=d.getId(); &typeLettre=2">Generer lettre d'acceptation</a>
                    }
                    else if()
                    {
                        <a class="btn btn-default" href="Navigation?action=genererLettre&idDossier=d.getId(); &typeLettre=3">Generer lettre de refus</a>
                    }*/
            %>
            <%
                }
                else if(d.getEtat().equals(TypeEtatDossier.retour_ver_secretariat) && d.getAdmissible().equals(TypeDossier.admissibilite))
                {
            %>
                    <a class="btn btn-default" href="Navigation?action=genererLettre&idDossier=<%d.getId(); %>&typeLettre=4">Generer lettre d'audition</a>
            <%
                }
            %>
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
<div id="dialog" title="Confirmer la suppression">
    <p>Voulez vous vraiment supprimer ce dossier ?</p>
</div>
