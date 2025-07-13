package rs;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class School {

    public School() {
    }

    @PlanningId
    Long id;

    @PlanningVariable
    Teacher teacher;

    public Long getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String
    toString() {
        return "School{" +
                "id=" + id +
                ", teacher=" + teacher +
                '}';
    }
}
