package ets.schedule.interfaces.services;

import ets.schedule.data.HttpEntity;
import ets.schedule.data.responses.report.ReportResponse;

public interface StudentReportService {
    HttpEntity<ReportResponse> fetchStudentReport(Long profileId);
}
