package models;

import util.UUIDModel;
import util.ValidationStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Utilisateur extends UUIDModel {
    @Column(nullable = false, unique = true)
    public String email;
    public Date dateNaissance;
    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    public ValidationToken validationToken;
    @Enumerated(EnumType.STRING)
    public ValidationStatus validationStatus;
    public String password;
    @OneToMany(mappedBy = "utilisateur")
    public List<Profil> profils;
}
