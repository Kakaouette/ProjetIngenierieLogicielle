/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    
    $("#display").change(function(){
        $("#formation select").attr("size",$(this).val());
    });
    
    $("#recherche").keyup(function(){
        var filter = $(this).val();
        var i = 1;
        var options=$("#formation select>option");
        options.each(function(){
            if ($(this).text().indexOf(filter) !== -1) {
                $(this).show();
                i++;
            } else {
                $(this).hide();
            }
        });
    });
    
});
