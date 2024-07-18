package ets.schedule.interfaces.integrations;

import java.util.concurrent.CompletableFuture;

import ets.schedule.integrations.flask.payloads.LoginPayload;
import ets.schedule.integrations.flask.responses.DisciplinesResponse;
import ets.schedule.integrations.flask.responses.LoginResponse;
import ets.schedule.integrations.flask.responses.SkillsResponse;

public interface FlaskIntegration {
    CompletableFuture<LoginResponse> doLoginAsync(LoginPayload payload);
    CompletableFuture<SkillsResponse> getSkillsAsync(Long profileId);
    CompletableFuture<DisciplinesResponse> getDisciplinePerformancesAsync(Long profileId);
}
