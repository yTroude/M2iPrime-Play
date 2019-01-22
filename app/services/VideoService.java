package services;

import models.Acteur;
import models.Video;
import play.Logger;

import java.util.List;

public class VideoService {

    public static final String LOG_PREFIX = "VideoService | ";


    public static List<Video> findAll(){
        List<Video> films = Video.findAll();
        return films ;
    }
    public static List<Video> findVideoByFormat(String format){

        List<Video> films = Video.find("format=?1",format).fetch();
        return films ;
    }



    public static List<Video> findVideosByCategorie(String categorie){
        Logger.debug("%s findVideosByCategorie : [%s ]", LOG_PREFIX, categorie);
        List<Video>video= Video.find("categorie=?1",categorie).fetch();
        return video;
    }

    public static void addVideo(String titre, String desc, String categorie, String format, String image){
        Video video = new Video();
        video.desc = desc;
        video.categorie = categorie;
        video.titre = titre;
        video.format = format;
        video.image = image;
        video.save();
    }


    public static Video getVideoByUuid(String uuid){

        return Video.findById(uuid);
    }


    public static List<Video> findVideoByFormatAndCategorie(String format, String categorie) {
        List<Video> videos = Video.find("format=?1 AND categorie=?2", format, categorie).fetch();
        return videos;
    }

    public static void addActeurToVideo(String uuidActeur, String uuidVideo){
        Logger.debug("%s addActeurToVideo : [%s, %s ]", LOG_PREFIX, uuidActeur, uuidVideo);
        Video video = getVideoByUuid(uuidVideo);

        Acteur acteur = ActeurService.getActeurByUuid(uuidActeur) ;

        video.acteurs.add(acteur);
        video.save();

    }
}
