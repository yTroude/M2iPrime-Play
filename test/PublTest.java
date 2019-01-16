import errors.AccountNotActivated;
import errors.BadUtilisateurException;
import org.junit.*;
import org.junit.Before;
import org.mindrot.jbcrypt.BCrypt;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import services.PasswordResetRequestService;
import services.UtilisateursService;

public class PublTest extends FunctionalTest {

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
    public void testMotDePassePerdu() {
        Response response = GET("/lostPassword");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

    @Test
    public void testSendResetPasswordEmail() {
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

    @Test
    public void testSendResetPasswordEmailBadUtilisateurException(){
        //Given
        String email = "noninscrit@mail.com";
        //When
        try {
            PasswordResetRequestService.sendResetPasswordEmail(email);
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            //Then
            assertTrue(true);
        } catch (AccountNotActivated e) {
            assertFalse(true);
        }
    }

    @Test
    public void testSendResetPasswordEmailAccountNotActivatedException(){
        //Given
        String email = "nonvalide@mail.com";
        //When
        try {
            PasswordResetRequestService.sendResetPasswordEmail(email);
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            //Then
            assertFalse(true);
        } catch (AccountNotActivated e) {
            assertTrue(true);
        }
    }
    
}