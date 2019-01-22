package models;

import play.db.jpa.Model;
import play.libs.Codec;

import javax.persistence.Entity;

import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Video extends UUIDModel {

    public String titre;
    public String desc;
    public String categorie;
    public String format;
    public String image;

    @ManyToMany
    public List<Acteur>acteurs;
}
