package services;

import models.Utilisateur;
import models.ValidationToken;
import play.Logger;

import java.time.Instant;
import java.util.Date;

public class ValidationTokenService {

    public static final String LOG_PREFIX = "ValidationTokenService | ";

    protected static ValidationToken createValidationToken(Utilisateur utilisateur){
        Logger.debug("%s createValidationToken : [%s]", LOG_PREFIX, utilisateur.email);
        ValidationToken validationToken = new ValidationToken();
        validationToken.dateCreation = Date.from(Instant.now());
        return validationToken;
    }
}
