package controllers.auth;

import controllers.Secure;
import controllers.TrackerController;
import models.Utilisateur;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.mvc.With;
import services.UtilisateursService;
import util.Images;

import static controllers.Security.connectedUser;

@With(Secure.class)
public class Inscription extends TrackerController {
    public static void formulaireFinInscription() {
        String[] avatars = Images.avatars;
        render(avatars);
    }

    public static void finirInscription(@Required @Unique String pseudo, @Required String avatar) {
        boolean valid=true;
        if (validation.hasErrors()) {
            valid=false;
        }
        if (UtilisateursService.getByPseudo(pseudo) != null){
            flash.put("PseudoExiste","true");
        }
        if(!valid){
            params.flash();
            validation.keep();
            formulaireFinInscription();
        }

        Utilisateur utilisateur = connectedUser();
        utilisateur.pseudo = pseudo;
        utilisateur.avatar = avatar;
        render();
    }
}
