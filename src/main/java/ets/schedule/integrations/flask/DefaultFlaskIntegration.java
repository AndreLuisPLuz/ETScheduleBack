package ets.schedule.integrations.flask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.integrations.flask.payloads.LoginPayload;
import ets.schedule.integrations.flask.responses.DisciplinesResponse;
import ets.schedule.integrations.flask.responses.LoginResponse;
import ets.schedule.integrations.flask.responses.SkillsResponse;
import ets.schedule.interfaces.integrations.FlaskIntegration;

public class DefaultFlaskIntegration implements FlaskIntegration {
    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers = null;

    @Value("${info.external.flask.username}")
    private String username;
    
    @Value("${info.external.flask.password}")
    private String password;
    
    @Value("${info.external.flask.baseurl}")
    private String baseUrl;

    @Value("${info.external.flask.routes.login}")
    private String loginRoute;

    @Value("${info.external.flask.routes.skills}")
    private String skillsRoute;

    @Value("${info.external.flask.routes.disciplines}")
    private String disciplinesRoute;

    private void assignHeaders() {
        LoginResponse response;
        try {
            var payload = new LoginPayload(username, password);
            response = doLoginAsync(payload).get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new ApplicationException(503, "Service unavailable.");
        }

        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + response.token());
    }

    @Override
    public CompletableFuture<LoginResponse> doLoginAsync(LoginPayload payload) {
        return CompletableFuture.supplyAsync(() -> {
            LoginResponse response;
            try {
                var endpoint = baseUrl + loginRoute;
                response = restTemplate.postForObject(endpoint, payload, LoginResponse.class);
            } catch (RestClientException ex) {
                throw new ApplicationException(503, ex.getMessage());
            }

            return response;
        });
    }

	@Override
	public CompletableFuture<SkillsResponse> getSkillsAsync(Long profileId) {
		if (headers == null)
            assignHeaders();

        return CompletableFuture.supplyAsync(() -> {
            var uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + skillsRoute);
            uriBuilder.pathSegment(profileId.toString());
    
            var endpoint = uriBuilder.toUriString();

            SkillsResponse response;
            try {
                response = restTemplate.exchange(
                        endpoint,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        SkillsResponse.class)
                    .getBody();
            } catch (RestClientException ex) {
                throw new ApplicationException(503, ex.getMessage());
            }

            return response;
        });
	}

	@Override
	public CompletableFuture<DisciplinesResponse> getDisciplinePerformancesAsync(Long profileId) {
		if (headers == null)
            assignHeaders();
        
        return CompletableFuture.supplyAsync(() -> {
            var uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + disciplinesRoute);
            uriBuilder.pathSegment(profileId.toString());
    
            var endpoint = uriBuilder.toUriString();

            DisciplinesResponse response;
            try {
                response = restTemplate.exchange(
                        endpoint,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        DisciplinesResponse.class)
                    .getBody();
            } catch (RestClientException ex) {
                throw new ApplicationException(503, ex.getMessage());
            }

            return response;
        });
	}
}
