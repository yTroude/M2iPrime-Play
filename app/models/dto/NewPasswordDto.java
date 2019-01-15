package models.dto;

import play.data.validation.Required;

public class NewPasswordDto {
    @Required
    public String passwordResetRequestUuid;
    @Required
    public String password;
    @Required
    public String passwordConfirmation;
    public String validationTokenUuid;
}
