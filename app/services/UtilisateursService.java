package services;

import models.UtilisateurDto;
import play.Logger;

public class UtilisateursService {

    public static final String LOG_PREFIX = "UtilisateursService | ";

    public static void creerUtilisateur(UtilisateurDto utilisateurDto) {
        Logger.debug("%s creerUtilisateur : [%s]", LOG_PREFIX, utilisateurDto.email);
        utilisateurDto.save();
    }

    public static UtilisateurDto getByEmailAndPassword(String email, String password) {
        Logger.debug("%s getByEmailAndPassword : [%s]", LOG_PREFIX, email);
        return UtilisateurDto.find("email = ?1 AND password = ?2", email, password).first();
    }

    public static UtilisateurDto getByEmail(String email) {
        Logger.debug("%s getByEmail : [%s]", LOG_PREFIX, email);
        return UtilisateurDto.find("email = ?1", email).first();
    }
}
