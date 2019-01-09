package services;

import errors.AccountNotActivated;
import errors.BadUtilisateurException;
import models.PasswordResetRequest;
import models.Utilisateur;
import notifiers.Mails;
import play.Logger;

public class PasswordResetRequestService {

    public static final String LOG_PREFIX = "PasswordResetRequestService | ";


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

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.email = utilisateur.email;
        passwordResetRequest.validationToken = ValidationTokenService.createValidationToken();

        passwordResetRequest.save();

        //Générer new mdp, envoyer mail au user, chiffrer, enregistrer
//        String generatedPwd = PasswordGenerator.generatePassword(12,
//                PasswordGenerator.ALPHA_CAPS +
//                        PasswordGenerator.ALPHA +
//                        PasswordGenerator.SPECIAL_CHARS +
//                        PasswordGenerator.NUMERIC);
//        utilisateur.password = generatedPwd;
        Mails.reinitialiserMotDePasse(passwordResetRequest);
//        utilisateur.password = BCrypt.hashpw(generatedPwd, BCrypt.gensalt());
//        utilisateur.save();
    }
}
