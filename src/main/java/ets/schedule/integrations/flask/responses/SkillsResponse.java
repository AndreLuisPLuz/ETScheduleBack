package ets.schedule.integrations.flask.responses;

import java.util.List;

import ets.schedule.integrations.flask.data.Skill;

public record SkillsResponse(
    List<Skill> soft_skills,
    List<Skill> hard_skills
) { }
