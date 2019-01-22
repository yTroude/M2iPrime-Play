package util;

import play.Play;

public class Images {
    public static final String IMG_BASE_PATH="/public/images/";
    public static final String DEFAULT_AVATAR_NAME="_avatar.png";
    public static final String IMG_AVATARS_PATH="avatars/";
    public static final String[] avatars = Play.getFile(IMG_BASE_PATH+IMG_AVATARS_PATH).list();
}
