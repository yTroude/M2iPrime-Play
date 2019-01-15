package models;

import util.UUIDModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class PasswordResetRequest extends UUIDModel {

    public String email;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    public ValidationToken validationToken;
}
