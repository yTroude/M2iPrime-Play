package jobs;

import models.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import services.UtilisateursService;

@OnApplicationStart
public class BootstrapJob extends Job {

    public void doJob() {

        if (Play.mode.isDev()) {
            Fixtures.deleteAllModels();
            Fixtures.loadModels("data/initial-data.yml");
            Utilisateur u = UtilisateursService.getByEmail("valide@mail.com");
            u.password= BCrypt.hashpw("123456",BCrypt.gensalt());
            u.save();

            u = UtilisateursService.getByEmail("inscrit@mail.com");
            u.password= BCrypt.hashpw("123456",BCrypt.gensalt());
            u.save();

            u = UtilisateursService.getByEmail("nonvalide@mail.com");
            u.password= BCrypt.hashpw("123456",BCrypt.gensalt());
            u.save();
        }
    }
}
