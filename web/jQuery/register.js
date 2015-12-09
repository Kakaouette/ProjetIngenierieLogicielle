/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    
    $("#formation").hide();
    $("#display").attr("max",$("#formation option").length);
    $("#formation select>option:first-child").attr("selected",true);
    $("#formation select>option:nth-child(n+2)").each(function(){
       $(this).attr("selected",false); 
    });
    
    $("#display").change(function(){
        $("#formation select").attr("size",$(this).val());
    });
    
    $("#type").change(function(){
       if($(this).val()==="secrétaire_formation"||$(this).val()==="responsable_commission"||$(this).val()==="responsable_formation"){
            $("#formation").show();
       } 
       else{
           $("#formation").hide();
       }
    });
    
    $("#recherche").keyup(function(){
        var filter = $(this).val();
        var i = 1;
        var options=$("#formation select>option");
        options.each(function(){
            if ($(this).text().search(new RegExp(filter,"i")) !== -1) {
                $(this).show();
                i++;
            } else {
                $(this).hide();
            }
        });
    });
    
    $("#formation select>option:first-child").click(function(){
       $("#formation select>option:nth-child(n+2)").each(function(){
           $(this).attr("selected",false);
       });
    });
    
    $("#formation select>option:nth-child(n+2)").click(function(){
           $("#formation select>option:first-child").attr("selected",false);
     });
    
});
