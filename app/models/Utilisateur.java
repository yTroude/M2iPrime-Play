package models;

import util.UUIDModel;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Utilisateur extends UUIDModel {
    @Column(nullable = false, unique = true)
    public String email;
    public String nom;
    public String prenom;
    @Enumerated(EnumType.STRING)
    public EUtilisateurRole role;
    public Date dateNaissance;
    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    public ValidationToken validationToken;
    public boolean valid;
    public String password;
}
