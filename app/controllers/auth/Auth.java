package controllers.auth;

import controllers.Secure;
import controllers.TrackerController;
import models.Profil;
import models.Utilisateur;
import play.mvc.With;

import java.util.HashMap;
import java.util.List;

import static controllers.Security.connectedUser;

@With(Secure.class)
public class Auth extends TrackerController {

    public static void index(){
        Utilisateur utilisateur = connectedUser();
        HashMap<String,String>profils
        render(profils);
    }

    public static void changeActiveProfile(Profil profil){
        session.put("pseudoProfilActif",profil.pseudo);
        session.put("avatarProfilActif",profil.avatar);
    }

    private static void firstTime() {
        Utilisateur utilisateur = connectedUser();

    }


}
