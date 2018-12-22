package services;

import models.UtilisateurDto;
import play.Logger;

public class UtilisateursService {

    public static final String LOG_PREFIX = "UtilisateursService | ";

    public static void creerUtilisateur(UtilisateurDto utilisateurDto) {
        Logger.debug("%s creerUtilisateur : [%s]", LOG_PREFIX, utilisateurDto.email);
    }
}
