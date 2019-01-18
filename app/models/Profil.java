package models;

import util.UUIDModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Profil extends UUIDModel {
    public String pseudo;
    public String avatar;
    @ManyToOne
    public Utilisateur utilisateur;
}
