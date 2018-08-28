$(document).ready(function () {
        console.log("script file loaded");
        $('.nav li').removeClass('active');
        var menuId = $('h1').attr('id');
        $('#' + menuId).addClass("active");
        console.log("doing something");
    }
);