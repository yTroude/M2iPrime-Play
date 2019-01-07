package jobs;

import models.EUtilisateurRole;
import models.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class BootstrapJob extends Job {

    public void doJob() {

        if (Play.mode.isDev()) {
            Fixtures.deleteAllModels();
            //Fixtures.loadModels("data/initial-data.yml");
            Utilisateur admin = new Utilisateur();
            admin.role=EUtilisateurRole.ADMIN;
            admin.email="admin@m2iprime.com";
            admin.password=BCrypt.hashpw("admin123",BCrypt.gensalt());
            admin.save();
        }

    }

}
