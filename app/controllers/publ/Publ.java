package controllers.publ;

import controllers.TrackerController;
import errors.AccountNotActivated;
import errors.BadUtilisateurException;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import services.UtilisateursService;

public class Publ extends TrackerController {

    public static void index() {
        render();
    }

    public static void motDePassePerdu() {
        render();
    }

    public static void reinitMotDePasse(String email){
        try {
            UtilisateursService.reinitMotDePasse(email);
        } catch (BadUtilisateurException e) {
            flash.put("lostPwd", "BadUtilisateurException");
            motDePassePerdu();
        } catch (AccountNotActivated e) {
            flash.put("utilisateurEmail", email);
            flash.put("AccountNotActivated", "true");
            motDePassePerdu();
        }
        flash.clear();
        render();
    }

}