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
        render();
    }

    public static void defineNewPassword(String passwordResetRequestUuid, String validationTokenUuid){
        try {
            PasswordResetRequestService.defineNewPassword(passwordResetRequestUuid, validationTokenUuid);
        } catch (BadPasswordResetRequestException e) {
            flash.put("status", "BadPasswordResetRequestException");
            motDePassePerdu();
        } catch (BadValidationTokenException e) {
            flash.put("status", "BadValidationTokenException");
            motDePassePerdu();
        } catch (ValidationTokenExpiredException e) {
            flash.put("status", "ValidationTokenExpiredException");
            motDePassePerdu();
        }
        render(passwordResetRequestUuid, validationTokenUuid);
    }

    public static void validateNewPassword(@Valid NewPasswordDto newPasswordDto){
        if(validation.hasErrors()){
            params.flash();
            validation.keep();
            defineNewPassword(newPasswordDto.passwordResetRequestUuid, newPasswordDto.validationTokenUuid);
        }
        try {
            UtilisateursService.validateNewPassword(newPasswordDto);
        } catch (BadPasswordResetRequestException e) {
            flash.put("BadPasswordResetRequestException", "true");
            motDePassePerdu();
        } catch (PasswordConfirmationException e) {
            flash.put("PasswordConfirmationException", "true");
            defineNewPassword(newPasswordDto.passwordResetRequestUuid, newPasswordDto.validationTokenUuid);
        } catch (BadUtilisateurException e) {
            flash.put("BadUtilisateurException", "true");
            motDePassePerdu();
        }
        render();
    }

}