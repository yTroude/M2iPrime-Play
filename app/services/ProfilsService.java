package services;

import models.Profil;
import models.Utilisateur;

public class ProfilsService {
    public static final String LOG_PREFIX = "ProfilsService | ";


    public static Profil getByUtilisateurAndPseudo(Utilisateur utilisateur, String pseudo) {
        return Profil.find("utilisateur=?1 AND pseudo=?2", utilisateur, pseudo).first();
    }

    //TODO Gestion des différents profils avec profil actif
}
