package rs;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.ArrayList;
import java.util.List;

@PlanningSolution
public class ShiftRoster {
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    List<Employee> inputEmployees;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    List<EmployeeScheduledVacation> inputEmployeeScheduledVacations;

    @PlanningEntityCollectionProperty
    List<ShiftAssignment> shiftAssignments = new ArrayList<>();

    @PlanningScore
    HardSoftScore hardSoftScore;

    public ShiftRoster() {
    }

    public ShiftRoster(List<Employee> inputEmployees, List<EmployeeScheduledVacation> employeeScheduledVacations) {
        this.inputEmployees = inputEmployees;
        this.inputEmployeeScheduledVacations = employeeScheduledVacations;
    }

    public List<Employee> getInputEmployees() {
        return inputEmployees;
    }

    public void setInputEmployees(List<Employee> inputEmployees) {
        this.inputEmployees = inputEmployees;
    }

    public List<ShiftAssignment> getShiftAssignments() {
        return shiftAssignments;
    }

    public void setShiftAssignments(List<ShiftAssignment> shiftAssignments) {
        this.shiftAssignments = shiftAssignments;
    }

    public HardSoftScore getHardSoftScore() {
        return hardSoftScore;
    }

    public void setHardSoftScore(HardSoftScore hardSoftScore) {
        this.hardSoftScore = hardSoftScore;
    }

    public List<EmployeeScheduledVacation> getInputEmployeeScheduledVacations() {
        return inputEmployeeScheduledVacations;
    }

    public void setInputEmployeeScheduledVacations(List<EmployeeScheduledVacation> inputEmployeeScheduledVacations) {
        this.inputEmployeeScheduledVacations = inputEmployeeScheduledVacations;
    }
}
