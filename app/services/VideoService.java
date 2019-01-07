package services;

import models.Video;
import play.Logger;

import java.util.List;

public class VideoService {

    public static final String LOG_PREFIX = "VideoService | ";

    public static List<Video> findAll() {
        Logger.debug("%s findAll", LOG_PREFIX);
        return Video.findAll();
    }

    public static void uploadVideo(Video video) {
        Logger.debug("%s uploadVideo : [%s / %s]", LOG_PREFIX, video.nom, video.description);
        video.save();
    }

    public static Video getByUUID(String uuid) {
        Logger.debug("%s getByUUID : [%s]", LOG_PREFIX, uuid);
        return Video.findById(uuid);
    }

    public static void deleteVideo(Video video) {
        Logger.debug("%s deleteVideo : [%s]", LOG_PREFIX, video.uuid);
        video.delete();
    }
}
