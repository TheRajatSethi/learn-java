package rs;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

enum Language {
    ENGLISH,
    FRENCH,
    HINDI,
    PUNJABI
}

enum Level{
    MANAGER,
    EMPLOYEE,
}

@NoArgsConstructor
@Data
class Employee{
    int id;
    String name;
    Level level;
    LocalTime startAvailability;
    LocalTime endAvailability;
    LocalTime preferredStartAvailability;
    LocalTime preferredEndAvailability;
    Integer age;
    Language[] languagesSpoken;
    String[] skills;
    Double latitude;
    Double longitude;

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }
}


@Data
@NoArgsConstructor
@PlanningEntity
class Shift{
    @PlanningId
    int id;
    int group;
    LocalTime startTime;
    LocalTime endTime;
    Level level;
    Double latitude;
    Double longitude;


    @PlanningVariable
    Employee employee;

    public Shift(int id, int group, LocalTime startTime, LocalTime endTime, Level level, Double latitude, Double longitude) {
        this.id = id;
        this.group = group;
        this.startTime = startTime;
        this.endTime = endTime;
        this.level = level;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return Integer.toString(this.id) + " " + this.employee;
    }
}

class ShiftGroupRequirements{
    int shiftGroup;
    String[] skills;
}

@PlanningSolution
@NoArgsConstructor
@ToString
class TimeTable{

    @ProblemFactCollectionProperty
    @ValueRangeProvider
    List<Employee> employees;
    @PlanningEntityCollectionProperty
    List<Shift> shifts;

    @PlanningScore
    HardSoftScore score;

    public TimeTable(List<Employee> employees, List<Shift> shifts) {
        this.employees = employees;
        this.shifts = shifts;
    }
}


public class Main {
    public static void main(String[] args) {

        // Read Data
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).registerModule(new JavaTimeModule());
        Employee[] employees;
        Shift[] shifts;
        try {
            employees = mapper.readValue(new File("src/main/resources/employees.yaml"), Employee[].class);
            System.out.println(Arrays.toString(employees));
            shifts = mapper.readValue(new File("src/main/resources/shifts.yaml"), Shift[].class);
            System.out.println(Arrays.toString(shifts));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Solve
        SolverFactory<TimeTable> solverFactor = SolverFactory.create(
                new SolverConfig().withSolutionClass(TimeTable.class)
                        .withEntityClasses(Shift.class)
                        .withConstraintProviderClass(ShiftConstraintProvider.class)
                        .withTerminationUnimprovedSpentLimit(Duration.ofMillis(1000)));

        TimeTable problem = new TimeTable(Arrays.stream(employees).toList(), Arrays.stream(shifts).toList());

        TimeTable solution = solverFactor.buildSolver().solve(problem);
        System.out.println(solution);
    }
}
