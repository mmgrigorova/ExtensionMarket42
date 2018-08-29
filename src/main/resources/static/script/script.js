$(document).ready(function () {
    console.log("script file loaded");
    $('.nav li').removeClass('active');
    var menuId = $('h1').attr('id');
    $('#' + menuId).addClass("active");
    console.log("doing something");

    $('#textarea')
        .textext({
            plugins: 'autocomplete tags ajax',
            prompt: 'Start adding tags...',
            ajax: {
                url: 'http://localhost:8080/api/tags',
                dataType: 'json',
                cacheResults: true
            }
        });
});