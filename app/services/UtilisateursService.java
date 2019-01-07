package services;

import errors.*;
import models.Utilisateur;
import models.ValidationToken;
import models.dto.InscriptionDto;
import notifiers.Mails;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        ValidationToken validationToken = new ValidationToken();
        validationToken.dateCreation = Date.from(Instant.now());
        utilisateur.validationToken = validationToken;

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

    public static void confirmerUtilisateur(String utilisateurUuid, String validationTokenUuid) throws BadUtilisateurException, BadValidationTokenException {
        Logger.debug("%s confirmerUtilisateur : [%s] [%s]", LOG_PREFIX, utilisateurUuid, validationTokenUuid);
        Utilisateur utilisateur = Utilisateur.find("uuid = ?1", utilisateurUuid).first();
        if (utilisateur == null) {
            throw new BadUtilisateurException();
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

    public static void resentEmailForValidationToken(){

    }
}
