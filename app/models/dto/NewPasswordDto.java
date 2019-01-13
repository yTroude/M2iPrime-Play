package models.dto;

import play.data.validation.Required;

public class NewPasswordDto {
    @Required
    public String email;
    @Required
    public String password;
    @Required
    public String passwordConfirmation;
}
