package controllers.api;
import controllers.TrackerController;
import models.Acteur;
import models.Video;
import models.adapters.ActeurAdapter;
import models.adapters.VideoAdapter;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.results.NotFound;
import play.mvc.results.RenderJson;
import services.ActeurService;
import services.VideoService;

import java.util.List;


public class ApiController extends TrackerController {


    public static void allVideos(){
        List<Video> videos = VideoService.findAll();
        renderJSON(videos);
    }

   /* public static void allFilms(){
        Logger.info("allFilms");
        List<Video> videos = VideoService.findAllFilms();
        Logger.debug("Films : %d", videos.size());
        renderJSON(videos);
    }

    public static void allSeries(){
        List<Video>videos = VideoService.findAllSeries();
        renderJSON(videos);
    }
*/

   public static void filterByFormat(String format){
       List<Video>videos = VideoService.findVideoByFormat(format);
       renderJSON(videos);
   }

    public static void detailsVideo(String uuid){
        Video details = VideoService.getVideoByUuid(uuid);
        renderJSON(details, ActeurAdapter.get());
    }


    public static void ajouterVideo(String titre, String desc, String categorie, String format){
        VideoService.addVideo(titre,desc,categorie, format);
        Http.Response.current().status=Http.StatusCode.CREATED;
    }


    public static void filterByCategorie(String categorie){
        List<Video>videos = VideoService.findVideosByCategorie(categorie);
        renderJSON(videos);
    }

   /* public static void allFilmsByCategorie(String categorie){
        List<Video>videos = VideoService.findFilmsByCategorie(categorie);
        renderJSON(videos);
    }

    public static void allSeriesByCategorie(String categorie){
        List<Video>videos = VideoService.findSeriesByCategorie(categorie);
        renderJSON(videos);
    }
*/
   public static void filterByFormatAndCategorie(String format, String categorie){
       List<Video> videos = VideoService.findVideoByFormatAndCategorie(format, categorie);
       renderJSON(videos);
   }

    /*public static void allVideoByActeur(String acteur){
        List<Video>videos = VideoService.getVideoByActeur(acteur);
        renderJSON(videos);
    }
*/

    private static void renderJSONError(Http.Response response, Integer status, Object o) {
        response.status = status;
        throw new RenderJson(o);
    }

    public static void detailsActeur(String uuid){
        Acteur details = ActeurService.getActeurByUuid(uuid);
        renderJSON(details, VideoAdapter.get());
    }


    public static void addActeur(String nomActeur, String prenomActeur){
        ActeurService.addActeur(nomActeur, prenomActeur);
    }

    public static void addActeurToVideo(String uuidActeur, String uuidVideo){
        VideoService.addActeurToVideo(uuidActeur, uuidVideo);
    }

    public static void addVideoToActeur(String uuidVideo, String uuidActeur ){
        VideoService.addActeurToVideo(uuidVideo, uuidActeur);
    }

}
