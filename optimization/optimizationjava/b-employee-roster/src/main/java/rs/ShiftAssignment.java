package rs;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class ShiftAssignment {

    @PlanningId
    Long id;

    Shift shift;

    @PlanningVariable
    Employee employee;

    public ShiftAssignment() {
    }

    public ShiftAssignment(Long id, Shift shift) {
        this.id = id;
        this.shift = shift;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "ShiftAssignment{" +
                "id=" + id +
                ", shift=" + shift +
                ", employee=" + employee +
                '}';
    }
}
