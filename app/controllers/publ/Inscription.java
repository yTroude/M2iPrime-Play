package controllers.publ;

import controllers.TrackerController;
import errors.BadUtilisateurException;
import errors.BadValidationTokenException;
import errors.UtilisateurExisteException;
import errors.UtilisateurTropJeuneException;
import models.dto.InscriptionDto;
import play.data.validation.Valid;
import services.UtilisateursService;

public class Inscription extends TrackerController {

    public static void formulaireInscription() {
        render();
    }

    public static void inscription(@Valid InscriptionDto inscriptionDto) {
        if(validation.hasErrors()){
            params.flash();
            validation.keep();
            formulaireInscription();
        }
        try {
            UtilisateursService.creerUtilisateur(inscriptionDto);
        } catch (UtilisateurExisteException e) {
            params.flash();
            flash.put("UtilisateurExists","true");
            formulaireInscription();
        } catch (UtilisateurTropJeuneException e) {
            params.flash();
            flash.put("UtilisateurTropJeune","true");
            formulaireInscription();
        }
        render();
    }

    public static void confirmerInscription(String utilisateurUuid, String validationTokenUuid){
        //TODO gestion d'exception
        try {
            UtilisateursService.confirmerUtilisateur(utilisateurUuid, validationTokenUuid);
            params.put("status","OK");
        } catch (BadUtilisateurException e) {
            params.put("Sssstatus","BadUtilisateur");
//            e.printStackTrace();
        } catch (BadValidationTokenException e) {
            params.put("status","BadValidationToken");
            e.printStackTrace();
        }
        render();
    }

    public static void validTokenNotFound(){
        render();
    }

}
