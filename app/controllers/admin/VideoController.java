package controllers.admin;

import controllers.Check;
import controllers.Secure;
import controllers.TrackerController;
import models.Video;
import play.Logger;
import play.data.validation.Valid;
import play.mvc.With;
import services.VideoService;

import java.util.List;
import java.util.Map;

@With (Secure.class)
@Check("ADMIN")
public class VideoController extends TrackerController {

    public static void videos() {
        List<Video> videos = VideoService.findAll();
        render(videos);
    }

    public static void uploadVideo() {
        render();
    }

    public static void saveUploadedVideo(@Valid Video video) {
        if (validation.hasErrors()) {
            for (Map.Entry entry : validation.errorsMap().entrySet()) {
                Logger.error("Admin | uploadVideo : %s = %s", entry.getKey(), entry.getValue());
            }
            params.flash();
            validation.keep();
            uploadVideo();
        }

        VideoService.uploadVideo(video);

        videos();
    }

    public static void deleteVideo(String uuid) {
        Video video = VideoService.getByUUID(uuid);
        notFoundIfNull(video);
        VideoService.deleteVideo(video);
        videos();
    }
}
