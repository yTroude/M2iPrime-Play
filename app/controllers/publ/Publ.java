package controllers.publ;

import controllers.TrackerController;
import errors.*;
import models.dto.NewPasswordDto;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import services.PasswordResetRequestService;
import services.UtilisateursService;

import javax.validation.Valid;

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
            System.out.println("BadPasswordResetRequestException");
            flash.put("status", "BadPasswordResetRequestException");
            motDePassePerdu();
        } catch (BadValidationTokenException e) {
            System.out.println("BadValidationTokenException");
            flash.put("status", "BadValidationTokenException");
            motDePassePerdu();
        }
        System.out.println("defineNewPassword render()");
        render();
    }

    public static void resetPassword(@Valid NewPasswordDto newPasswordDto){
        if(validation.hasErrors()){
            params.flash();
            validation.keep();
//            defineNewPassword();
        }
        try {
            UtilisateursService.resetPassword(newPasswordDto);
        } catch (PasswordConfirmationException e) {
            flash.put("PasswordConfirmationException", "true");
        } catch (BadUtilisateurException e) {
            flash.put("BadUtilisateurException", "true");
        }
        render();
    }

}