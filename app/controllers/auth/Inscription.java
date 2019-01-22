package controllers.auth;

import controllers.Secure;
import controllers.TrackerController;
import models.Utilisateur;
import play.data.validation.Required;
import play.mvc.With;
import services.UtilisateursService;
import util.Images;

import static controllers.Security.connectedUser;

@With(Secure.class)
public class Inscription extends TrackerController {
    public static void formulaireFinInscription() {
        String[] avatars = Images.avatars;
        renderArgs.put("avatars",avatars);
        render();
    }

    public static void finirInscription(@Required String pseudo, @Required String avatar) {
        if (validation.hasErrors()) {
            validation.keep();
            params.flash();
            formulaireFinInscription();
            //valid=false;
        }
        if (UtilisateursService.getByPseudo(pseudo) != null){
            flash.put("PseudoExiste","true");
            params.flash();
            validation.keep();
            formulaireFinInscription();
        }

        Utilisateur utilisateur = connectedUser();
        utilisateur.pseudo = pseudo;
        utilisateur.avatar = avatar;
        utilisateur.save();
        render();
    }
}
