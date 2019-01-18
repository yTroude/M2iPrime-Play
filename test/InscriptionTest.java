import controllers.publ.Inscription;
import models.Utilisateur;
import models.dto.InscriptionDto;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import play.mvc.Http;
import play.test.Fixtures;
import play.test.FunctionalTest;
import services.UtilisateursService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InscriptionTest extends FunctionalTest {

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
    public void testFormulaireInscription() {
        Http.Response response = GET("/inscription");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

//    @Test
//    public void testInscription(){
//        //Given
//        InscriptionDto inscriptionDto = new InscriptionDto();
//        inscriptionDto.email = "toto@mail.com";
//        inscriptionDto.password = "totototo";
//        LocalDate ld = LocalDate.now().minusYears(16);
//        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        inscriptionDto.dateNaissance = date;
//
//        //When
//        Inscription.inscription(inscriptionDto);
//
//        //Then
//        Http.Response response = GET("/inscription/submit");
//        assertIsOk(response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//    }
}
