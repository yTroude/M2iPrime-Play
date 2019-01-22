package models.dto;


import play.data.validation.MinSize;
import play.data.validation.Required;

import java.util.Date;


public class InscriptionDto {
    @Required
    public String email;
    @MinSize(3)
    @Required
    public String password;
    @Required
    public Date dateNaissance;

}
