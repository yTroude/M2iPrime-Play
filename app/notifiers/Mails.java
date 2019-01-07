package notifiers;

import models.Utilisateur;
import play.mvc.Mailer;

public class Mails extends Mailer {
    public static void confirmerInscription(Utilisateur utilisateur){
        setSubject("Confirmez votre inscription à M2iPrime");
        addRecipient(utilisateur.email);
        setFrom("M2iPrime <test.m2iprime@gmail.com>");
        send(utilisateur);
    }
}
