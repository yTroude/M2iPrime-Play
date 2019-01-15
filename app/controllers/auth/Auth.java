package controllers.auth;

import controllers.Secure;
import controllers.TrackerController;
import models.Profil;
import models.Utilisateur;
import play.mvc.Before;
import play.mvc.With;

import java.util.List;

import static controllers.Security.connectedUser;

@With(Secure.class)
public class Auth extends TrackerController {

    @Before
    static void getListProfils() {

    }

    public static void index() {
        if (session.contains("profilActif")) {
            Utilisateur utilisateur = connectedUser();
            List<Profil> profils = utilisateur.profils;
            render(profils);
        } else {
            ProfilController.choisirProfilActif();
        }
    }

    public static void gestionCompte() {
        render();
    }
}
