var trHTML = '';

$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/jrws/rest/user/list"
    }).then(function(data) {
    	$.each(data, function(key) {        
    		trHTML += '<tr><td>' + data[key].id+ '</td><td>' +data[key].name+ '</td><td>' +data[key].description +
    		'</td><td><button type="button">Edit</button><button type="button" onclick="deleteUser('+data[key].id+')">Delete</button>'+
    		'</td></tr>';
    	});
    	$('#userTable').append(trHTML);
    });
});

function deleteUser(id) {
	 $.ajax({
	     url: "http://localhost:8080/jrws/rest/user/delete/"+id
	 }).then(function(data) {
	    	
	 });
}

