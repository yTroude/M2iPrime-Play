package services;

import models.Utilisateur;
import models.ValidationToken;
import play.Logger;

import java.time.Instant;
import java.util.Date;

public class ValidationTokenService {

    public static final String LOG_PREFIX = "ValidationTokenService | ";

    protected static ValidationToken createValidationToken(){
        Logger.debug("%s createValidationToken", LOG_PREFIX);
        ValidationToken validationToken = new ValidationToken();
        validationToken.dateCreation = Date.from(Instant.now());
        return validationToken;
    }
}
