package models;

import play.data.validation.Required;

public class Video extends UUIDModel {

    @Required
    public String nom;

    @Required
    public String description;

}
