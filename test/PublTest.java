import controllers.publ.Publ;
import errors.*;
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
    public void testIndexUnauthentifiedUser() {
        Response response = GET("/");
        assertStatus(302, response);
    }

    @Test
    public void testMotDePassePerdu() {
        Response response = GET("/lostPassword");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

//    @Test
//    public void testSendResetPasswordEmail() {
//        // Given
//        String email = "inscrit@mail.com";
//
//        // When
//        Publ.sendResetPasswordEmail(email);
//
//        // Then
//        Response response = POST("/lostPassword/submit");
//        assertIsOk(response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//    }
//
//    @Test(expected = BadUtilisateurException.class)
//    public void testSendResetPasswordEmailBadUtilisateurException(){
//        //Given
//        String email = "noninscrit@mail.com";
//        // When
//        Publ.sendResetPasswordEmail(email);
//        //Then => BadUtilisateurException
//    }
//
//    @Test(expected = AccountNotActivated.class)
//    public void testSendResetPasswordEmailAccountNotActivatedException(){
//        //Given
//        String email = "nonvalide@mail.com";
//        // When
//        Publ.sendResetPasswordEmail(email);
//        //Then => AccountNotActivatedException
//    }
//
//    @Test
//    public void testDefineNewPassword(){
//        //Given
//        String passwordResetRequestUuid = "passwordResetRequestInscrit";
//        String validationTokenUuid = "validationPwdResetRequestInscrit";
//        //When
//        Publ.defineNewPassword(passwordResetRequestUuid, validationTokenUuid);
//
//        // Then
//        Response response = POST("/lostPassword/defineNewPassword/{passwordResetRequestUuid}/{validationTokenUuid}");
//        assertIsOk(response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//    }
}