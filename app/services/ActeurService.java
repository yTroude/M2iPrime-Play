package services;

import models.Acteur;
import models.Video;
import play.Logger;

import java.util.List;

public class ActeurService {

    public static List<Acteur> findActeursByVideoUuid(String uuid) {
        return VideoService.getVideoByUuid(uuid).acteurs;
    }

    public static final String LOG_PREFIX = "ActeurService | ";

    public static void addActeur(String nomActeur, String prenomActeur) {
        Acteur acteur = new Acteur();
        acteur.nomActeur = nomActeur;
        acteur.prenomActeur = prenomActeur;
        //Logger.debug("%s addActeur : [%s / %s]", LOG_PREFIX, acteur.nomActeur, acteur.prenomActeur);
        acteur.save();
    }

    public static Acteur getActeurByUuid(String uuid) {

        return Acteur.findById(uuid);
    }
}
