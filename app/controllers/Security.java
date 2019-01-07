package controllers;

import controllers.admin.Admin;
import controllers.auth.Auth;
import controllers.publ.Publ;
import models.EUtilisateurRole;
import models.Utilisateur;
import models.dto.InscriptionDto;
import services.UtilisateursService;

public class Security extends Secure.Security {

    public static final String LOG_PREFIX = "Security | ";

    static boolean authenticate(String email, String password) {
        Utilisateur utilisateur = UtilisateursService.getByEmailAndPassword(email, password);
        if (utilisateur != null) {
            return true;
        }
        return false;
    }

    static void onAuthenticated() {
        if(connectedUser().role == EUtilisateurRole.ADMIN) {
            Admin.index();
        } else {
            Auth.index();
        }
    }

    static void onDisconnected() {
        Publ.index();
    }

    public static Utilisateur connectedUser() {
        String email = connected();
        return UtilisateursService.getByEmail(email);
    }
}