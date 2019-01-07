package models;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = {@Index(name = "IDX_ADMIN_EMAIL", columnList = "email")})
public class Admin extends Personne {

    public String email;
}
