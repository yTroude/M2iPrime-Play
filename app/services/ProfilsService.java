package services;

import models.Profil;
import models.Utilisateur;
import models.dto.ProfilDto;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProfilsService {
    public static final String LOG_PREFIX = "ProfilsService | ";


    public static Profil getByUtilisateurAndPseudo(Utilisateur utilisateur, String pseudo) {
        Logger.debug("%s getByUtilisateurAndPseudo [%s %s]", LOG_PREFIX, utilisateur.email, pseudo);
        return Profil.find("utilisateur=?1 AND pseudo=?2", utilisateur, pseudo).first();
    }

    public static ProfilDto getprofilDtoFromProfil(Profil profil) {
        Logger.debug("%s getprofilDtoFromProfil", LOG_PREFIX);
        if (profil == null) {
            return null;
        }
        Logger.debug("%s getProfilDtoFromProfil", LOG_PREFIX);
        ProfilDto profilDto = new ProfilDto();
        profilDto.avatar = profil.avatar;
        profilDto.pseudo = profil.pseudo;
        return profilDto;
    }

    public static List<ProfilDto> getListProfilDtoFromListProfils(List<Profil> profils) {
        Logger.debug("%s getListProfilDtoFromListProfils", LOG_PREFIX);
        if (profils == null) {
            return null;
        }
        List<ProfilDto> profilDtos = new ArrayList<>();
        for (Profil profil : profils) {
            profilDtos.add(getprofilDtoFromProfil(profil));
        }
        return profilDtos;
    }

}
