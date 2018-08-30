$(document).ready(function () {
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