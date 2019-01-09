package util;

import play.Play;

public class Images {
    public static final String DEFAULT_AVATAR_NAME="_avatar.png";
    public static final String[] avatars = Play.getFile("public/images/avatars").list();


}
