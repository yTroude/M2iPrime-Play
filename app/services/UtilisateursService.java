package services;

import errors.*;
import models.PasswordResetRequest;
import models.Utilisateur;
import models.ValidationToken;
import models.dto.InscriptionDto;
import notifiers.Mails;
import org.apache.commons.lang.time.DateUtils;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import util.PasswordGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;

public class UtilisateursService {

    public static final String LOG_PREFIX = "UtilisateursService | ";

    public static void creerUtilisateur(InscriptionDto inscriptionDto) throws UtilisateurExisteException, UtilisateurTropJeuneException {
        Logger.debug("%s creerUtilisateur : [%s]", LOG_PREFIX, inscriptionDto.email);

        //Verifications metier
        if (getByEmail(inscriptionDto.email) != null) {
            throw new UtilisateurExisteException();
        }
        LocalDate ld = LocalDate.now().minusYears(16);
        Date minNaissance = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (inscriptionDto.dateNaissance.after(minNaissance)) {
            throw new UtilisateurTropJeuneException();
        }

        //Creation objet Utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.dateNaissance = inscriptionDto.dateNaissance;
        utilisateur.email = inscriptionDto.email;
        utilisateur.password = BCrypt.hashpw(inscriptionDto.password, BCrypt.gensalt());
        utilisateur.valid = false;

        //Token validation
        utilisateur.validationToken = ValidationTokenService.createValidationToken();

        //Enregistrer utilisateur
        utilisateur.save();

        //Envoi mail
        Mails.confirmerInscription(utilisateur);
    }

    public static Utilisateur getByEmailAndPassword(String email, String password) {
        Logger.debug("%s getByEmailAndPassword : [%s]", LOG_PREFIX, email);
        return Utilisateur.find("email = ?1 AND password = ?2", email, password).first();
    }

    public static Utilisateur getByEmail(String email) {
        Logger.debug("%s getByEmail : [%s]", LOG_PREFIX, email);
        return Utilisateur.find("email = ?1", email).first();
    }

    public static Utilisateur getByUuid(String uuid){
        Logger.debug("%s getByUuid : [%s]", LOG_PREFIX, uuid);
        return Utilisateur.findById(uuid);
    }

    public static void confirmerUtilisateur(String utilisateurUuid, String validationTokenUuid) throws BadUtilisateurException, BadValidationTokenException, AccountAlreadyActivated {
        Logger.debug("%s confirmerUtilisateur : [%s] [%s]", LOG_PREFIX, utilisateurUuid, validationTokenUuid);
        Utilisateur utilisateur = Utilisateur.find("uuid = ?1", utilisateurUuid).first();
        if (utilisateur == null) {
            throw new BadUtilisateurException();
        }
        if (utilisateur.valid == true){
            throw new AccountAlreadyActivated();
        }
        ValidationToken validationToken = ValidationToken.find("uuid = ?1", validationTokenUuid).first();
        LocalDate ld = LocalDate.now().minusDays(1);
        Date dateLimiteValidToken = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (validationToken == null || validationToken.dateCreation.before(dateLimiteValidToken)) {
            throw new BadValidationTokenException();
        } else {
            utilisateur.valid = true;
            utilisateur.save();
        }
    }

    public static void renvoiEmailActivationDeCompte(String email) throws BadUtilisateurException {
        Logger.debug("%s renvoiEmailActivationDeCompte : [%s]", LOG_PREFIX, email);
        Utilisateur utilisateur = UtilisateursService.getByEmail(email);
        if (utilisateur == null) {
            throw new BadUtilisateurException();
        }
        utilisateur.validationToken = ValidationTokenService.createValidationToken();
        utilisateur.save();
        Mails.confirmerInscription(utilisateur);
    }

    public static void defineNewPassword(String passwordResetRequestUuid, String validationTokenUuid) throws BadPasswordResetRequestException, BadValidationTokenException {
        Logger.debug("%s defineNewPassword : [%s] [%s]", LOG_PREFIX, passwordResetRequestUuid, validationTokenUuid);
        PasswordResetRequest passwordResetRequest = PasswordResetRequest.find("uuid = ?1", passwordResetRequestUuid).first();
        if (passwordResetRequest == null) {
            throw new BadPasswordResetRequestException();
        }

        ValidationToken validationToken = ValidationToken.find("uuid = ?1", validationTokenUuid).first();
//        LocalDate ld = LocalDate.now();
//        Date dateLimiteValidToken = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        final long FIVE_MINUTES_IN_MILLIS=300000;//millisecs
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date dateLimiteValidToken = new Date(t - FIVE_MINUTES_IN_MILLIS);
        System.out.println("validationToken.dateCreation : " + validationToken.dateCreation);
        System.out.println("dateLimiteValidToken : " + dateLimiteValidToken);

        if (validationToken == null || validationToken.dateCreation.before(dateLimiteValidToken)) {
            System.out.println("validationToken.dateCreation : " + validationToken.dateCreation);
            System.out.println("dateLimiteValidToken : " + dateLimiteValidToken);
            throw new BadValidationTokenException();
        }
    }
}
