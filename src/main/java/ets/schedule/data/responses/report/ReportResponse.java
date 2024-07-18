package ets.schedule.data.responses.report;

import java.util.List;
import java.util.stream.Collectors;

import ets.schedule.integrations.flask.data.Discipline;
import ets.schedule.integrations.flask.data.Skill;
import ets.schedule.integrations.flask.responses.DisciplinesResponse;
import ets.schedule.integrations.flask.responses.SkillsResponse;
import ets.schedule.models.Profiles;

public record ReportResponse(
    String student,
    String group,
    List<Skill> softSkills,
    List<Skill> hardSkills,
    List<Performance> performances
) {
    final record Performance(
        String discipline,
        float average
    ) {
        protected static Performance buildFromDiscipline(Discipline discipline) {
            return new Performance(
                discipline.discipline_name(),
                discipline.stats().competences_average()
            );
        }
    }

    public static ReportResponse buildFromFlaskResponses(
            Profiles studentProfile,
            SkillsResponse skillsResponse,
            DisciplinesResponse disciplinesResponse
    ) {
        var performances = disciplinesResponse.disciplines()
            .stream()
            .map(d -> 
                Performance.buildFromDiscipline(d))
            .collect(Collectors.toList());
        
        return new ReportResponse(
            studentProfile.getUser().getFullName(),
            studentProfile.getGroup().getName(),
            skillsResponse.soft_skills(),
            skillsResponse.hard_skills(),
            performances);
    }
}
