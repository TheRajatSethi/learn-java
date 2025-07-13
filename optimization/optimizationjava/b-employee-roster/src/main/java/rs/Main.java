package rs;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SolverFactory<ShiftRoster> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(ShiftRoster.class)
                .withEntityClasses(ShiftAssignment.class)
                .withConstraintProviderClass(CP.class)
                .withTerminationSpentLimit(Duration.ofSeconds(2)));

        ShiftRoster problem = data();

        Solver<ShiftRoster> solver = solverFactory.buildSolver();
        ShiftRoster solution = solver.solve(problem);
        System.out.println(solution.shiftAssignments);
    }

    static ShiftRoster data(){
        ShiftRoster shiftRoster = new ShiftRoster();

        DriverManagerDataSource dataSource  = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:db.db");

        JdbcClient jdbcClient = JdbcClient.create(dataSource);

        var employees = jdbcClient.sql("Select * from employee").query(Employee.class).list();
        var scheduledVacations = jdbcClient.sql("Select * from employee_scheduled_vacation").query(EmployeeScheduledVacation.class).list();

        shiftRoster.setInputEmployees(employees);
        shiftRoster.setInputEmployeeScheduledVacations(scheduledVacations);

        List<ShiftAssignment> shiftAssignments = new ArrayList<>();

        long id = 1;
        for (int i=1; i<=10; i++){
            var s = new Shift(LocalDate.of(2024,1,i));
            shiftAssignments.add(new ShiftAssignment(id++, s));
            shiftAssignments.add(new ShiftAssignment(id++, s));
        }

        shiftRoster.setShiftAssignments(shiftAssignments);
        return shiftRoster;

    }
}
