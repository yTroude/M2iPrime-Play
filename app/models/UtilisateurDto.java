package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
@Table(indexes = {@Index(name = "IDX_EMAIL_PASSWORD", columnList="email,password")})
public class UtilisateurDto extends Model {

    public String email;

    public String nom;

    public String prenom;

    public String password;

    public String profile;

}
