$(document).ready(function () {
    console.log("script file loaded");
    $('.nav li').removeClass('active');
    var menuId = $('h1').attr('id');
    $('#' + menuId).addClass("active");

    $('#close-report').on('click', function () {
        $('.accordion').hide();
        $('#close-report').hide();
    });
    console.log("doing something");
});