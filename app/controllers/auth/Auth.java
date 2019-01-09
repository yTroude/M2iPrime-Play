package controllers.auth;

import controllers.Secure;
import controllers.TrackerController;
import play.mvc.With;

@With(Secure.class)
public class Auth extends TrackerController {

    public static void index(){
        render();
    }

}
