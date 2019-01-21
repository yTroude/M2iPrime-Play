import errors.*;
import models.PasswordResetRequest;
import models.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import play.test.Fixtures;
import play.test.UnitTest;
import services.PasswordResetRequestService;
import services.UtilisateursService;

public class PasswordResetRequestServiceTest extends UnitTest {

    @Before
    public void setUpDatabase() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("data.yml");
        Utilisateur u = UtilisateursService.getByEmail("inscrit@mail.com");
        u.password = BCrypt.hashpw("123456", BCrypt.gensalt());
        u.save();

        u = UtilisateursService.getByEmail("valide@mail.com");
        u.password = BCrypt.hashpw("123456", BCrypt.gensalt());
        u.save();

        u = UtilisateursService.getByEmail("nonvalide@mail.com");
        u.password = BCrypt.hashpw("123456", BCrypt.gensalt());
        u.save();
    }

    @Test
    public void testSendResetPasswordEmail() {
        // Given
        String email = "inscrit@mail.com";

        // When
        try {
            PasswordResetRequestService.sendResetPasswordEmail(email);
            //Then
            assertTrue(true);
        } catch (BadUtilisateurException e) {
            assertFalse(true);
        } catch (AccountNotActivated accountNotActivated) {
            assertFalse(true);
        }
    }

    @Test
    public void testSendResetPasswordEmailBadUtilisateurException() {
        // Given
        String email = "noninscrit@mail.com";

        // When
        Utilisateur utilisateur = UtilisateursService.getByEmail(email);

        // Then
        if (utilisateur == null) {
            assertTrue(true);
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testSendResetPasswordEmailAccountNotActivated() {
        // Given
        String email = "nonvalide@mail.com";

        // When
        Utilisateur utilisateur = UtilisateursService.getByEmail(email);

        // Then
        if (utilisateur.valid == false) {
            assertTrue(true);
        } else {
            assertFalse(true);
        }
    }

    @Test
    public void testDefineNewPassword() {
        // Given
        PasswordResetRequest megabobPwdResetRequest = PasswordResetRequestService.getByEmail("inscrit@mail.com");
        String passwordResetRequestUuid = megabobPwdResetRequest.uuid;
        String validationTokenUuid = megabobPwdResetRequest.validationToken.uuid;

        // When
        try {
            PasswordResetRequestService.defineNewPassword(passwordResetRequestUuid, validationTokenUuid);
            // Then
            assertTrue(true);
        } catch (BadValidationTokenException e) {
            assertFalse(true);
        } catch (ValidationTokenExpiredException e) {
            assertFalse(true);
        } catch (BadPasswordResetRequestException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testDefineNewPasswordValidationTokenExpiredException() {
        // Given
        PasswordResetRequest megabobPwdResetRequest = PasswordResetRequestService.getByEmail("inscrit@mail.com");
        String passwordResetRequestUuid = megabobPwdResetRequest.uuid;
        String validationTokenUuid = megabobPwdResetRequest.validationToken.uuid;

        // When
        try {
            PasswordResetRequestService.defineNewPassword(passwordResetRequestUuid, validationTokenUuid);
            // Then
            assertFalse(true);
        } catch (BadValidationTokenException e) {
            assertFalse(true);
        } catch (ValidationTokenExpiredException e) {
            assertTrue(true);
        } catch (BadPasswordResetRequestException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testDefineNewPasswordBadValidationTokenException() {
        // Given
        PasswordResetRequest megabobPwdResetRequest = PasswordResetRequestService.getByEmail("inscrit@mail.com");
        String passwordResetRequestUuid = megabobPwdResetRequest.uuid;
        String validationTokenUuid = "123";

        // When
        try {
            PasswordResetRequestService.defineNewPassword(passwordResetRequestUuid, validationTokenUuid);
            // Then
            assertFalse(true);
        } catch (BadValidationTokenException e) {
            assertTrue(true);
        } catch (ValidationTokenExpiredException e) {
            assertFalse(true);
        } catch (BadPasswordResetRequestException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testDefineNewPasswordBadPasswordResetRequestException() {
        // Given
        PasswordResetRequest megabobPwdResetRequest = PasswordResetRequestService.getByEmail("inscrit@mail.com");
        String passwordResetRequestUuid = "123";
        String validationTokenUuid = megabobPwdResetRequest.validationToken.uuid;

        // When
        try {
            PasswordResetRequestService.defineNewPassword(passwordResetRequestUuid, validationTokenUuid);
            // Then
            assertFalse(true);
        } catch (BadValidationTokenException e) {
            assertFalse(true);
        } catch (ValidationTokenExpiredException e) {
            assertFalse(true);
        } catch (BadPasswordResetRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetByEmail(){
        //Given
        String email = "inscrit@mail.com";

        //When
        PasswordResetRequest passwordResetRequest = PasswordResetRequestService.getByEmail(email);

        //Then
        if (passwordResetRequest != null){
            assertTrue(true);
        }
        else {
            assertFalse(true);
        }
    }

}
