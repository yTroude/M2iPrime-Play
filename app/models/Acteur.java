package models;

import play.db.jpa.Model;
import play.libs.Codec;

import javax.persistence.Entity;

import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Acteur extends UUIDModel {

    public String nomActeur;
    public String prenomActeur;

    @ManyToMany(mappedBy = "acteurs")
    public List<Video> videos;


}
