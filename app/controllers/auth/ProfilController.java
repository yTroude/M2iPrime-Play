package controllers.auth;

import controllers.TrackerController;
import errors.NombreMaxProfilsAtteintException;
import errors.ProfilNotFoundException;
import models.Profil;
import models.Utilisateur;
import play.Logger;
import play.data.validation.Required;
import services.ProfilsService;

import java.util.List;

import static controllers.Security.connectedUser;

public class ProfilController extends TrackerController {
    public static void profils() {
        Utilisateur utilisateur = connectedUser();
        List<Profil> profils = connectedUser().profils;
        render(profils);
    }

    public static void modifierProfil(@Required String avatar, @Required String pseudo, @Required String oldPseudo) {
        Logger.debug("controller modifierProfil %s %s",oldPseudo,pseudo);
        if ("createProfil".equals(oldPseudo)) { //Créer profil
            try {
                ProfilsService.creerProfil(connectedUser(), pseudo, avatar);
            } catch (NombreMaxProfilsAtteintException e) {
                Logger.debug("NombreMaxProfilsAtteintException");
                profils();
            }
        } else { //modifier profil
            try {
                ProfilsService.modifierProfil(connectedUser(), oldPseudo, pseudo, avatar);
                if (session.get("profilActif").equals(oldPseudo)){
                    session.put("profilActif",pseudo);
                    session.put("avatarActif",avatar);
                }
            } catch (ProfilNotFoundException e) {
                Logger.debug("ProfilNotFoundException");
                profils();
            }
        }
        profils();
    }

    public static void choisirProfilActif() {
        Utilisateur utilisateur = connectedUser();
        List<Profil> profils = connectedUser().profils;
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
