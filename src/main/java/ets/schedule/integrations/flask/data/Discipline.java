package ets.schedule.integrations.flask.data;

public record Discipline(
    String discipline_name,
    DisciplineStats stats
) {
    public final record DisciplineStats(
        float competences_average,
        String semester
    ) { }
}
