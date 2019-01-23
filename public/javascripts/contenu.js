
//liste des films
$(document).ready(function(){
    fetch("http:/api/videos/film",{
        method:'GET'
    }).then(response => response.json())
        .then(data=>{//data=profils
            var container = $('#container');
            for (var i = 0; i < data.length; i++) {
                var listeFilms = $('<img>')
                    .attr("src", data[i].image)
                    .attr("class", "contentminiature")
                    .attr('id', data[i].uuid)
                    .appendTo(container)
                    .click(function () {
                        detailModal(this.id)
                    });
            }
        })
});


//liste des series
$(document).ready(function() {
    fetch("http:/api/videos/serie", {
        method: 'GET'
    }).then(response => response.json())
        .then(data => {
            var container2 = $('#container2');
            container2.html('');
            for (var i = 0; i < data.length; i++) {
                var listeSerie = document.createElement('img');
                listeSerie.setAttribute("src", data[i].image);
                listeSerie.setAttribute("class", "contentminiature");


                container2.append(listeSerie)
            }
        })
})
//liste des documentaires
$(document).ready(function() {
    fetch("http:/api/videos/documentaire", {
        method: 'GET'
    }).then(response => response.json())
        .then(data => {
            var container3 = $('#container3');
            container3.html('');
            for (var i = 0; i < data.length; i++) {
                var listeDocumentaires = document.createElement('img');
                listeDocumentaires.setAttribute("src", data[i].image);
                listeDocumentaires.setAttribute("class", "contentminiature");

                container3.append(listeDocumentaires)
            }
        })
})

function detailModal(uuid) {
    fetch("http:/api/videos/details/" + uuid, {
        method: 'GET'
    }).then(response => response.json())
        .then(data => {
            var theModal = $('#theModal');



            var contenu = data.titre + "<br>" + data.desc + "<br>"+data.format + "<br>" + data.categorie ;



            $('<p>').html(contenu)
                .appendTo('#modalContent');
            for(var i=0;i<data.acteurs.length; i++ ){console.log(data.acteurs[i]);
                $('<p>').html( data.acteurs[i].nomActeur + " " + data.acteurs[i].prenomActeur).appendTo('#modalContent');
            }
            theModal.css('display', 'block')

        });
}


/*
function allVideos(){
    fetch("http:/api/videos",{
        method:'GET'
    }).then(response=>response.json())
    .then(data=>{for(var i=0;i<data.length;i++){console.log(data[i].titre);}})

}
*/
/*
function detailVideos(){
    fetch("http:/api/videos/details/{uuid}",{
        method:'GET'
    }).then(response=>response.json())
        .then(data=>console.log(data));

}
*/
/*
function filterByCategorie(){
    fetch("http:/api/videos/categorie/{categorie}",{
        method:'GET'
    }).then(response=>response.json())
        .then(data=>console.log(data));

}
*/
/*
function filterByFormat(){
    fetch("http:/api/videos/{format}",{
        method:'GET'
    }).then(response=>response.json())
        .then(data=>console.log(data));

}
*/
/*
function filterByFormatAndCategorie(){
    fetch("http:/api/videos/{format}/{categorie}",{
        method:'GET'
    }).then(response=>response.json())
        .then(data=>console.log(data));

}
*/
/*
function detailsActeur(){
    fetch("http:/api/acteurs/details/{uuid}",{
        method:'GET'
    }).then(response=>response.json())
        .then(data=>console.log(data));

}
*/
/*
function addActeur(){
    fetch("http:/api/acteurs/add",{
        method:'POST'
    }).then(response=>response.json())
        .then(data=>console.log(data));

}
*/

/*
function firstVideoTitre(){
    let videos=allVideos();
    return videos[0].titre;
}
*/
/*
function plop(){
    fetch('http:/api/videos',{
        method:'GET'
    }).then(response => response.json())
        .then(data=>{//data=profils
            var container = $('#container');
            container.html('');
            for (var i=0;i<data.length;i++){
                var paragraphe = document.createElement('p');
                paragraphe.innerText = data[i].desc;
                container.append(paragraphe)
            }
        })
}
*/