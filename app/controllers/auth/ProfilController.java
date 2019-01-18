package controllers.auth;

import controllers.TrackerController;
import models.Profil;
import models.Utilisateur;
import models.dto.ProfilDto;
import services.ProfilsService;

import java.util.List;

import static controllers.Security.connectedUser;

public class ProfilController extends TrackerController {
    public static void profils() {
        Utilisateur utilisateur = connectedUser();
        List<ProfilDto> profils = ProfilsService.getListProfilDtoFromListProfils(utilisateur.profils);
        render(profils);
    }

    public static void modifierProfil() {
        render();
    }

    public static void choisirProfilActif() {
        Utilisateur utilisateur = connectedUser();
        List<ProfilDto> profils = ProfilsService.getListProfilDtoFromListProfils(utilisateur.profils);
        render(profils);
    }

    public static void changerProfilActif(String pseudo) {
        Profil profil = ProfilsService.getByUtilisateurAndPseudo(connectedUser(), pseudo);
        if (profil != null) {
            session.put("avatarActif", profil.avatar);
            session.put("profilActif", pseudo);
        }
        Auth.index();
    }
}
