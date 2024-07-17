package ets.schedule.integrations.flask;

import org.springframework.beans.factory.annotation.Value;

public class FlaskIntegration {
    @Value("info.external.flask.baseurl")
    private String baseUrl;
}
