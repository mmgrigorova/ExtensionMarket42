$(document).ready(function () {
        console.log("script file loaded");
        $('.nav li').removeClass('active');
        var menuId = $('h1').attr('id');
        $('#' + menuId).addClass("active");
        console.log("doing something");

        $('#textarea').textext({
            plugins: 'tags prompt focus autocomplete ajax arrow',
            tagsItems: ['Basic', 'JavaScript', 'PHP', 'Scala'],
            prompt: 'Add one...',
            ajax: {
                url: '/manual/examples/data.json',
                dataType: 'json',
                cacheResults: true
            }
        });
    }
);