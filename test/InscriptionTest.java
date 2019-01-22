import controllers.publ.Inscription;
import errors.UtilisateurExisteException;
import models.Utilisateur;
import models.dto.InscriptionDto;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import play.mvc.Http;
import play.test.Fixtures;
import play.test.FunctionalTest;
import services.UtilisateursService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InscriptionTest extends BetterFunctionalTest {

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
        //When
        Http.Response response = GET("/inscription");

        //Then
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

    @Test
    public void testInscription(){
        //Given
        Map<String, String> parameters = new HashMap<>();
        parameters.put("inscriptionDto.email",  "toto@mail.com");
        parameters.put("inscriptionDto.password",  "totototo");
        LocalDate ld = LocalDate.now().minusYears(25);
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        parameters.put("inscriptionDto.dateNaissance",  new DateTime(date).toString("yyyy-MM-dd"));

        //Debug
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        //When
        Http.Response response = POST("/inscription/submit", parameters);

        //Then
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
//
//    @Test
//    public void testInscriptionNoEmail(){
//        //Given
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("inscriptionDto.password",  "totototo");
//        LocalDate ld = LocalDate.now().minusYears(25);
//        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        parameters.put("inscriptionDto.dateNaissance",  new DateTime(date).toString("yyyy-MM-dd"));
//
//        //Debug
//        for (Map.Entry<String, String> entry : parameters.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//
//        //When
//        Http.Response response = POST("/inscription/submit", parameters);
//
//        //Then
//        assertStatus(302, response);
////        assertContentType("text/html", response);
////        assertCharset(play.Play.defaultWebEncoding, response);
//    }

//    @Test
//    public void testInscriptionUtilisateurExiste(){
//        //Given
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("inscriptionDto.email",  "inscrit@mail.com");
//        parameters.put("inscriptionDto.password",  "totototo");
//        LocalDate ld = LocalDate.now().minusYears(25);
//        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        parameters.put("inscriptionDto.dateNaissance",  new DateTime(date).toString("yyyy-MM-dd"));
//
//        //Debug
//        for (Map.Entry<String, String> entry : parameters.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//
//        //When
//        Http.Response response = POST("/inscription/submit", parameters);
//
//        //Then
//        assertStatus(302, response);
//        assertEquals("/inscription", response.headers.get("Location").value());
//
//        // FollowRedirect
//        response = GET(response.headers.get("Location").value());
//        try {
//            String content =response.out.toString("utf-8");
//            System.out.println(content);
//            assertTrue(content.contains("Cet email est déjà utilisé."));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////        assertContentType("text/html", response);
////        assertCharset(play.Play.defaultWebEncoding, response);
//    }

    @Test
    public void testInscriptionUtilisateurTropJeune(){
        //Given
        Map<String, String> parameters = new HashMap<>();
        parameters.put("inscriptionDto.email",  "tatatata@mail.com");
        parameters.put("inscriptionDto.password",  "totototo");
        LocalDate ld = LocalDate.now().minusYears(14);
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        parameters.put("inscriptionDto.dateNaissance",  new DateTime(date).toString("yyyy-MM-dd"));

        //Debug
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        //When
        Http.Response response = POST("/inscription/submit", parameters);

        //Then
        assertStatus(302, response);
        assertEquals("/inscription", response.headers.get("Location").value());

        // FollowRedirect
        response = GET(response.headers.get("Location").value());
        try {
            String content = response.out.toString("utf-8");
            System.out.println(content);
            assertTrue(content.contains("Vous devez avoir plus de 16 ans pour vous inscrire"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void testConfirmerInscription(){
//        //Given
//        Utilisateur jeanbon = UtilisateursService.getByEmail("nonvalide@mail.com");
//        String utilisateurUuid = jeanbon.uuid;
//        String validationTokenUuid = jeanbon.validationToken.uuid;
//        System.out.println(utilisateurUuid);
//        System.out.println(validationTokenUuid);
//
//        //When
//        Http.Response response = GET("/inscription/confirm/"+ utilisateurUuid +"/"+ validationTokenUuid);
//
//        //Then
//        assertIsOk(response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//        String content = null;
//        try {
//            content = response.out.toString("utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(content);
//        assertTrue(content.contains("Vous devez avoir plus de 16 ans pour vous inscrire"));
//    }

//    @Test
//    public void testConfirmerInscriptionBadUtilisateurException(){
//        //Given
//        Utilisateur jeanbon = UtilisateursService.getByEmail("nonvalide@mail.com");
//        String utilisateurUuid = jeanbon.uuid;
//        String validationTokenUuid = jeanbon.validationToken.uuid;
//        String url = "/inscription/confirm/"+ utilisateurUuid +"/"+ validationTokenUuid;
//        jeanbon.delete();
//
//        //When
//
//        Http.Response response = GET(url);
//
//        //Then
//        assertIsOk(response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//    }
//
//    @Test
//    public void renvoiEmailActivationDeCompte() {
//        //Given
//        String email = "nonvalide@mail.com";
//
//        //When
//        Inscription.renvoiEmailActivationDeCompte(email);
//
//        //Then
//        Http.Response response = GET("/inscription/resentEmail");
//        assertIsOk(response);
//        assertContentType("text/html", response);
//        assertCharset(play.Play.defaultWebEncoding, response);
//    }
}
