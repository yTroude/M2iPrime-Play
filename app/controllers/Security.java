package controllers;

import controllers.auth.Auth;
import controllers.publ.Publ;
import models.UtilisateurDto;
import services.UtilisateursService;

public class Security extends Secure.Security {

    public static final String LOG_PREFIX = "Security | ";

    static boolean authenticate(String username, String password) {
        UtilisateurDto utilisateur = UtilisateursService.getByEmailAndPassword(username, password);
        if (utilisateur != null) {
            return true;
        }
        return false;
    }

    static void onAuthenticated() { Auth.index(); }

    static void onDisconnected() {
        Publ.index();
    }

    public static UtilisateurDto connectedUser() {
        String username = connected();
        return UtilisateursService.getByEmail(username);
    }
}