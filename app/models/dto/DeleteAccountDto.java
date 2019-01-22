package models.dto;

import play.data.validation.Required;

public class DeleteAccountDto {
    @Required
    public String email;
    @Required
    public String validationTokenUuid;
    @Required
    public String password;
    @Required
    public String passwordConfirmation;
}
