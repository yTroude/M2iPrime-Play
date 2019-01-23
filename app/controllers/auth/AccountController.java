package controllers.auth;

import controllers.TrackerController;
import errors.BadUtilisateurException;
import errors.PasswordConfirmationException;
import errors.WrongPasswordException;
import models.dto.DeleteAccountDto;
import services.UtilisateursService;

import javax.validation.Valid;


public class AccountController extends TrackerController {

    public static void gestionCompte(){
        render();
    }

    public static void deleteAccount(@Valid DeleteAccountDto deleteAccountDto){
        if(validation.hasErrors()){
            params.flash();
            validation.keep();
            gestionCompte();
        }
        try {
            UtilisateursService.deleteAccount(deleteAccountDto);
        } catch (WrongPasswordException e) {
            flash.put("WrongPasswordException", "true");
            gestionCompte();
        } catch (PasswordConfirmationException e) {
            flash.put("PasswordConfirmationException", "true");
            gestionCompte();
        } catch (BadUtilisateurException e) {
            flash.put("BadUtilisateurException", "true");
            gestionCompte();
        }
        System.out.println("AccountController deleteAccount render");
        render();
    }
}
