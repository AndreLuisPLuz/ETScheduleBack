package ets.schedule.services;

import java.util.concurrent.ExecutionException;

import ets.schedule.repositories.ProfilesJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.responses.report.ReportResponse;
import ets.schedule.integrations.flask.responses.DisciplinesResponse;
import ets.schedule.integrations.flask.responses.SkillsResponse;
import ets.schedule.interfaces.integrations.FlaskIntegration;
import ets.schedule.interfaces.services.StudentReportService;

public class DefaultStudentReportService implements StudentReportService {
    @Autowired
    FlaskIntegration flask;

    @Autowired
    ProfilesJPARepository profileRepo;

	@Override
	public HttpEntity<ReportResponse> fetchStudentReport(Long profileId) {
        SkillsResponse skillsResponse;
        DisciplinesResponse disciplinesResponse;

        try {
            skillsResponse = flask.getSkillsAsync(profileId).get();
            disciplinesResponse = flask.getDisciplinePerformancesAsync(profileId).get();
        } catch (InterruptedException | ExecutionException ex) {
            throw new ApplicationException(503, "Service unavailable.");
        }

        var profileFetch = profileRepo.findById(profileId);

        if (!profileFetch.isPresent())
            throw new ApplicationException(404, "Student not found.");

        var profile = profileFetch.get();

        return new HttpEntity<ReportResponse>(
            HttpStatusCode.valueOf(200),
            ReportResponse.buildFromFlaskResponses(
                profile,
                skillsResponse,
                disciplinesResponse
        ));
	}
}
