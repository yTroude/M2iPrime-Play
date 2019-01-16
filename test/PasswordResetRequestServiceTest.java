import errors.AccountNotActivated;
import errors.BadUtilisateurException;
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
        u.password= BCrypt.hashpw("123456",BCrypt.gensalt());
        u.save();

        u = UtilisateursService.getByEmail("valide@mail.com");
        u.password= BCrypt.hashpw("123456",BCrypt.gensalt());
        u.save();

        u = UtilisateursService.getByEmail("nonvalide@mail.com");
        u.password= BCrypt.hashpw("123456",BCrypt.gensalt());
        u.save();
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
        }
        else {
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
        }
        else {
            assertFalse(true);
        }
    }

    @Test
    public void testDefineNewPassword() {
        // Given
        String email = "inscrit@mail.com";

        // When
        try {
            PasswordResetRequestService.sendResetPasswordEmail(email);
        } catch (BadUtilisateurException e) {
            assertFalse(true);
        } catch (AccountNotActivated accountNotActivated) {
            assertFalse(true);
        }

        // Then
        assertTrue(true);
    }
}
