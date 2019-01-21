import errors.*;
import models.PasswordResetRequest;
import models.Utilisateur;
import models.dto.InscriptionDto;
import models.dto.NewPasswordDto;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import play.test.Fixtures;
import play.test.UnitTest;
import services.PasswordResetRequestService;
import services.UtilisateursService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UtilisateursServiceTest extends UnitTest {

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
    public void testCreerUtilisateur(){
        //Given
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.email = "toto@mail.com";
        inscriptionDto.password = "totototo";
        LocalDate ld = LocalDate.now().minusYears(16);
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        inscriptionDto.dateNaissance = date;

        //When
        try {
            UtilisateursService.creerUtilisateur(inscriptionDto);
            //Then
            assertTrue(true);
        } catch (UtilisateurExisteException e) {
            assertFalse(true);
        } catch (UtilisateurTropJeuneException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testCreerUtilisateurUtilisateurExisteException(){
        //Given
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.email = "inscrit@mail.com";
        inscriptionDto.password = "totototo";
        LocalDate ld = LocalDate.now().minusYears(16);
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        inscriptionDto.dateNaissance = date;

        //When
        try {
            UtilisateursService.creerUtilisateur(inscriptionDto);
            assertFalse(true);
        } catch (UtilisateurExisteException e) {
            //Then
            assertTrue(true);
        } catch (UtilisateurTropJeuneException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testCreerUtilisateurUtilisateurTropJeuneException(){
        //Given
        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.email = "toto@mail.com";
        inscriptionDto.password = "totototo";
        LocalDate ld = LocalDate.now().minusYears(15);
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        inscriptionDto.dateNaissance = date;

        //When
        try {
            UtilisateursService.creerUtilisateur(inscriptionDto);
            assertFalse(true);
        } catch (UtilisateurExisteException e) {
            assertFalse(true);
        } catch (UtilisateurTropJeuneException e) {
            //Then
            assertTrue(true);
        }
    }

    @Test
    public void testConfirmerUtilisateur(){
        //Given
        Utilisateur jeanbon = UtilisateursService.getByEmail("nonvalide@mail.com");
        String utilisateurUuid = jeanbon.uuid;
        String validationTokenUuid = jeanbon.validationToken.uuid;

        //When
        try {
            UtilisateursService.confirmerUtilisateur(utilisateurUuid, validationTokenUuid);
            //Then
            assertTrue(true);
        } catch (BadUtilisateurException e) {
            assertFalse(true);
        } catch (BadValidationTokenException e) {
            assertFalse(true);
        } catch (AccountAlreadyActivated accountAlreadyActivated) {
            assertFalse(true);
        }
    }

    @Test
    public void testConfirmerUtilisateurBadUtilisateurException(){
        //Given
        Utilisateur jeanbon = UtilisateursService.getByEmail("nonvalide@mail.com");
        String utilisateurUuid = "123";
        String validationTokenUuid = jeanbon.validationToken.uuid;

        //When
        try {
            UtilisateursService.confirmerUtilisateur(utilisateurUuid, validationTokenUuid);
            //Then
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            assertTrue(true);
        } catch (BadValidationTokenException e) {
            assertFalse(true);
        } catch (AccountAlreadyActivated accountAlreadyActivated) {
            assertFalse(true);
        }
    }

    @Test
    public void testConfirmerUtilisateurAccountAlreadyActivated(){
        //Given
        Utilisateur megabob = UtilisateursService.getByEmail("inscrit@mail.com");
        String utilisateurUuid = megabob.uuid;
        String validationTokenUuid = megabob.validationToken.uuid;

        //When
        try {
            UtilisateursService.confirmerUtilisateur(utilisateurUuid, validationTokenUuid);
            //Then
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            assertTrue(true);
        } catch (BadValidationTokenException e) {
            assertFalse(true);
        } catch (AccountAlreadyActivated accountAlreadyActivated) {
            assertTrue(true);
        }
    }
    @Test
    public void testConfirmerUtilisateurBadValidationTokenException(){
        //Given
        Utilisateur jeanbon = UtilisateursService.getByEmail("nonvalide@mail.com");
        String utilisateurUuid = jeanbon.uuid;
        String validationTokenUuid = "123";

        //When
        try {
            UtilisateursService.confirmerUtilisateur(utilisateurUuid, validationTokenUuid);
            //Then
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            assertTrue(true);
        } catch (BadValidationTokenException e) {
            assertTrue(true);
        } catch (AccountAlreadyActivated accountAlreadyActivated) {
            assertFalse(true);
        }
    }

    @Test
    public void testRenvoiEmailActivationDeCompte(){
        //Given
        String email = "nonvalide@mail.com";

        //When
        try {
            UtilisateursService.renvoiEmailActivationDeCompte(email);
            //Then
            assertTrue(true);
        } catch (BadUtilisateurException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testRenvoiEmailActivationDeCompteBadUtilisateurException(){
        //Given
        String email = "123@mail.com";

        //When
        try {
            UtilisateursService.renvoiEmailActivationDeCompte(email);
            //Then
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetByEmail(){
        //Given
        String email = "inscrit@mail.com";

        //When
        Utilisateur utilisateur = UtilisateursService.getByEmail(email);

        //Then
        if (utilisateur.email.equals(email)){
            assertTrue(true);
        }
        else {
            assertFalse(true);
        }
    }

    @Test
    public void testGetByPseudo(){
        //Given
        String pseudo = "inscrit";

        //When
        Utilisateur utilisateur = UtilisateursService.getByPseudo(pseudo);

        //Then
        if (utilisateur.pseudo.equals(pseudo)){
            assertTrue(true);
        }
        else {
            assertFalse(true);
        }
    }

    @Test
    public void testGetByUuid(){
        //Given
        Utilisateur tmp = UtilisateursService.getByPseudo("inscrit");

        //When
        Utilisateur utilisateur = UtilisateursService.getByUuid(tmp.uuid);

        //Then
        if (utilisateur.uuid.equals(tmp.uuid)){
            assertTrue(true);
        }
        else {
            assertFalse(true);
        }
    }

    @Test
    public void testValidateNewPassword(){
        //Given
        PasswordResetRequest megabobPwdResetRequest = PasswordResetRequestService.getByEmail("inscrit@mail.com");
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.passwordResetRequestUuid = megabobPwdResetRequest.uuid;
        newPasswordDto.validationTokenUuid = megabobPwdResetRequest.validationToken.uuid;
        newPasswordDto.password = "666";
        newPasswordDto.passwordConfirmation = "666";

        //When
        try {
            UtilisateursService.validateNewPassword(newPasswordDto);
            assertTrue(true);
        } catch (PasswordConfirmationException e) {
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            assertFalse(true);
        } catch (BadPasswordResetRequestException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testValidateNewPasswordBadPasswordResetRequestException(){
        //Given
        PasswordResetRequest megabobPwdResetRequest = PasswordResetRequestService.getByEmail("inscrit@mail.com");
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.passwordResetRequestUuid = "123";
        newPasswordDto.validationTokenUuid = megabobPwdResetRequest.validationToken.uuid;
        newPasswordDto.password = "666";
        newPasswordDto.passwordConfirmation = "666";

        //When
        try {
            UtilisateursService.validateNewPassword(newPasswordDto);
            assertFalse(true);
        } catch (PasswordConfirmationException e) {
            assertFalse(true);
        } catch (BadUtilisateurException e) {
            assertFalse(true);
        } catch (BadPasswordResetRequestException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testValidateNewPasswordPasswordConfirmationException(){
        //Given
        PasswordResetRequest megabobPwdResetRequest = PasswordResetRequestService.getByEmail("inscrit@mail.com");
        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.passwordResetRequestUuid = megabobPwdResetRequest.uuid;
        newPasswordDto.validationTokenUuid = megabobPwdResetRequest.validationToken.uuid;
        newPasswordDto.password = "666";
        newPasswordDto.passwordConfirmation = "000000";

        //When
        try {
            UtilisateursService.validateNewPassword(newPasswordDto);
            assertFalse(true);
        } catch (PasswordConfirmationException e) {
            assertTrue(true);
        } catch (BadUtilisateurException e) {
            assertFalse(true);
        } catch (BadPasswordResetRequestException e) {
            assertFalse(true);
        }
    }
}
