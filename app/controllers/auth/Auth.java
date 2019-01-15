package controllers.auth;

import controllers.Secure;
import controllers.TrackerController;
import play.mvc.With;

import static controllers.Security.connectedUser;
import static controllers.auth.Inscription.formulaireFinInscription;

@With(Secure.class)
public class Auth extends TrackerController {

    public static void index(){
        if(connectedUser().pseudo==null){
            formulaireFinInscription();
        }
        render();
    }

}
