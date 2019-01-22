package notifiers;

import models.PasswordResetRequest;
import models.Utilisateur;
import play.Logger;
import play.mvc.Mailer;

public class Mails extends Mailer {

    public static final String LOG_PREFIX = "Mails | ";

    public static void confirmerInscription(Utilisateur utilisateur){
        Logger.debug("%s confirmerInscription : [%s]", LOG_PREFIX, utilisateur.email);
        setSubject("Confirmez votre inscription à M2iPrime");
        addRecipient(utilisateur.email);
        setFrom("M2iPrime <test.m2iprime@gmail.com>");
        send(utilisateur);
    }

    public static void reinitialiserMotDePasse(PasswordResetRequest passwordResetRequest) {
        Logger.debug("%s reinitialiserMotDePasse : [%s]", LOG_PREFIX, passwordResetRequest.email);
        setSubject("Réinitialisation du mot de passe de votre compte M2iPrime");
        addRecipient(passwordResetRequest.email);
        setFrom("M2iPrime <test.m2iprime@gmail.com>");
        send(passwordResetRequest);
    }
}
