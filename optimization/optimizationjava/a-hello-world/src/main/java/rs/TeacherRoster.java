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
public class TeacherRoster {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    List<Teacher> inputTeachers;

    public List<Teacher> getInputTeachers() {
        return inputTeachers;
    }

    public void setInputTeachers(List<Teacher> inputTeachers) {
        this.inputTeachers = inputTeachers;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    public HardSoftScore getHardSoftScore() {
        return hardSoftScore;
    }

    public void setHardSoftScore(HardSoftScore hardSoftScore) {
        this.hardSoftScore = hardSoftScore;
    }

    @PlanningEntityCollectionProperty
    List<School> schools = new ArrayList<>(5);

    @PlanningScore
    HardSoftScore hardSoftScore;

    public TeacherRoster() {
    }

}
