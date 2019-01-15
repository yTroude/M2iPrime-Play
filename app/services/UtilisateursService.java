package services;

import errors.*;
import models.PasswordResetRequest;
import models.Utilisateur;
import models.ValidationToken;
import models.dto.InscriptionDto;
import models.dto.NewPasswordDto;
import notifiers.Mails;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

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
        if (utilisateur.valid){
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

        //Vérifier que le compte existe
        Utilisateur utilisateur = UtilisateursService.getByEmail(email);
        if (utilisateur == null) {
            throw new BadUtilisateurException();
        }

        //Générer un token de validation, save en BDD, envoyer l'email d'activation de compte
        utilisateur.validationToken = ValidationTokenService.createValidationToken();
        utilisateur.save();
        Mails.confirmerInscription(utilisateur);
    }



    public static void validateNewPassword(NewPasswordDto newPasswordDto) throws PasswordConfirmationException, BadUtilisateurException, BadPasswordResetRequestException {
        Logger.debug("%s validateNewPassword : [%s]", LOG_PREFIX, newPasswordDto.passwordResetRequestUuid);

        //Verifications metier
        PasswordResetRequest passwordResetRequest = PasswordResetRequest.find("uuid = ?1", newPasswordDto.passwordResetRequestUuid).first();
        if (passwordResetRequest == null){
            throw new BadPasswordResetRequestException();
        }
        if (getByEmail(passwordResetRequest.email) == null) {
            throw new BadUtilisateurException();
        }
        if (!newPasswordDto.password.equals(newPasswordDto.passwordConfirmation)) {
            throw new PasswordConfirmationException();
        }

        //Affecter nouveau mot de passe à l'objet Utilisateur correspondant
        Utilisateur utilisateur = getByEmail(passwordResetRequest.email);
        utilisateur.password = BCrypt.hashpw(newPasswordDto.password, BCrypt.gensalt());

        //Enregistrer utilisateur
        utilisateur.save();
    }
}
