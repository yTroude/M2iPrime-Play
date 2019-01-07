package controllers;

import play.Logger;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Controller;

import java.util.Map;

public class TrackerController extends Controller {

    @Before
    static void before() {
        Logger.info("----------------------");
        Logger.info("nav - %s", request.url);
        for (Map.Entry<String, String> entry : request.params.allSimple().entrySet()) {
            Logger.debug("params : %s = %s", entry.getKey(), entry.getValue());
        }
        Logger.info("-----");
        if (Security.isConnected()) {
            renderArgs.put("connectedUser", Security.connectedUser());
        }
        Logger.info("-----");
    }

    @After
    public static void after() {
        Logger.info("----------------------");
    }

}
