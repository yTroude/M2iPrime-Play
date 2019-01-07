package models;

import util.UUIDModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class Utilisateur extends UUIDModel {
    @Column(nullable = false, unique = true)
    public String email;
    public String nom;
    public String prenom;
    public Date dateNaissance;
    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    public ValidationToken validationToken;
    public boolean valid;
    public String password;
}
