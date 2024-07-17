package ets.schedule.integrations.flask;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;

public class FlaskIntegration {
    @Value("info.external.flask.baseurl")
    private String baseUrl;
}
