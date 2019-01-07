package models.dto;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;


public class InscriptionDto {
    @Required
    public String email;
    @Required
    public String password;
    @Required
    public Date dateNaissance;

}
