var rootURL = "http://localhost:8080/PokemonProject/rest/pokemon";
var currentPokemon;

function openTab(evt, tabName) {
	var i, tabcontent, tablinks;

	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}

	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.className += " active";
}

var search = function(searchKey) {
	if (searchKey == '') {
		findAll();
	} else {
		findByName(searchKey);
	}
};

var newPokemon = function() {
	$('#btnDelete').hide();
	currentPokemon = {};
	formReset();
};

var findAll = function() {
	console.log('findAll');
	$.ajax({
		type : 'GET',
		url : rootURL,
		dataType : "json",
		success : renderList
	});
};

var findData = function() {
	console.log('findAll');
	$.ajax({
		type : 'GET',
		url : rootURL,
		dataType : "json",
		success : renderDataTable
	});
};

var findByName = function(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type : 'GET',
		url : rootURL + '/search/' + searchKey,
		dataType : "json",
		success : renderList
	});
};

var findById = function(id) {
	console.log('findById: ' + id);
	$.ajax({
		type : 'GET',
		url : rootURL + '/' + id,
		dataType : "json",
		success : function(data) {
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			currentPokemon = data;
			renderDetails(currentPokemon);
		}
	});
};

var addPokemon = function() {
	console.log('addPokemon');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL,
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Pokemon created successfully');
			$('#btnDelete').show();
			$('#pokemonId').val(data.id);
			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('addPokemon error: ' + textStatus);
		}
	});
};

var updatePokemon = function() {
	console.log('updatePokemon');
	$.ajax({
		type : 'PUT',
		contentType : 'application/json',
		url : rootURL + '/' + $('#pokemonId').val(),
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Pokemon updated successfully');
			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('updatePokemon error: ' + textStatus);
		}
	});
};

var deletePokemon = function() {
	console.log('deletePokemon');
	$.ajax({
		type : 'DELETE',
		url : rootURL + '/' + $('#pokemonId').val(),
		success : function(data, textStatus, jqXHR) {
			alert('Pokemon deleted successfully');
			findAll();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('deletePokemon error');
		}
	});
};

function renderList(data) {
	var list = data;

	$('#pokemonList li').remove();
	$.each(data, function(index, pokemon) {
		$('#pokemonList').append(
				'<li><a href="#" id="' + pokemon.id + '">' + pokemon.name
						+ '</a></li>');
	});

	output = '<div class="row">';
	$.each(list, function(index, pokemon) {
			var img = "pics/pics/" + pokemon.picture;
			output += ('<div class="col-sm-6 col-md-4 col-lg-3"><div class="card"><img src=' + '"' + img	+ '"'
			+ 'height="150"><p>Name: ' + pokemon.name + '</p><p>Region: '
			+ pokemon.region + '</p><p>Generation: ' + pokemon.generation + '</p></div></div>');
		});
	output += '</div>';
	$('#productList').append(output);
}

function renderDataTable(data){
	var list = data;
	
	console.log("response");
	$('#table_body tr').remove();
	$.each(list, function(index, pokemon) {
			$('#table_body').append('<tr><td>' + pokemon.id + '</td><td>' + pokemon.name + '</td><td>'
				+ pokemon.type + '</td><td>' + pokemon.weakness	+ '</td><td>' + pokemon.ability
				+ '</td><td><a data-toggle="modal" href="#pokeStop" id="' + pokemon.id + '">Edit</a></td></tr>');
			});
	$('#table_id').DataTable();
}

var renderDetails = function(pokemon) {
	$('#pokemonId').val(pokemon.id);
	$('#name').val(pokemon.name);
	$('#generation').val(pokemon.generation);
	$('#type').val(pokemon.type);
	$('#weakness').val(pokemon.weakness);
	$('#category').val(pokemon.category);
	$('#region').val(pokemon.region);
	$('#gender').val(pokemon.gender);
	$('#height').val(pokemon.height);
	$('#weight').val(pokemon.weight);
	$('#ability').val(pokemon.ability);
	$('#pic').attr('src', 'pics/pics/' + pokemon.picture);
	$('#description').val(pokemon.description);
};

var formToJSON = function() {
	var pokemonId = $('#pokemonId').val();
	return JSON.stringify({
		"id" : pokemonId == "" ? null : pokemonId,
		"name" : $('#name').val(),
		"generation" : $('#generation').val(),
		"type" : $('#type').val(),
		"weakness" : $('#weakness').val(),
		"category" : $('#category').val(),
		"region" : $('#region').val(),
		"gender" : $('#gender').val(),
		"height" : $('#height').val(),
		"weight" : $('#weight').val(),
		"ability" : $('#ability').val(),
		"picture" : currentPokemon.picture,
		"description" : $('#description').val()
	});
};

var formReset = function(){
	$('#pokemonId').val("");
	$('#name').val("");
	$('#generation').val("");
	$('#type').val("");
	$('#weakness').val("");
	$('#category').val("");
	$('#region').val("");
	$('#gender').val("");
	$('#height').val("");
	$('#weight').val("");
	$('#ability').val("");
	$('#pic').attr('src', "");
	$('#description').val("");
}

$(document).ready(function() {
	$('#Home').show();
	
	$('#admin').click(function(){
		findData();
	});
	
	$('#btnDelete').hide();

	$('#btnSearch').click(function() {
		search($('#searchKey').val());
		return false;
	});

	$('#searchKey').keypress(function(e) {
		if (e.which == 13) {
			search($('#searchKey').val());
			e.preventDefault();
			return false;
		}
	});

	$('#btnAdd').click(function() {
		newPokemon();
		return false;
	});

	$('#btnSave').click(function() {
		if ($('#pokemonId').val() == '')
			addPokemon();
		else
			updatePokemon();
			location.reload();
		return false;
	});

	$('#btnDelete').click(function() {
		deletePokemon();
		location.reload();
		return false;
	});

	$(document).on("click", '#table_body a', function() {
		findById(this.id);
	});

	$(document).on("click", '#pokemonList a', function() {
		findById(this.id);
	});

	$("img").error(function() {
		$(this).attr("src", "pics/pics/pokeball.jpg");
	});

	$('#pokemonId').val("");
	$('#name').val("");
	$('#generation').val("");
	$('#type').val("");
	$('#weakness').val("");
	$('#category').val("");
	$('#region').val("");
	$('#gender').val("");
	$('#height').val("");
	$('#weight').val("");
	$('#ability').val("");
	$('#pic').attr('src', "");
	$('#description').val("");

	findAll();
});
