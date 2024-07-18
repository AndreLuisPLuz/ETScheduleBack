package ets.schedule.integrations.flask.responses;

import java.util.List;

import ets.schedule.integrations.flask.data.Discipline;

public record DisciplinesResponse(
    List<Discipline> disciplines
) { }
