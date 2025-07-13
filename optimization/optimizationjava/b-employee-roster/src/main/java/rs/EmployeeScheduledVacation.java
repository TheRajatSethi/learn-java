package rs;

import java.time.LocalDate;

public record EmployeeScheduledVacation(long id, long employeeId, LocalDate date) {}
