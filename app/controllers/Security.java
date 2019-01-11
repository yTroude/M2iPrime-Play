package controllers;

import controllers.auth.Auth;
import models.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import services.UtilisateursService;

import static util.ValidationStatus.MAIL_SENT;

public class Security extends Secure.Security {

    public static final String LOG_PREFIX = "Security | ";

    static boolean authenticate(String username, String password){
        Logger.debug("%s authenticate [user %s ; pass %s]", LOG_PREFIX,username,password);
        Utilisateur utilisateur = UtilisateursService.getByEmail(username);
        if (utilisateur != null && (utilisateur.validationStatus != MAIL_SENT)){
            Logger.debug("%s authenticate, utilisateur != null", LOG_PREFIX);
            if (BCrypt.checkpw(password, utilisateur.password)){
                Logger.debug("%s authenticate [user=%s]", LOG_PREFIX,username);
                return true;
            }
        }
        Logger.debug("%s authenticate, failed to authenticate, return false", LOG_PREFIX);
        return false;
    }

//    static boolean authenticate(String username, String password) {
//        Utilisateur utilisateur = UtilisateursService.getByEmailAndPassword(username, password);
//        if (utilisateur != null) {
//            return true;
//        }
//        return false;
//    }

    static void onAuthenticated() { Auth.index(); }

    static void onDisconnected() { Auth.index(); }

    public static Utilisateur connectedUser() {
        String username = connected();
        return UtilisateursService.getByEmail(username);
    }
}