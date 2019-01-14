package controllers.auth;

import controllers.Secure;
import controllers.TrackerController;
import models.Profil;
import models.Utilisateur;
import models.dto.ProfilDto;
import play.mvc.Before;
import play.mvc.With;
import services.ProfilsService;

import java.util.List;
import java.util.Map;

import static controllers.Security.connectedUser;

@With(Secure.class)
public class Auth extends TrackerController {

    @Before
    static void getListProfils(){

    }

    public static void index() {
        if (session.contains("profilActif")) {
            Utilisateur utilisateur = connectedUser();
            Map<String,String> profils = ProfilsService.getProfilsAsMap(utilisateur.profils);
            render(profils);
        } else {
            choisirProfilActif();
        }
    }

    public static void choisirProfilActif() {
        Utilisateur utilisateur = connectedUser();
        List<ProfilDto> profils = ProfilsService.getListProfilDtoFromListProfils(utilisateur.profils);
        render(profils);
    }

    public static void changerProfilActif(String pseudo) {
        session.put("profilActif", pseudo);
        index();
    }

    public static void gestionCompte(){
        render();
    }
}
