package controllers.publ;

import controllers.TrackerController;
import errors.BadUtilisateurException;
import errors.BadValidationTokenException;
import errors.UtilisateurExisteException;
import errors.UtilisateurTropJeuneException;
import models.Utilisateur;
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
            flash.put("UtilisateurExiste","true");
            formulaireInscription();
        } catch (UtilisateurTropJeuneException e) {
            params.flash();
            flash.put("UtilisateurTropJeune","true");
            formulaireInscription();
        }
        render();
    }

    public static void confirmerInscription(String utilisateurUuid, String validationTokenUuid){
        try {
            UtilisateursService.confirmerUtilisateur(utilisateurUuid, validationTokenUuid);
            flash.put("status","OK");
        } catch (BadUtilisateurException e) {
            flash.put("status","BadUtilisateur");
//            e.printStackTrace();
        } catch (BadValidationTokenException e) {
            Utilisateur utilisateur = UtilisateursService.getByUuid(utilisateurUuid);
            flash.put("utilisateurEmail", utilisateur.email);
            flash.put("status","BadValidationToken");
//            e.printStackTrace();
        }
        render();
    }

    public static void renvoiEmailActivationDeCompte(String email){
        try {
            UtilisateursService.renvoiEmailActivationDeCompte(email);
            flash.put("status","OK");
        } catch (BadUtilisateurException e) {
            flash.put("status","EmailDoesNotExist");
        }
        render();
    }

}
