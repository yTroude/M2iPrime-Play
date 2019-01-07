package controllers.admin;

import controllers.Check;
import controllers.Secure;
import controllers.TrackerController;
import play.mvc.With;


@With(Secure.class)
@Check({"ADMIN"})
public class Admin extends TrackerController {

    public static void index() {
        render();
    }

}
