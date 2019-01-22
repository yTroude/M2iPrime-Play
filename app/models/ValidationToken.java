package models;

import util.UUIDModel;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ValidationToken extends UUIDModel {
    public Date dateCreation;
}
