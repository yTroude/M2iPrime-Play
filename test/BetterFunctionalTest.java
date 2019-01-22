import play.mvc.Http;
import play.test.FunctionalTest;

import java.net.MalformedURLException;
import java.util.Map;

public class BetterFunctionalTest extends FunctionalTest {

    protected Http.Response POST(String url, Map<String, String> parameters, boolean followRedirect) {
        Http.Response response = POST(url, parameters);
        if (Http.StatusCode.FOUND == response.status && followRedirect) {
            Http.Header redirectedTo = response.headers.get("Location");
            String location = redirectedTo.value();
            if (location.contains("http")) {
                java.net.URL redirectedUrl;
                try {
                    redirectedUrl = new java.net.URL(redirectedTo.value());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                response = GET(redirectedUrl.getPath());
            } else {
                response = GET(location);
            }
        }
        return response;
    }

}
