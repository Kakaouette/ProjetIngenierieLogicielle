<%@page import="java.util.List"%>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.7/js/jquery.dataTables.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
<script src="https://cdn.datatables.net/plug-ins/1.10.7/sorting/datetime-moment.js"></script>
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css">
<script src="https://cdn.datatables.net/tabletools/2.2.3/js/dataTables.tableTools.min.js"></script>

<!--[if gte IE 9]><script src="jQuery/dataTables.colVis.js"></script><![endif]-->
<!--[if !IE]>--><script src="jQuery/dataTables.colVis.js"></script><![endif]-->

<link rel="stylesheet" href="jQuery/dataTables.colVis.css">
<script src="https://cdn.datatables.net/responsive/1.0.6/js/dataTables.responsive.min.js"></script>
<link rel="stylesheet" href="jQuery/dataTables.responsive.css">

<%int taille = 0;
    List< Object[]> leTableau = (List< Object[]>) request.getAttribute("leTableau");
    if (leTableau != null) {
        if (leTableau.size() > 50000) {
            taille = 50000;
            out.print("<div class=\"alert alert-danger\">"
                    + "Il y a plus de 50 000 lignes à afficher, affinez la recherche"
                    + "</div>");
        } else {
            taille = leTableau.size();
        }%>
<br>
<script type="text/javascript" class="init">
    $(document).ready(function() {
        $.fn.dataTable.moment('DD/MM/YYYY à HH:mm:ss');
        var table = $('#myTable').dataTable({
        "language": {
        "url": "https://cdn.datatables.net/plug-ins/1.10.7/i18n/French.json"
        },
                "aLengthMenu": [[10, 25, 50, 100, - 1], [10, 25, 50, 100, "Toutes"]],
                "order": ['<%= request.getAttribute("sortL")%>', '<%= request.getAttribute("sortC")%>'],
                "fnInitComplete": function() {
                    this.css("visibility", "visible");
                },
                dom: '<"row"<"col-sm-6"C>\n\
                        <"col-sm-6"T>><"clear"><"row"<"col-sm-6"l><"col-sm-6"f>>\n\
                        rt<"row"<"col-sm-6"i><"col-sm-6"p>>',
                "colVis": {
                "buttonText": "Afficher/cacher des colonnes <span class=\"caret\"></span>"
                },
                "tableTools": {
                "sSwfPath": "//cdn.datatables.net/tabletools/2.2.3/swf/copy_csv_xls_pdf.swf",
                        "aButtons": [
                        ]
                },
                "aaData": [
    <%
        for (int j = 0; j < taille; j++) {%>
                [
    <%
        Object[] oTab = (Object[]) leTableau.get(j);
        for (int i = 0; i < oTab.length; i++) {%>
                "<%out.print(oTab[i]);%>"
    <%if (i != oTab.length - 1) {
            out.print(",");
        }%>
    <%}%>]
    <%if (j != taille - 1) {
            out.print(",");
        }%>
    <%}%>
                ]
    });
    $.extend(true, $.fn.DataTable.TableTools.classes, {
        "container": "btn-group btn-group DTTT",
        "buttons": {
            "normal": "btn btn-default btn",
            "disabled": "disabled"
        },
        "collection": {
            "container": "DTTT_dropdown dropdown-menu dropdown-toggle",
            "buttons": {
                "normal": "",
                "disabled": "disabled"
            }
        },
        "select": {
            "row": "active"
        }
    });
    });
</script>
<%}%>