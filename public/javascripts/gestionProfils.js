$(document).ready(function () {

    //Gestion de la soumission du formulaire
    $('#profilForm').submit(function () {
        $('#oldPseudoInput').attr('value',
            $('.actif').first().attr('id'));
        return true;
    });
    //Gestion de l'affichage du formulaire de modification de profil
    $('.select-profile').click(function () {
        var elem = $(this);
        var collapseDiv = $('#profil-collapse');

        //Si le formulaire est déjà affiché, on le cache, on modifie les informations puis on le montre à nouveau
        if (collapseDiv.hasClass("show")) {
            collapseDiv.on('hidden.bs.collapse', function () {
                updateInputValues(elem);
                $(this).collapse('show');
            });
            collapseDiv.collapse('hide');
        }
        //Sinon, on modifie les informations puis on le montre
        else {
            updateInputValues(elem);
            collapseDiv.collapse('show');
        }

        $('.actif').removeClass("actif");
        elem.addClass("actif");
    });

    //Gestion de l'affichage du formulaire de création de profil
    $('.create-profile').click(function () {
        var collapseDiv = $('#profil-collapse');
        //Si le formulaire est déjà affiché, on le cache, on modifie les informations puis on le montre à nouveau
        if (collapseDiv.hasClass("show")) {
            collapseDiv.on('hidden.bs.collapse', function () {
                clearInputValues();
                $(this).collapse('show');
            });
            collapseDiv.collapse('hide');
        }
        else {
            clearInputValues();
            collapseDiv.collapse('show');
        }
        $('.actif').removeClass("actif");
        $(this).addClass("actif");

    });
});

/**
 * Met à jour les valeurs du formulaire de gestion de profil
 * @param elem Le bouton cliqué, qui contient les informations qu'il faut afficher
 */
function updateInputValues(elem) {
    $('#pseudoInput').val(elem.attr("id"));
    $('.avatarInput')
        .filter(function (index) {
            return $(this).attr('value') === elem.children().first().attr('id');
        })
        .prop("checked", true);
    $('#submit-profil').text("Mettre à jour");
    $('#submit-profil').removeClass("create");
    $('#submit-profil').addClass("Mettre à jour");

}

/**
 * Initialise les valeurs du formulaire pour la création d'un nouveau profil
 */
function clearInputValues() {
    $('#pseudoInput').val('');
    $('.avatarInput')
        .filter(function (index) {
            return $(this).attr('value') === "/public/images/avatars/_avatar.png";
        })
        .prop("checked", true);
    $('#submit-profil').text("Créer le profil");
}