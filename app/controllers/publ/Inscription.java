package controllers.publ;

import controllers.TrackerController;
import models.UtilisateurDto;
import services.UtilisateursService;

public class Inscription extends TrackerController {

    public static void formulaireInscription() {
        render();
    }

    public static void inscription(UtilisateurDto utilisateurDto) {
        UtilisateursService.creerUtilisateur(utilisateurDto);
        render();
    }
}
