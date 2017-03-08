var trHTML = '';

$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/jrws/rest/department/list"
    }).then(function(data) {
    	$.each(data, function(key) {        
    		trHTML += '<tr><td>' + data[key].id+ '</td><td>' +data[key].name+ '</td><td>' +data[key].description +
    		'</td><td><button type="button">Edit</button><button type="button">Delete</button></td></tr>';
    	});
    	$('#departmentTable').append(trHTML);
    });
});