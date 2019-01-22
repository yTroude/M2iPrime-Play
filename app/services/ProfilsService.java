package services;

import errors.NombreMaxProfilsAtteintException;
import errors.ProfilNotFoundException;
import models.Profil;
import models.Utilisateur;
import play.Logger;
import play.Play;

public class ProfilsService {
    public static final String LOG_PREFIX = "ProfilsService | ";


    public static Profil getByUtilisateurAndPseudo(Utilisateur utilisateur, String pseudo) {
        Logger.debug("%s getByUtilisateurAndPseudo [%s %s]", LOG_PREFIX, utilisateur.email, pseudo);
        return Profil.find("utilisateur=?1 AND pseudo=?2", utilisateur, pseudo).first();
    }


    public static void creerProfil(Utilisateur utilisateur, String pseudo, String avatar) throws NombreMaxProfilsAtteintException {
        Logger.debug("%s creerProfil [%s %s %s]", LOG_PREFIX, utilisateur.email, pseudo, avatar);

        if (utilisateur.profils.size() >= Integer.valueOf(Play.configuration.getProperty("profils.quantiteMax"))) {
            throw new NombreMaxProfilsAtteintException();
        }

        Profil profil = new Profil();
        profil.pseudo = pseudo;
        profil.avatar = avatar;
        profil.utilisateur = utilisateur;

        profil.save();
    }

    public static void modifierProfil(Utilisateur utilisateur, String oldPseudo, String pseudo, String avatar) throws ProfilNotFoundException {
        Logger.debug("%s modifierProfil [%s %s %s]", LOG_PREFIX, utilisateur.email, pseudo, avatar);

        Profil profil = getByUtilisateurAndPseudo(utilisateur, oldPseudo);
        if (profil == null) {
            throw new ProfilNotFoundException();
        }
        profil.pseudo = pseudo;
        profil.avatar = avatar;
        profil.save();
    }
}
