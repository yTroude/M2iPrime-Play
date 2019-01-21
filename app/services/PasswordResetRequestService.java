package services;

import errors.*;
import models.PasswordResetRequest;
import models.Utilisateur;
import models.ValidationToken;
import notifiers.Mails;
import play.Logger;

import java.util.Calendar;
import java.util.Date;

public class PasswordResetRequestService {

    public static final String LOG_PREFIX = "PasswordResetRequestService | ";

    public static PasswordResetRequest getByEmail(String email) {
        Logger.debug("%s getByEmail : [%s]", LOG_PREFIX, email);
        return PasswordResetRequest.find("email = ?1", email).first();
    }

    public static void sendResetPasswordEmail(String email) throws BadUtilisateurException, AccountNotActivated {
        Logger.debug("%s reinitMotDePasse : [%s]", LOG_PREFIX, email);
        Utilisateur utilisateur = UtilisateursService.getByEmail(email);

        //Vérifications métier
        if (utilisateur == null) {
            throw new BadUtilisateurException();
        }
        if (utilisateur.valid == false){
            throw new AccountNotActivated();
        }

        //Créer l'objet et lui affecter les valeurs
        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.email = utilisateur.email;
        passwordResetRequest.validationToken = ValidationTokenService.createValidationToken();

        //Enregistrer les paramètres de l'objet
        passwordResetRequest.save();

        //Envoi du mail
        Mails.reinitialiserMotDePasse(passwordResetRequest);
    }

    public static void defineNewPassword(String passwordResetRequestUuid, String validationTokenUuid) throws BadPasswordResetRequestException, BadValidationTokenException, ValidationTokenExpiredException {
        Logger.debug("%s defineNewPassword : [%s] [%s]", LOG_PREFIX, passwordResetRequestUuid, validationTokenUuid);

        //Retrouver l'objet par uuid et vérifier qu'il existe toujours dans la BDD
        PasswordResetRequest passwordResetRequest = PasswordResetRequest.find("uuid = ?1", passwordResetRequestUuid).first();
        if (passwordResetRequest == null) {
            throw new BadPasswordResetRequestException();
        }

        //Vérifier que le token existe et qu'il n'est pas expiré
        ValidationToken validationToken = ValidationToken.find("uuid = ?1", validationTokenUuid).first();
        final long FIVE_MINUTES_IN_MILLIS=300000;//millisecs
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date dateLimiteValidToken = new Date(t - FIVE_MINUTES_IN_MILLIS);

        if (validationToken == null) {
            throw new BadValidationTokenException();
        }
        if (validationToken.dateCreation.before(dateLimiteValidToken)){
            throw new ValidationTokenExpiredException();
        }
    }
}
