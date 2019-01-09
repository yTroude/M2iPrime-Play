package controllers.publ;

import controllers.TrackerController;
import errors.AccountNotActivated;
import errors.BadPasswordResetRequestException;
import errors.BadUtilisateurException;
import errors.BadValidationTokenException;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import services.PasswordResetRequestService;
import services.UtilisateursService;

public class Publ extends TrackerController {

    public static void index() {
        render();
    }

    public static void motDePassePerdu() {
        render();
    }

    public static void sendResetPasswordEmail(String email){
        try {
            PasswordResetRequestService.sendResetPasswordEmail(email);
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

    public static void defineNewPassword(String passwordResetRequestUuid, String validationTokenUuid){
        try {
            UtilisateursService.defineNewPassword(passwordResetRequestUuid, validationTokenUuid);
        } catch (BadPasswordResetRequestException e) {
            flash.put("status", "BadPasswordResetRequestException");
            motDePassePerdu();
        } catch (BadValidationTokenException e) {
            flash.put("status", "BadValidationTokenException");
            motDePassePerdu();
        }
        render();
    }

}